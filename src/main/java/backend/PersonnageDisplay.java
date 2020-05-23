package backend;

import frontend.AfficheMap;
import frontend.AffichePerso;
import frontend.Event;
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

    public PersonnageDisplay(String nom, int x, int y){
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
            if(perso.getCaracteristique().getName().compareTo("mechant")!=0) {
                file = new File("./src/main/resources/spritesPersos/Sprite" + perso.getCaracteristique().getName());
            }else{
                file = new File("./src/main/resources/spritesPersos/Sprite" + perso.getCaracteristique().getType());
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

    public void action(AffichePerso affichePerso, GridPane perso, GridPane grilleMvt, AfficheMap afficheMap, VBox console){
        if(isAlive()) {
            ArrayList<Coordinate> attack = affichePerso.getAttackAreaAfterMovement(getPersonnage(), getCoordinate());
            boolean booleanAttack = false;
            for (Coordinate c : attack) {
                PersonnageDisplay p = AffichePerso.getPersonnageDisplayAt(c);
                if (p != null && AffichePerso.contains(AffichePerso.listPersonnage, p)) {
                    booleanAttack = true;
                    int hpEnnemi = getPersonnage().getCaracteristique().getHp();
                    int hpPersonnage = p.getPersonnage().getCaracteristique().getHp();
                    Event.printAttackAction(this, p, Color.RED, Color.BLUE, console);
                    this.getPersonnage().attack(c);
                    Event.printAfterAttack(p, hpPersonnage, Color.BLUE, console);
                    Event.printCounterAttack(this, p, hpEnnemi, Color.RED, Color.BLUE, console);
                }
            }

            if (!booleanAttack) {
                ArrayList<Coordinate> mvt = affichePerso.getCoordinate(getPersonnage(), getCoordinate());
                Coordinate bestCoordinate = null;
                for (Coordinate c : mvt) {
                    if (AffichePerso.getPersonnageDisplayAt(c) == null) {
                        if (bestCoordinate == null)
                            bestCoordinate = new Coordinate(c.getX(), c.getY());
                        for (PersonnageDisplay p : AffichePerso.listPersonnage) {
                            double newValue = Math.sqrt(Math.pow(c.getX() - p.getCoordinate().getX(), 2) + Math.pow(c.getY() - p.getCoordinate().getY(), 2));
                            double comparaison = Math.sqrt(Math.pow(bestCoordinate.getX() - c.getX(), 2) + Math.pow(bestCoordinate.getY() - c.getY(), 2));
                            if (newValue < comparaison)
                                bestCoordinate = new Coordinate(c.getX(), c.getY());
                        }
                    }
                }

                if (bestCoordinate != null) {
                    Event.printMoveAction(this, Color.RED, console);
                    affichePerso.move(this, bestCoordinate, perso, grilleMvt, afficheMap, console);
                    attack = affichePerso.getAttackAreaAfterMovement(getPersonnage(), getCoordinate());
                    for (Coordinate c : attack) {
                        PersonnageDisplay p = AffichePerso.getPersonnageDisplayAt(c);
                        if (p != null && AffichePerso.contains(AffichePerso.listPersonnage, p)) {
                            this.getPersonnage().attack(c);
                            if (getPersonnage().getCaracteristique().getHp() <= 0)
                                setDeath();
                            if (p.getPersonnage().getCaracteristique().getHp() <= 0)
                                p.setDeath();
                        }
                    }

                }
            }
        }else{
            if(Event.numEnnemi<AffichePerso.listEnnemi.size()-1)
            {
                Event.numEnnemi++;
                while(Event.numEnnemi<AffichePerso.listEnnemi.size()-1 && !AffichePerso.listEnnemi.get(Event.numEnnemi).isAlive()) {
                    if(Event.numEnnemi<AffichePerso.listEnnemi.size()-1)
                        Event.numEnnemi++;
                }
                if(AffichePerso.listEnnemi.get(Event.numEnnemi).isAlive())
                    AffichePerso.listEnnemi.get(Event.numEnnemi).action(affichePerso, perso, grilleMvt, afficheMap, console);
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
