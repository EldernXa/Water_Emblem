import frontend.AffichageGraphique;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class WaterEmblemMain extends Application {

    public void start(Stage primaryStage)
    {
        primaryStage.setScene(new Scene(new AffichageGraphique().init(), 600, 800));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
