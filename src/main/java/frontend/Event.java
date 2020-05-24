package frontend;

import IA.Action;
import IA.Algo_Minimax;
import IA.Etat;
import IA.HeuristiqueBasique;
import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
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
    static public ArrayList<Action> listAction = null;
    static public ArrayList<PersonnageDisplay> listMechantRestant = null;

    static void clickOnMap(GridPane perso, AffichePerso affichePerso, GridPane grilleMvt, GridPane grilleAttack,
                           VBox information, Button move, Button attack, Button stay, AfficheMap afficheMap, VBox console, Button endTurn){
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
                                        buttonMove(x, y, move, attack, affichePerso, perso, grilleMvt, afficheMap, grilleAttack, stay, console, endTurn);
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

    public static void buttonStay(Button stay, Button move, Button attack, Button endTurn, GridPane grilleMvt, GridPane grilleAttack,
                                  AfficheMap afficheMap, AffichePerso affichePerso, GridPane perso, VBox console,
                                  GridPane group, GridPane map, GridPane root, ChoiceBox<String> choiceMap, Button start, Label txt,
                                  VBox information, ScrollPane scrollPane, VBox panel, String nameMap){
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
                    if(AffichePerso.isWin() || AffichePerso.isLost()) {
                        Label result = null;
                        if (AffichePerso.isWin()) {
                            result = new Label("Vous avez gagne le niveau");
                            result.setTextFill(Color.BLUE);
                        }
                        else if (AffichePerso.isLost()) {
                            result = new Label("Vous avez perdu le dernier niveau");
                            result.setTextFill(Color.RED);
                        }
                        if(result!=null) {
                            result.setStyle("-fx-font-size: 25;");
                        }
                        information.getChildren().clear();
                        information.setVisible(false);
                        stay.setOnAction(null);
                        AffichePerso.listPersonnage.clear();
                        AffichePerso.listEnnemi.clear();
                        console.getChildren().clear();
                        console.setVisible(false);
                        personnageSelected = null;
                        ennemiSelected = null;
                        perso.getChildren().clear();
                        group.getChildren().clear();
                        map.getChildren().clear();
                        root.getChildren().clear();
                        scrollPane.setContent(null);
                        panel.getChildren().clear();
                        root.setVgap(20);
                        root.setHgap(20);
                        root.add(txt, 0, 0);
                        root.add(choiceMap, 0, 1);
                        root.add(start, 1, 1);
                        root.add(result, 0, 2);
                    }else {
                        afficheMap.effectField(AffichePerso.listEnnemi);
                        Label lbl = new Label("TOUR DES ENNEMI");
                        lbl.setTextFill(Color.GREEN);
                        console.getChildren().add(lbl);
                        ArrayList<Personnage> listGentil = new ArrayList<>();
                        ArrayList<Personnage> listMechant = new ArrayList<>();
                        listMechantRestant = new ArrayList<>();
                        for(PersonnageDisplay p:AffichePerso.listPersonnage)
                            if(p.isAlive())
                                listGentil.add(new Personnage(p.getPersonnage().getCaracteristique().getName(),
                                        new Coordinate(p.getCoordinate().getX(), p.getCoordinate().getY())));
                        for(PersonnageDisplay p:AffichePerso.listEnnemi)
                            if(p.isAlive()) {
                                listMechant.add(new Personnage(p.getPersonnage().getCaracteristique().getName(),
                                        new Coordinate(p.getCoordinate().getX(), p.getCoordinate().getY())));
                                listMechantRestant.add(p);
                            }
                        endTurn.setVisible(false);
                        Etat e = new Etat(listMechant, listGentil, new HeuristiqueBasique(), nameMap);
                        Etat etat = Algo_Minimax.startMini(e, 2, false);
                        listAction = new ArrayList<>();
                        for(Action a : etat.getListActionprec())
                        {
                            listAction.add(a.cloner());
                        }
                        Event.numEnnemi = 0;
                        if(listMechantRestant.size()!=0)
                            listMechantRestant.get(Event.numEnnemi).action(affichePerso, perso, grilleMvt, afficheMap, listAction.get(numEnnemi), console, endTurn);
                    }
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

    public static void buttonMove(int x, int y, Button move, Button attack, AffichePerso affichePerso,
                                  GridPane perso, GridPane grilleMvt, AfficheMap afficheMap,
                                  GridPane grilleAttack, Button stay, VBox console, Button endTurn){
        move.setVisible(true);
        attack.setVisible(false);
        move.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                move.setVisible(false);
                personnageSelected.setBooleanMove(true);
                printMoveAction(personnageSelected, Color.BLUE, console);
                affichePerso.move(personnageSelected, new Coordinate(x, y), perso, grilleMvt, afficheMap, console, endTurn);
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
        Label lbl3 = new Label(" se deplace");
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
            lbl3 = new Label(" n'a subit aucun degats");
        }else if(!ennemi.isAlive())
            lbl3 = new Label(" est mort");
        else
            lbl3 = new Label(" a subit " + (hpEnnemi-ennemi.getPersonnage().getCaracteristique().getHp() + " degats"));
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
            lbl3 = new Label(" n'a subit aucun degats");
        else if(!personnage.isAlive())
            lbl3 = new Label(" est mort");
        else
            lbl3 = new Label(" a subit " + (hpPersonnage-personnage.getPersonnage().getCaracteristique().getHp()) + " degats");
        lbl3.setTextFill(Color.WHITE);
        hbox = new HBox();
        hbox.getChildren().addAll(lbl1, lbl2, lbl3);
        console.getChildren().add(hbox);
    }
}