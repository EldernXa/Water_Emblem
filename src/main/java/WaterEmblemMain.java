import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class WaterEmblemMain extends Application {

    public void start(Stage primaryStage)throws FileNotFoundException
    {
        VBox box = new VBox();
        FileInputStream inputStream = new FileInputStream("./src/main/resources/maps/WaterEmblemMap01.png");
        Image img = new Image(inputStream);
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(400);
        imgView.setFitWidth(300);
        box.getChildren().add(imgView);
        primaryStage.setScene(new Scene(box, 300, 300));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
