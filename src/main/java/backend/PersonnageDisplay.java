package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private boolean change;

    public PersonnageDisplay(String nom, int x, int y){
        present = true;
        change = false;
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

    public boolean isPresent(){
        return present;
    }

    public void setPresent(boolean present){
        this.present = present;
    }

    public boolean getChange(){
        return change;
    }
    public void change(){
        change = !change;
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
                if(f.getName().contains("Arret"))
                    inputStream[0].add(new FileInputStream(f.getAbsoluteFile()));
                else if(f.getName().contains("Arri")){
                    inputStream[1].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Attaque")){
                    inputStream[2].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Avant")){
                    inputStream[3].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Droite")){
                    inputStream[4].add(new FileInputStream(f.getAbsoluteFile()));
                }else if(f.getName().contains("Gauche")){
                    inputStream[5].add(new FileInputStream(f.getAbsoluteFile()));
                }
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

    boolean isAlive(){
        return isAlive = perso.getCaracteristique().getHp() <= 0 ;
    }

    public ImageView getImageView(){
        return imageView[orientation].get(num);
    }

    void setDeath(){
        isAlive = false;
    }
    public void setOrientation(int orientation){
        switch(orientation){
            case 0:
                this.orientation = 0;
                break;
            case 1:
                this.orientation = 1;
                break;
            case 2:
                this.orientation = 2;
                break;
            case 3:
                this.orientation = 3;
                break;
            case 4:
                this.orientation = 4;
                break;
            case 5:
                this.orientation=5;
                break;
        }
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
