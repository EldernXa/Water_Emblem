package frontend;

import backend.Personnage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class AffichePerso {
    List<Personnage> listPersonnage;
    List<Personnage> listEnnemi;

    GridPane init() {
        GridPane perso = new GridPane();
        try {
            FileInputStream inputStream = new FileInputStream("./src/main/resources/maps/WaterEmblemMap01.png");
            Image img = new Image(inputStream);
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(25);
            imgView.setFitWidth(25);
            perso.add(imgView, 0, 0);
        }catch(FileNotFoundException exception){
            System.out.println("Image non existant.");
        }
        return perso;
    }
}
