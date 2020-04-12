import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WaterEmblemMain extends Application {

    public void start(Stage primaryStage){
        VBox box = new VBox();
        box.getChildren().add(new Text("Test JavaFX !"));
        Scene newScene = new Scene(box, 150, 150);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    public static void main(String [] args){
        launch(args);
    }

}
