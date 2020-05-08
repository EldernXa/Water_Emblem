package frontend;

import backend.Coordinate;
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
    GridPane perso;
    ImageView imgView;

    public AffichePerso(){
        init();
    }

    void init() {
        perso = new GridPane();
        perso.setVgap(50);
        perso.setHgap(50);
        try {
            FileInputStream inputStream = new FileInputStream("./src/main/resources/maps/WaterEmblemMap01.png");
            Image img = new Image(inputStream);
            imgView = new ImageView(img);
            imgView.setFitHeight(25);
            imgView.setFitWidth(25);
            perso.add(imgView, 0, 0);
        }catch(FileNotFoundException exception){
            System.out.println("Image non existant.");
        }
    }

    public GridPane getGridPanePerso(){
        return perso;
    }

    public ImageView getImgView(){
        return imgView;
    }

    public void move(Coordinate coordinate){
        GridPane.setColumnIndex(imgView, coordinate.getX());
        GridPane.setRowIndex(imgView, coordinate.getY());
    }

}
