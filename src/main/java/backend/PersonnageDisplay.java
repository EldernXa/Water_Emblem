package backend;

import IA.Action;
import frontend.AfficheMap;
import frontend.AffichePerso;
import frontend.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class PersonnageDisplay {
    ArrayList<FileInputStream>[] inputStream;
    ArrayList<ImageView>[] imageView;
    private int orientation;
    boolean isAlive;
    private Coordinate coordinate;
    private Personnage perso;
    private int num;
    private boolean present;
    private boolean booleanAttack;
    private boolean booleanMove;
    private boolean endTurn;
    private boolean isBad;

    public PersonnageDisplay(String nom, int x, int y, boolean isBad){
        this.isBad = isBad;
        present = true;
        booleanAttack =false;
        booleanMove = false;
        endTurn = false;
        this.perso = new Personnage(nom);
        inputStream = new ArrayList[6];
        imageView = new ArrayList[6];
        num = 0;
        orientation = 0;
        for(int i=0; i<6;i++)
        {
            inputStream[i] = new ArrayList<>();
            imageView[i] = new ArrayList<>();
        }
        coordinate = new Coordinate(x, y);
        initPicture();
    }

    public boolean isBad(){
        return isBad;
    }

    public void setBooleanMove(boolean value){
        this.booleanMove = value;
    }

    public void setBooleanAttack(boolean value){
        this.booleanAttack = value;
    }

    public void setEndTurn(boolean value){
        this.endTurn = value;
    }

    public boolean getBooleanMove(){
        return booleanMove;
    }

    public boolean getBooleanAttack(){
        return booleanAttack;
    }

    public boolean getEndTurn(){
        return endTurn;
    }

    public boolean isPresent(){
        return present;
    }

    public void setPresent(boolean present){
        this.present = present;
    }

    public int getOrientation(){
        return orientation;
    }

    public InputStream getInputStream(){
        return inputStream[orientation].get(num);
    }

    public void initPicture(){
        try {
            File file;
            if(!isBad()) {
                file = new File("./src/main/resources/spritesPersos/Sprite" + perso.getCaracteristique().getName());
            }else{
                file = new File("./src/main/resources/spritesPersos/SpriteEnnemis/Sprite" + perso.getCaracteristique().getName());
            }
            for(File f: Objects.requireNonNull(file.listFiles())){
                if(f.getName().contains("Arret") && !f.getName().contains("Nb"))
                    inputStream[0].add(new FileInputStream(f.getAbsoluteFile()));
                else if(f.getName().contains("Arri")){
                    inputStream[1].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Avant")){
                    inputStream[2].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Droite")){
                    inputStream[3].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Gauche")){
                    inputStream[4].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("ArretNb"))
                    inputStream[5].add(new FileInputStream(f.getAbsoluteFile()));
            }
            for(int i=0; i<inputStream.length; i++)
                for(int in=0; in<inputStream[i].size(); in++)
                {
                    imageView[i].add(new ImageView(new Image(inputStream[i].get(in))));
                }
        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist");
        }
    }

    public boolean isAlive(){
        return isAlive = perso.getCaracteristique().getHp() > 0 ;
    }

    public ImageView getImageView(){
        return imageView[orientation].get(num);
    }

    public void setDeath(){
        isAlive = false;
    }

    public void attack(Coordinate coordinate){
        this.getPersonnage().attack(coordinate);
        PersonnageDisplay perso = AffichePerso.getPersonnageDisplayAt(coordinate);
        if(perso!=null)
        {
            if(this.getPersonnage().getCaracteristique().getHp()<=0)
                this.setDeath();
            if(perso.getPersonnage().getCaracteristique().getHp()<=0)
                perso.setDeath();
        }
    }
    public void setOrientation(int orientation){
        this.orientation = orientation;
        num = 0;
    }
    void move(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    public Coordinate getCoordinate(){return this.coordinate;}
    public Personnage getPersonnage(){return perso;}
    public void setCoordinate(Coordinate coordinate){
        this.coordinate = new Coordinate(coordinate.getX(), coordinate.getY());
        for(int i=0; i<inputStream.length;i++)
            for(int is=0; is<inputStream[i].size(); is++)
            {
                imageView[i].get(is).setTranslateX(0);
                imageView[i].get(is).setTranslateY(0);
            }
    }

    public void action(AffichePerso affichePerso, GridPane perso, GridPane grilleMvt, AfficheMap afficheMap,
                       Action action, VBox console, Button endTurn){
        if(isAlive()) {
            if (!action.getPosArrive().equal(action.getPosDepart())) {
                this.setBooleanMove(true);
                Event.printMoveAction(this, Color.web("0xAA3436"), console);
                affichePerso.move(this, action.getPosArrive(), perso, grilleMvt, afficheMap, console, endTurn);
            }
            PersonnageDisplay p = AffichePerso.getPersonnageDisplayAt(action.getPosDefenceur());
            if (p != null) {
                int hpEnnemi = getPersonnage().getCaracteristique().getHp();
                int hpPersonnage = p.getPersonnage().getCaracteristique().getHp();
                Event.printAttackAction(this, p, Color.web("0xAA3436"), Color.web("0x209396"), console);
                this.getPersonnage().attack(action.getPosDefenceur());
                if(p.isAlive()) {
                    Event.printAfterAttack(p, hpPersonnage, Color.web("0x209396"), console);
                    Event.printCounterAttack(this, p, hpEnnemi, Color.web("0xAA3436"), Color.web("0x209396"), console);
                }
                if(!this.getBooleanMove())
                {
                    if(Event.numEnnemi<Event.listMechantRestant.size()-1)
                    {
                        Event.listMechantRestant.get(++Event.numEnnemi).action(affichePerso, perso, grilleMvt, afficheMap,
                                Event.listAction.get(Event.numEnnemi), console, endTurn);
                    }else{
                        Event.numEnnemi=0;
                    }
                }
            }
        }

    }


    public void nextPosition(){
        if(num==inputStream[orientation].size()-1) {
            num = 0;
        }
        else
            num++;
    }

    public int getNum(){
        return num;
    }

}
