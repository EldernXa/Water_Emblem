package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.util.Duration;

import javax.swing.plaf.ProgressBarUI;
import java.util.ArrayList;
import java.util.HashMap;

public class Event {
    static private PersonnageDisplay personnageSelected = null;
    static private boolean booleanMove = false;
    static private boolean booleanAttack = false;
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
                    if(personnageSelected!=null){
                        HBox photoNom = new HBox();
                        ImageView imgView = new ImageView(personnageSelected.getImageView().getImage());
                        imgView.setFitWidth(AffichageGraphique.size);
                        imgView.setFitHeight(AffichageGraphique.size);
                        photoNom.getChildren().addAll(imgView, new Label(personnageSelected.getPersonnage().getCaracteristique().getName()));
                        HBox pv = new HBox();
                        ProgressBar progressBar = new ProgressBar();
                        progressBar.setProgress((float)personnageSelected.getPersonnage().getCaracteristique().getHp()/personnageSelected.getPersonnage().getCaracteristique().getMaxHp());
                        Label pvLab = new Label(String.valueOf(personnageSelected.getPersonnage().getCaracteristique().getHp()) + "/" + personnageSelected.getPersonnage().getCaracteristique().getMaxHp());
                        pvLab.setFont(new Font("Arial",10));
                        pv.getChildren().add(progressBar);
                        information.setVisible(true);
                        information.getChildren().addAll(photoNom, pv, pvLab);
                    }
                    if(personnageSelected!=null && !AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected))
                    {
                        for(Coordinate c: affichePerso.getCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                            addRectangle(grilleMvt, c, Color.rgb(0, 0, 255, 0.3));
                        for(Coordinate c: affichePerso.getAttackCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                            addRectangle(grilleAttack, c, Color.rgb(255, 0, 0, 0.3));
                        move.setVisible(false);
                        attack.setVisible(false);
                        stay.setVisible(true);
                    }
                }
                else{
                    if(!AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected)) {
                        if (personnageSelected != AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))) {
                            ArrayList<Coordinate> listMvt = affichePerso.getCoordinate(personnageSelected.getPersonnage(),
                                    personnageSelected.getCoordinate());
                            for (Coordinate c : listMvt) {
                                if (c.equal(new Coordinate(x, y))) {
                                    if (AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)) == null) {
                                        move.setVisible(true);
                                        move.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                move.setVisible(false);
                                                information.getChildren().clear();
                                                affichePerso.move(personnageSelected, new Coordinate(x, y), perso);
                                                personnageSelected = null;
                                                grilleMvt.getChildren().clear();
                                                grilleAttack.getChildren().clear();
                                                stay.setText("Fin");
                                            }
                                        });
                                    }else if(AffichePerso.contains(AffichePerso.listEnnemi, AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)))){
                                        attack.setVisible(true);
                                        attack.setOnAction(new EventHandler<ActionEvent>(){
                                            @Override
                                            public void handle(ActionEvent event){
                                                attack.setVisible(false);
                                                information.getChildren().clear();
                                                personnageSelected.getPersonnage().attack(new Coordinate(x, y));
                                                personnageSelected = null;
                                                grilleMvt.getChildren().clear();
                                                grilleAttack.getChildren().clear();
                                                stay.setText("Fin");
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                    else {
                        move.setVisible(false);
                        attack.setVisible(false);
                        stay.setVisible(false);
                        information.getChildren().clear();
                        personnageSelected = null;
                        grilleMvt.getChildren().clear();
                        grilleAttack.getChildren().clear();
                    }
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

    public static void buttonStay(Button stay, VBox information, Button move, Button attack,
                           GridPane grilleMvt, GridPane grilleAttack, GridPane perso){
        stay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                information.setVisible(false);
                information.getChildren().clear();
                stay.setVisible(false);
                move.setVisible(false);
                attack.setVisible(false);
                AffichageGraphique.group.getChildren().clear();
                grilleMvt.getChildren().clear();
                grilleAttack.getChildren().clear();
                personnageSelected =null;
            }
        });
    }
}
