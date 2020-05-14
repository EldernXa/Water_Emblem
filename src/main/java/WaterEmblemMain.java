import backend.Personnage;
import frontend.AffichageGraphique;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class WaterEmblemMain  {

    public void start(Stage primaryStage)throws FileNotFoundException
    {
        /*int tailleX = 50, tailleY = 50;
        Rectangle rect = new Rectangle();
        rect.setHeight(tailleX);
        rect.setWidth(tailleY);
        rect.setX(0);
        rect.setY(0);
        rect.setFill(Color.RED);
        rect.setStroke(Color.BLACK);
        Rectangle rect1 = new Rectangle();
        rect1.setHeight(tailleX);
        rect1.setWidth(tailleY);
        rect1.setX(tailleX);
        rect1.setY(tailleY);
        rect1.setFill(Color.GREEN);
        rect1.setStroke(Color.BLACK);
        FileInputStream inputStream = new FileInputStream("./src/main/resources/maps/WaterEmblemMap01.png");
        Image img = new Image(inputStream);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(25);
        imgView.setFitWidth(25);

        GridPane root = new GridPane();
        GridPane newBox = new GridPane();
        GridPane boxImg = new GridPane();
        boxImg.setAlignment(Pos.TOP_LEFT);
        newBox.setAlignment(Pos.TOP_LEFT);
        newBox.add(rect, 0, 0);
        newBox.add(rect1, 1, 0);
        boxImg.setHgap(50);
        boxImg.setVgap(50);
        boxImg.add(imgView, 1, 0);
        // For move
        GridPane.setColumnIndex(imgView, 1);
        GridPane.setRowIndex(imgView, 1);
        //
        root.getChildren().addAll(newBox, boxImg);*/
        primaryStage.setScene(new Scene(new AffichageGraphique().init(), 600, 800));
        primaryStage.show();
    }
    public static void main(String[] args){
        //launch(args);
        Personnage p = new Personnage("Cavalie", "aea");
        System.out.println(p.getCaracteristique().getDef());
    }
}
