package backend;

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;

public class PersonnageDisplay {
    FileInputStream inputStream;
    ImageView imageView;
    String orientation;
    boolean isAlive;
    Coordinate coordinate;
    Personnage perso;

    boolean sAlive(){
        return isAlive = perso.getCaracteristique().getHp() <= 0 ;
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

}
