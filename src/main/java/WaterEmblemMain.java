import frontend.AffichageGraphique;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;


public class WaterEmblemMain extends Application {
    public void start(Stage primaryStage)
    {
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        primaryStage.setScene(new Scene(new AffichageGraphique().init(), (int)dimension.getWidth(), (int)dimension.getHeight()));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);

    }
}
