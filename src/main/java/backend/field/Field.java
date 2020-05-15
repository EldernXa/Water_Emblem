package backend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Field {
    Pane apsect;
    ImageView imgView;

    public Field(String nomImg){
        apsect = new Pane();
        try {
            FileInputStream inputStream = new FileInputStream("src/main/resources/"+ nomImg +".png");
            Image img = new Image(inputStream);
            imgView = new ImageView(img);
            apsect.getChildren().add(imgView);
        }catch(FileNotFoundException exception){
            System.out.println("Image non existant.");
        }
    }

    public Pane getApsect() {
        return apsect;
    }

    public ImageView getImgView() {
        return imgView;
    }

    abstract void affect(Personnage personnage);
    abstract void disaffect(Personnage personnage);
}

//package backend;
//
//import javafx.scene.paint.Color;
//
//public abstract class Field {
//    private Color color;
//
//    public Field(Color color){
//        this.color = color;
//    }
//
//    public Color getColor(){
//        return color;
//    }
//
//    abstract void affect(Personnage personnage);
//    abstract void disaffect(Personnage personnage);
//}
