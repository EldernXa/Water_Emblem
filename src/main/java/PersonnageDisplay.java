import javax.swing.text.html.ImageView;
import java.io.FileInputStream;

public class PersonnageDisplay {
    FileInputStream inputStream;
    ImageView imageView;
    String orientation;
    boolean isAlive;
    Coordinate coordinate;

    boolean sAlive(){
        return isAlive;
    }
    void setDeath(){
        isAlive = false;
    }
    void setOrientation(String orientation){
        this.orientation = orientation;
    }
    void move(Coordinate coordinate){

    }

}
