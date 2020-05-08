package backend;

import javax.swing.text.html.ImageView;
import java.io.FileInputStream;

public class PersonnageDisplay {
    FileInputStream inputStream;
    ImageView imageView;
    String orientation;
    boolean isAlive;
    backend.Coordinate coordinate;
    backend.Personnage perso;

    boolean sAlive(){
        return isAlive = perso.getCaracteristique().getHp() <= 0 ;
    }
    void setDeath(){
        isAlive = false;
    }
    void setOrientation(String orientation){
        this.orientation = orientation;
    }
    void move(backend.Coordinate coordinate){
        this.coordinate = coordinate;
    }

}
