package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Event {
    static PersonnageDisplay personnageSelected = null;
    static void clickOnMap(Pane pane, AffichePerso affichePerso){
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                int x = (int) Math.floor(event.getX()/AffichageGraphique.size);
                int y = (int) Math.floor(event.getY()/AffichageGraphique.size);
                if(personnageSelected == null)
                {
                    personnageSelected = AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y));
                }
                else{
                    affichePerso.move(personnageSelected,new Coordinate(x, y));
                    personnageSelected = null;
                }
            }
        });
    }
}
