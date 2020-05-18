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

    public PersonnageDisplay(String nom, int x, int y){
        this.perso = new Personnage(nom);
        inputStream = new ArrayList[6];
        imageView = new ArrayList[6];
        num = 0;
        orientation = 0;
        for(int i=0; i<=5;i++)
        {
            inputStream[i] = new ArrayList<>();
            imageView[i] = new ArrayList<>();
        }
        coordinate = new Coordinate(x, y);
        initPicture();
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
                    inputStream[0].add(new FileInputStream(f.getAbsolutePath()));
                else if(f.getName().contains("Arrière")){
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
            for(int i=0; i<6; i++)
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
    void setOrientation(String orientation){
        switch(orientation){
            case "Arret":
                this.orientation = 0;
                break;
            case "Arrière":
                this.orientation = 1;
                break;
            case "Attaque":
                this.orientation = 2;
                break;
            case "Avant":
                this.orientation = 3;
                break;
            case "Droite":
                this.orientation = 4;
                break;
            case "Gauche":
                this.orientation=5;
                break;
        }
        num = 0;
        for(int i=0; i<6; i++) {
            for (int in = 0; in < inputStream[i].size(); in++) {
                imageView[i].add(new ImageView(new Image(inputStream[i].get(in))));
            }
        }
    }
    void move(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    public Coordinate getCoordinate(){return this.coordinate;}
    public Personnage getPersonnage(){return perso;}
    public void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public void nextPosition(){
        if(num==inputStream[orientation].size()-1) {
            num = 0;
        }
        else
            num++;
        for(int i=0; i<6; i++) {
            for (int in = 0; in < inputStream[i].size(); in++) {
                imageView[i].add(new ImageView(new Image(inputStream[i].get(in))));
            }
        }
    }

    public int getNum(){
        return num;
    }

}
