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
    static private PersonnageDisplay personnageSelected = null;
    static void clickOnMap(GridPane perso, AffichePerso affichePerso, GridPane grilleMvt, GridPane grilleAttack){
        perso.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                        for(Coordinate c: affichePerso.getCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                            addRectangle(grilleMvt, c, Color.rgb(0, 0, 255, 0.3));
                        for(Coordinate c: affichePerso.getAttackCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                            addRectangle(grilleAttack, c, Color.rgb(255, 0, 0, 0.3));
                    }
                }
                else{
                    ArrayList<Coordinate> listMvt = affichePerso.getCoordinate(personnageSelected.getPersonnage(),
                            personnageSelected.getCoordinate());
                    for(Coordinate c:listMvt){
                        if(c.equal(new Coordinate(x, y))) {
                            affichePerso.move(personnageSelected, new Coordinate(x, y), perso);
                            personnageSelected = null;
                            grilleMvt.getChildren().clear();
                            grilleAttack.getChildren().clear();
                        }
                    }
                }
            }
        });
    }

    static private void addRectangle(GridPane grille, Coordinate c, Color color){
        Rectangle rect = new Rectangle(AffichageGraphique.size, AffichageGraphique.size);
        rect.setFill(color);
        grille.add(rect, c.getX(), c.getY());
        GridPane.setHalignment(rect, HPos.LEFT);
        GridPane.setValignment(rect, VPos.TOP);
    }
}
