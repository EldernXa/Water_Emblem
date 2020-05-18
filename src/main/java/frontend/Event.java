package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.plaf.ProgressBarUI;
import java.util.ArrayList;
import java.util.HashMap;

public class Event {
    static private PersonnageDisplay personnageSelected = null;
    static void clickOnMap(GridPane perso, AffichePerso affichePerso, GridPane grilleMvt, GridPane grilleAttack,
                           VBox information, Button move, Button attack, Button stay){
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
                        HBox photoNom = new HBox();
                        ImageView imgView = new ImageView(personnageSelected.getImageView().getImage());
                        imgView.setFitWidth(AffichageGraphique.size);
                        imgView.setFitHeight(AffichageGraphique.size);
                        photoNom.getChildren().addAll(imgView, new Label(personnageSelected.getPersonnage().getCaracteristique().getName()));
                        HBox pv = new HBox();
                        ProgressBar progressBar = new ProgressBar();
                        progressBar.setProgress((float)personnageSelected.getPersonnage().getCaracteristique().getHp()/personnageSelected.getPersonnage().getCaracteristique().getMaxHp());
                        pv.getChildren().add(progressBar);
                        information.getChildren().addAll(photoNom, pv);
                        move.setVisible(true);
                        attack.setVisible(true);
                        stay.setVisible(true);
                    }
                }
                else{
                    if(personnageSelected != AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)))
                    {
                        ArrayList<Coordinate> listMvt = affichePerso.getCoordinate(personnageSelected.getPersonnage(),
                                personnageSelected.getCoordinate());
                        for (Coordinate c : listMvt) {
                            if (c.equal(new Coordinate(x, y))) {
                                if(AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))==null) {
                                    affichePerso.move(personnageSelected, new Coordinate(x, y), perso);
                                    personnageSelected = null;
                                    grilleMvt.getChildren().clear();
                                    grilleAttack.getChildren().clear();
                                }
                            }
                        }
                    }
                    move.setVisible(false);
                    attack.setVisible(false);
                    stay.setVisible(false);
                    information.getChildren().clear();
                    personnageSelected = null;
                    grilleMvt.getChildren().clear();
                    grilleAttack.getChildren().clear();
                }
            }
        });
    }

    static private void addRectangle(GridPane grille, Coordinate c, Color color){
        if(AffichePerso.getPersonnageAt(c)!=null && AffichePerso.contains(AffichePerso.listEnnemi, AffichePerso.getPersonnageDisplayAt(c)))
        {
            Rectangle rect = new Rectangle(AffichageGraphique.size, AffichageGraphique.size);
            rect.setFill(Color.rgb(255, 0, 0, 0.3));
            grille.add(rect, c.getX(), c.getY());
            GridPane.setHalignment(rect, HPos.LEFT);
            GridPane.setValignment(rect, VPos.TOP);
        }
        else if(AffichePerso.getPersonnageAt(c)==null){
            Rectangle rect = new Rectangle(AffichageGraphique.size, AffichageGraphique.size);
            rect.setFill(color);
            grille.add(rect, c.getX(), c.getY());
            GridPane.setHalignment(rect, HPos.LEFT);
            GridPane.setValignment(rect, VPos.TOP);
        }
    }
}
