package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PersonnageDisplay {
    FileInputStream inputStream;
    ImageView imageView;
    String orientation;
    boolean isAlive;
    Coordinate coordinate;
    Personnage perso;

    public PersonnageDisplay(String nom, int x, int y){
        this.perso = new Personnage(nom);
        coordinate = new Coordinate(x, y);
        try {
            this.inputStream = new FileInputStream("./src/main/resources/spritesPersos/Sprite" + nom + "/" + nom + "Avant1.png");
            imageView = new ImageView(new Image(inputStream));
        }catch(FileNotFoundException e){
            System.out.println("File doesn't exist");
        }
    }

    boolean isAlive(){
        return isAlive = perso.getCaracteristique().getHp() <= 0 ;
    }

    public ImageView getImageView(){
        return imageView;
    }

    void setDeath(){
        isAlive = false;
    }
    void setOrientation(String orientation){
        this.orientation = orientation;
    }
    void move(Coordinate coordinate){
        this.coordinate = coordinate;
    }
    public Coordinate getCoordinate(){return this.coordinate;}
    public Personnage getPersonnage(){return perso;}
    public void setCoordinate(Coordinate coordinate){
        this.coordinate = coordinate;
    }

}
