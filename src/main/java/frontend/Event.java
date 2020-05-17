package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

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
                    if(personnageSelected!=null)
                    {
                        for(Coordinate c: affichePerso.getCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate())){
                            Rectangle rect = new Rectangle(AffichageGraphique.size, AffichageGraphique.size);
                            rect.setFill(Color.rgb(0, 0, 255, 0.3));
                            GridPane.setHalignment(rect, HPos.LEFT);
                            GridPane.setValignment(rect, VPos.TOP);
                            AffichageGraphique.grilleMvt.add(rect, c.getX(), c.getY());
                        }
                    }
                }
                else{
                    ArrayList<Coordinate> listMvt = affichePerso.getCoordinate(personnageSelected.getPersonnage(),
                            personnageSelected.getCoordinate());
                    for(Coordinate c:listMvt){
                        if(c.equal(new Coordinate(x, y))) {
                            affichePerso.move(personnageSelected, new Coordinate(x, y));
                            personnageSelected = null;
                            AffichageGraphique.grilleMvt.getChildren().clear();
                        }
                    }
                }
            }
        });
    }
}
