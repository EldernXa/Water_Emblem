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
    static public int numEnnemi = 0;

    static void clickOnMap(GridPane perso, AffichePerso affichePerso, GridPane grilleMvt, GridPane grilleAttack,
                           VBox information, Button move, Button attack, Button stay, AfficheMap afficheMap, VBox console){
        perso.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                int x = (int) Math.floor(event.getX()/AffichageGraphique.size);
                int y = (int) Math.floor(event.getY()/AffichageGraphique.size);
                if(personnageSelected == null)
                {
                    initPersonnageSelected(information, x, y, stay,
                            affichePerso, grilleMvt, grilleAttack,
                            move, attack);
                    if(personnageSelected!=null && !AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected)){
                        stay.setText("Rien faire");
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
                                        buttonMove(x, y, move, affichePerso, perso, grilleMvt, afficheMap, grilleAttack, stay, console);
                                    }
                                    else if(AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))!=null&&
                                            AffichePerso.contains(AffichePerso.listEnnemi, AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)))){
                                        for(Coordinate coordinate:affichePerso.getAttackAreaAfterMovement(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                                        {
                                            if(coordinate.equal(new Coordinate(x, y)))
                                                buttonAttack(x, y, information, attack, grilleMvt, grilleAttack, stay, affichePerso, console);
                                        }
                                    }else if(AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))!=null&&
                                        AffichePerso.contains(AffichePerso.listPersonnage, AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y)))){
                                        if(!personnageSelected.getBooleanMove()){
                                        grilleAttack.getChildren().clear();
                                        grilleMvt.getChildren().clear();
                                        initPersonnageSelected(information, x, y,
                                                stay, affichePerso, grilleMvt, grilleAttack,
                                                move, attack);
                                        if(!AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected)) {
                                            stay.setText("Rien faire");
                                            stay.setVisible(true);
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
                    }else
                    {
                        if(AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y))!=null)
                        {
                            initPersonnageSelected(information, x, y,
                                    stay, affichePerso, grilleMvt,
                                    grilleAttack, move, attack);
                            if(!AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected)){
                                stay.setText("Rien faire");
                                stay.setVisible(true);
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

    public static void buttonStay(Button stay, Button move, Button attack, GridPane grilleMvt, GridPane grilleAttack,
                                  AfficheMap afficheMap, AffichePerso affichePerso, GridPane perso, VBox console){
        stay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stay.setVisible(false);
                move.setVisible(false);
                attack.setVisible(false);
                AffichageGraphique.group.getChildren().clear();
                grilleMvt.getChildren().clear();
                grilleAttack.getChildren().clear();
                if(personnageSelected!= null) {
                    Label lbl1 = new Label("-> fin de tour pour ");
                    lbl1.setTextFill(Color.WHITE);
                    Label lbl2 = new Label(personnageSelected.getPersonnage().getCaracteristique().getName());
                    lbl2.setTextFill(Color.BLUE);
                    HBox hbox = new HBox();
                    hbox.getChildren().addAll(lbl1, lbl2);
                    console.getChildren().add(hbox);
                    personnageSelected.setEndTurn(true);
                    personnageSelected.setOrientation(5);
                }
                personnageSelected =null;
                if(AffichePerso.endTurn())
                {
                    afficheMap.effectField(AffichePerso.listPersonnage);
                    for(PersonnageDisplay p: AffichePerso.listPersonnage)
                    {
                        p.setOrientation(0);
                        p.setBooleanAttack(false);
                        p.setBooleanMove(false);
                    }
                    if(AffichePerso.isWin())
                        System.out.println("Win");
                    if(AffichePerso.isLost())
                        System.out.println("Lost");
                    afficheMap.effectField(AffichePerso.listEnnemi);
                    Label lbl = new Label("TOUR DES ENNEMI");
                    lbl.setTextFill(Color.GREEN);
                    console.getChildren().add(lbl);
                    Event.numEnnemi = 0;
                    while(Event.numEnnemi<AffichePerso.listEnnemi.size()-1 && !AffichePerso.listEnnemi.get(Event.numEnnemi).isAlive()) {
                        if(Event.numEnnemi<AffichePerso.listEnnemi.size()-1)
                            Event.numEnnemi++;
                    }
                    if(AffichePerso.listEnnemi.get(Event.numEnnemi).isAlive())
                        AffichePerso.listEnnemi.get(Event.numEnnemi).action(affichePerso, perso, grilleMvt, afficheMap, console);
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

    private static void buttonAttack(int x, int y, VBox information, Button attack,
                                     GridPane grilleMvt, GridPane grilleAttack,
                                     Button stay, AffichePerso affichePerso, VBox console){
        ennemiSelected = AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y));
        information.getChildren().clear();
        addInformation(information, personnageSelected,"Green");
        addInformation(information, ennemiSelected,"Red");
        attack.setVisible(true);

        attack.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                attack.setVisible(false);
                printAttackAction(personnageSelected, ennemiSelected, Color.BLUE, Color.RED, console);
                int hpPersonnage = personnageSelected.getPersonnage().getCaracteristique().getHp();
                int hpEnnemi = ennemiSelected.getPersonnage().getCaracteristique().getHp();
                for(Coordinate c:affichePerso.getAttackAreaAfterMovement(ennemiSelected.getPersonnage(), ennemiSelected.getCoordinate())){
                    if(c.equal(personnageSelected.getCoordinate())) {
                        personnageSelected.getPersonnage().attack(ennemiSelected.getCoordinate());
                        personnageSelected.setBooleanAttack(true);
                        printAfterAttack(ennemiSelected, hpEnnemi, Color.RED, console);

                        printCounterAttack(personnageSelected, ennemiSelected, hpPersonnage, Color.BLUE, Color.RED, console);
                    }
                }
                if(!personnageSelected.getBooleanAttack()){
                    personnageSelected.getPersonnage().attackHorsPortee(ennemiSelected.getCoordinate());
                    personnageSelected.setBooleanAttack(true);
                    printAfterAttack(ennemiSelected, hpEnnemi, Color.RED, console);
                }
                if(personnageSelected.getPersonnage().getCaracteristique().getHp()<=0)
                    personnageSelected.setDeath();
                if(ennemiSelected.getPersonnage().getCaracteristique().getHp()<=0)
                    ennemiSelected.setDeath();
                personnageSelected.setBooleanAttack(true);
                grilleMvt.getChildren().clear();
                grilleAttack.getChildren().clear();
                information.getChildren().clear();
                if(!personnageSelected.isAlive())
                    personnageSelected = null;
                if(!ennemiSelected.isAlive())
                    ennemiSelected=null;
                if(personnageSelected != null)
                    addInformation(information, personnageSelected,"Green");
                if(ennemiSelected!=null)
                    addInformation(information, ennemiSelected,"Red");
                stay.fire();
                stay.setText("Fin");
            }
        });
    }

    public static void buttonMove(int x, int y, Button move, AffichePerso affichePerso,
                                  GridPane perso, GridPane grilleMvt, AfficheMap afficheMap,
                                  GridPane grilleAttack, Button stay, VBox console){
        move.setVisible(true);
        move.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                move.setVisible(false);
                personnageSelected.setBooleanMove(true);
                printMoveAction(personnageSelected, Color.BLUE, console);
                affichePerso.move(personnageSelected, new Coordinate(x, y), perso, grilleMvt, afficheMap, console);
                grilleMvt.getChildren().clear();
                grilleAttack.getChildren().clear();
                stay.setText("Fin");
            }
        });
    }

    private static void initPersonnageSelected(VBox information, int x, int y,
                                               Button stay, AffichePerso affichePerso,
                                               GridPane grilleMvt, GridPane grilleAttack,
                                               Button move, Button attack){
        information.getChildren().clear();
        personnageSelected = AffichePerso.getPersonnageDisplayAt(new Coordinate(x, y));
        if(personnageSelected != null){
            addInformation(information, personnageSelected, "Green");
        }
        if(personnageSelected!=null && !AffichePerso.contains(AffichePerso.listEnnemi, personnageSelected)&&
            !personnageSelected.getEndTurn()){
            for(Coordinate c: affichePerso.getCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                addRectangle(grilleMvt, c, Color.rgb(0, 0, 255, 0.3));
            for(Coordinate c:affichePerso.getAttackCoordinate(personnageSelected.getPersonnage(), personnageSelected.getCoordinate()))
                addRectangle(grilleAttack, c, Color.rgb(255, 0, 0, 0.3));
            move.setVisible(false);
            attack.setVisible(false);
            stay.setVisible(false);
        }
    }

    public static void printMoveAction(PersonnageDisplay personnage, Color color, VBox console){
        Label lbl1 = new Label("-> ");
        lbl1.setTextFill(Color.WHITE);
        Label lbl2 = new Label(personnage.getPersonnage().getCaracteristique().getName());
        lbl2.setTextFill(color);
        Label lbl3 = new Label(" se déplace");
        lbl3.setTextFill(Color.WHITE);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(lbl1, lbl2, lbl3);
        console.getChildren().add(hbox);
    }

    public static void printAttackAction(PersonnageDisplay personnage, PersonnageDisplay ennemi, Color colorPerso, Color colorEnnemi, VBox console){
        Label lbl1 = new Label("-> ");
        lbl1.setTextFill(Color.WHITE);
        Label lbl2 = new Label(personnage.getPersonnage().getCaracteristique().getName());
        lbl2.setTextFill(colorPerso);
        Label lbl3 = new Label(" attaque ");
        lbl3.setTextFill(Color.WHITE);
        Label lbl4 = new Label(ennemi.getPersonnage().getCaracteristique().getName());
        lbl4.setTextFill(colorEnnemi);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(lbl1, lbl2, lbl3, lbl4);
        console.getChildren().add(hbox);
    }

    public static void printAfterAttack(PersonnageDisplay ennemi, int hpEnnemi, Color color, VBox console){
        Label lbl1 = new Label("-> ");
        lbl1.setTextFill(Color.WHITE);
        Label lbl2 = new Label(ennemi.getPersonnage().getCaracteristique().getName());
        lbl2.setTextFill(color);
        Label lbl3;
        if(hpEnnemi==ennemi.getPersonnage().getCaracteristique().getHp())
        {
            lbl3 = new Label(" n'a subit aucun dégâts");
        }else if(!ennemi.isAlive())
            lbl3 = new Label(" est mort");
        else
            lbl3 = new Label(" a subit " + (hpEnnemi-ennemi.getPersonnage().getCaracteristique().getHp() + " dégâts"));
        lbl3.setTextFill(Color.WHITE);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(lbl1, lbl2, lbl3);
        console.getChildren().add(hbox);
    }

    public static void printCounterAttack(PersonnageDisplay personnage, PersonnageDisplay ennemi, int hpPersonnage, Color colorPerso, Color colorEnnemi, VBox console){
        Label lbl1 = new Label("-> ");
        lbl1.setTextFill(Color.WHITE);
        Label lbl2 = new Label(ennemi.getPersonnage().getCaracteristique().getName());
        lbl2.setTextFill(colorEnnemi);
        Label lbl3 = new Label(" contre attaque");
        lbl3.setTextFill(Color.WHITE);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(lbl1, lbl2, lbl3);
        console.getChildren().add(hbox);

        lbl1 = new Label("-> ");
        lbl1.setTextFill(Color.WHITE);
        lbl2 = new Label(personnage.getPersonnage().getCaracteristique().getName());
        lbl2.setTextFill(colorPerso);
        if(hpPersonnage==personnage.getPersonnage().getCaracteristique().getHp())
            lbl3 = new Label(" n'a subit aucun dégâts");
        else if(!personnage.isAlive())
            lbl3 = new Label(" est mort");
        else
            lbl3 = new Label(" a subit " + (hpPersonnage-personnage.getPersonnage().getCaracteristique().getHp()) + " dégâts");
        lbl3.setTextFill(Color.WHITE);
        hbox = new HBox();
        hbox.getChildren().addAll(lbl1, lbl2, lbl3);
        console.getChildren().add(hbox);
    }
}