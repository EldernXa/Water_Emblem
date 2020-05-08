package frontend;

import backend.Coordinate;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;

public class AffichageGraphique {

    public Pane init() throws FileNotFoundException {
        int height = 50, width = 50;
        GridPane root = new GridPane();
        GridPane map = new GridPane();
        GridPane perso;
        Rectangle rect1 = new Rectangle();
        rect1.setHeight(height);
        rect1.setWidth(width);
        rect1.setFill(Color.WHITE);
        rect1.setStroke(Color.BLACK);
        Rectangle rect2 = new Rectangle();
        rect2.setHeight(height);
        rect2.setWidth(width);
        rect2.setFill(Color.GREEN);
        rect2.setStroke(Color.BLACK);
        root.setAlignment(Pos.TOP_LEFT);
        map.setAlignment(Pos.TOP_LEFT);
        map.add(rect1, 0, 0);
        map.add(rect2, 1, 0);


        AffichePerso affichePerso = new AffichePerso();
        perso = affichePerso.getGridPanePerso();

        Coordinate coordinate = new Coordinate(1, 1);
        affichePerso.move(coordinate);
        perso = affichePerso.getGridPanePerso();

        perso.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int)Math.floor(event.getX()/width);
                int y = (int)Math.floor(event.getY()/height);
                affichePerso.move(new Coordinate(x, y));
            }
        });

        root.getChildren().addAll(map, perso);
        return root;
    }
}
