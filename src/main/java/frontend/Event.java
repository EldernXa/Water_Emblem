package frontend;

import backend.Coordinate;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Event {
    static void clickOnMap(Pane pane, AffichePerso affichePerso, int size){
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                int x = (int) Math.floor(event.getX()/size);
                int y = (int) Math.floor(event.getY()/size);
                affichePerso.move(new Coordinate(x, y));
            }
        });
    }
}
