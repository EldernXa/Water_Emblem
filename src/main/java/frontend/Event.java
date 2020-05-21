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
    static private PersonnageDisplay ennemiSelected = null;

    static void clickOnMap(GridPane perso, AffichePerso affichePerso, GridPane grilleMvt, GridPane grilleAttack,
                           VBox information, Button move, Button attack, Button stay, AfficheMap afficheMap){
        perso.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                int x = (int) Math.floor(event.getX()/AffichageGraphique.size);
                int y = (int) Math.floor(event.getY()/AffichageGraphique.size);
                if(personnageSelected == null)
                {
                    information.getChildren().clear();
                    stay.setText("Rien faire");
                    personnageSelected = AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y));
                    stay.setVisible(true);
                    if(personnageSelected!=null){
                        addInformation(information, personnageSelected,"Green");
                    }
                    if(personnageSelected!=null && !AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected) &&
                        !personnageSelected.getEndTurn())
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
                    if(!AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected) && !personnageSelected.getEndTurn()) {
                        if (personnageSelected != AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))) {
                            ArrayList<Coordinate> listMvt = affichePerso.getCoordinate(personnageSelected.getPersonnage(),
                                    personnageSelected.getCoordinate());
                            for (Coordinate c : listMvt) {
                                if (c.equal(new Coordinate(x, y))) {
                                    if (AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)) == null && !personnageSelected.getBooleanMove()) {
                                        move.setVisible(true);
                                        move.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                move.setVisible(false);
                                                affichePerso.move(personnageSelected, new Coordinate(x, y), perso, grilleMvt, afficheMap);
                                                personnageSelected.setBooleanMove(true);
                                                grilleMvt.getChildren().clear();
                                                grilleAttack.getChildren().clear();
                                                if(personnageSelected.getBooleanAttack())
                                                    stay.fire();
                                                stay.setText("Fin");
                                            }
                                        });
                                    }
                                    if(!personnageSelected.getBooleanAttack()&&
                                        AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))!=null&&
                                        AffichePerso.contains(AffichePerso.listEnnemi, AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)))){
                                        for(Coordinate coordinate:affichePerso.getAttackAreaAfterMovement(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                                        {
                                            if(coordinate.equal(new Coordinate(x, y)))
                                            {
                                                ennemiSelected = AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y));
                                                information.getChildren().clear();
                                                addInformation(information, personnageSelected,"Green");
                                                addInformation(information, ennemiSelected,"Red");
                                                attack.setVisible(true);

                                                attack.setOnAction(new EventHandler<ActionEvent>(){
                                                    @Override
                                                    public void handle(ActionEvent event){
                                                        attack.setVisible(false);
                                                        personnageSelected.getPersonnage().attack(ennemiSelected.getCoordinate());
                                                        personnageSelected.setBooleanAttack(true);
                                                        grilleMvt.getChildren().clear();
                                                        grilleAttack.getChildren().clear();
                                                        information.getChildren().clear();
                                                        if(!ennemiSelected.isAlive())
                                                            ennemiSelected=null;
                                                        addInformation(information, personnageSelected,"Green");
                                                        if(ennemiSelected!=null)
                                                            addInformation(information, ennemiSelected,"Red");
                                                        if(personnageSelected.getBooleanMove())
                                                            stay.fire();
                                                        else{
                                                            for(Coordinate c: affichePerso.getCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                                                                addRectangle(grilleMvt, c, Color.rgb(0, 0, 255, 0.3));
                                                            grilleMvt.setVisible(true);
                                                        }
                                                        stay.setText("Fin");
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else{
                            if(!personnageSelected.getBooleanAttack() && !personnageSelected.getBooleanMove()) {
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
                }
            }
        });
    }

    static public void addRectangle(GridPane grille, Coordinate c, Color color){
        if(AffichePerso.getPersonnageAt(c)!=null && AffichePerso.contains(AffichePerso.listEnnemi, AffichePerso.getPersonnageDisplayAt(c))&&!personnageSelected.getBooleanAttack())
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

    public static void buttonStay(Button stay, Button move, Button attack,
                           GridPane grilleMvt, GridPane grilleAttack, AfficheMap afficheMap){
        stay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stay.setVisible(false);
                move.setVisible(false);
                attack.setVisible(false);
                AffichageGraphique.group.getChildren().clear();
                grilleMvt.getChildren().clear();
                grilleAttack.getChildren().clear();
                if(personnageSelected!= null)
                    personnageSelected.setEndTurn(true);
                personnageSelected =null;
                if(AffichePerso.endTurn())
                {
                    afficheMap.effectField();
                    if(AffichePerso.isWin())
                        System.out.println("Win");
                    if(AffichePerso.isLost())
                        System.out.println("Lost");
                    AffichePerso.newTurn();
                }
            }
        });
    }

    private static void addInformation(VBox information, PersonnageDisplay personnage,String couleur){
        HBox photoNom = new HBox();
        ImageView imgView = new ImageView(personnage.getImageView().getImage());
        imgView.setFitHeight(AffichageGraphique.size);
        imgView.setFitWidth(AffichageGraphique.size);
        photoNom.getChildren().addAll(imgView, new Label(personnage.getPersonnage().getCaracteristique().getName()));
        HBox pv = new HBox();
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress((float)personnage.getPersonnage().getCaracteristique().getHp()/personnage.getPersonnage().getCaracteristique().getMaxHp());
        progressBar.setStyle("-fx-accent: " + couleur  + ";");
        Label pvLab = new Label(personnage.getPersonnage().getCaracteristique().getHp() + "/" + personnage.getPersonnage().getCaracteristique().getMaxHp());
        pvLab.setFont(new Font(couleur,10));
        pv.getChildren().add(progressBar);
        information.setVisible(true);
        information.getChildren().addAll(photoNom, pv, pvLab);
    }
}
