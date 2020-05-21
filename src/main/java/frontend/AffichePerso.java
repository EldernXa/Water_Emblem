package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import backend.data.DataCoordCharacters;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class AffichePerso {

    public static List<PersonnageDisplay> listPersonnage;
    public static List<PersonnageDisplay> listEnnemi;
    private GridPane perso;
    private DataCoordCharacters dataCoordCharacters;
    private Timeline t;
    ImageView imgView;

    public AffichePerso(String dataCoordinate){
        dataCoordCharacters = new DataCoordCharacters(dataCoordinate);
        listPersonnage = new ArrayList<>();
        listEnnemi = new ArrayList<>();
        imgView = null;

        for(int i=0;i <dataCoordCharacters.getGentilCharactersList().size(); i++)
        {
            int x = Integer.parseInt(dataCoordCharacters.getGentilCoord().get(i).get(0));
            int y = Integer.parseInt(dataCoordCharacters.getGentilCoord().get(i).get(1));
            listPersonnage.add(new PersonnageDisplay(dataCoordCharacters.getGentilCharactersList().get(i), x, y));
        }
        for(int i=0; i<dataCoordCharacters.getMechantCharactersList().size();i++)
        {
            int x=Integer.parseInt(dataCoordCharacters.getMechantCoord().get(i).get(0));
            int y=Integer.parseInt(dataCoordCharacters.getMechantCoord().get(i).get(1));
            listEnnemi.add(new PersonnageDisplay(dataCoordCharacters.getMechantCharactersList().get(i), x, y));

        }
        init();
    }

    public static boolean endTurn(){
        for(PersonnageDisplay p : listPersonnage){
            if(!p.getEndTurn() && p.isAlive())
                return false;
        }
        return true;
    }

    public static void newTurn(){
        for(PersonnageDisplay p: listPersonnage){
            p.setEndTurn(false);
            p.setBooleanAttack(false);
            p.setBooleanMove(false);
        }
    }

    public ArrayList<Coordinate> getCoordinate(Personnage personnage, Coordinate coordinate){
        return dataCoordCharacters.getMovementArea(personnage, coordinate);
    }

    public ArrayList<Coordinate> getAttackCoordinate(Personnage personnage, Coordinate coordinate){
        return dataCoordCharacters.getAttackArea(personnage, coordinate);
    }

    void init() {
        perso = new GridPane();
        perso.setAlignment(Pos.TOP_LEFT);
        perso.setVgap(AffichageGraphique.size);
        perso.setHgap(AffichageGraphique.size);
        for(int i=0; i<AfficheMap.y;i++)
        {
            perso.getColumnConstraints().add(new ColumnConstraints(0));
        }
        for(int i=0; i<AfficheMap.x;i++)
            perso.getRowConstraints().add(new RowConstraints(0));

        initListPersonnage(listPersonnage, perso);
        initListPersonnage(listEnnemi, perso);

        AffichageGraphique.group.setVgap(50);
        AffichageGraphique.group.setHgap(50);

        t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        for(PersonnageDisplay p: listPersonnage)
            p.setOrientation(0);
        for(PersonnageDisplay p : listEnnemi)
            p.setOrientation(0);
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(280),
                (ActionEvent event) -> {
                    perso.getChildren().clear();
                    initAnimation(listPersonnage);
                    initAnimation(listEnnemi);
                    initListPersonnage(listPersonnage, perso);
                    initListPersonnage(listEnnemi, perso);
                }
        ));
        t.play();

    }

    public static boolean contains(List<PersonnageDisplay> list, PersonnageDisplay personnage){
        for(PersonnageDisplay p : list)
        {
            if(p.getPersonnage().getCaracteristique().getName().compareTo(personnage.getPersonnage().getCaracteristique().getName())==0)
                return true;
        }
        return false;
    }

    public static void initListPersonnage(List<PersonnageDisplay> list, GridPane perso){
        for(PersonnageDisplay p: list){
            if(p.isPresent() && p.isAlive()) {
                p.getImageView().setFitWidth(AffichageGraphique.size);
                p.getImageView().setFitHeight(AffichageGraphique.size);
                perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
                GridPane.setHalignment(p.getImageView(), HPos.LEFT);
                GridPane.setValignment(p.getImageView(), VPos.TOP);
            }
        }
    }

    private void initAnimation(List<PersonnageDisplay> personnage){
        for(PersonnageDisplay p: personnage){
            p.getImageView().setTranslateX(0);
            p.getImageView().setTranslateY(0);
            p.nextPosition();
            p.getImageView().setTranslateX(0);
            p.getImageView().setTranslateY(0);
        }
    }

    public GridPane getGridPanePerso(){
        return perso;
    }

    public ArrayList<Coordinate> getAttackAreaAfterMovement(Personnage name, Coordinate coordinate){
        return dataCoordCharacters.getAttackAreaAfterMovement(name, coordinate);
    }

    public void move(PersonnageDisplay persoToMove, Coordinate coordinate, GridPane perso,
                     GridPane grilleMvt, AfficheMap afficheMap){
        perso.getChildren().clear(); // aucun changement si je mets en commentaire
        persoToMove.setPresent(false);

        int deplacementHorizontal = Math.abs(coordinate.getX() - persoToMove.getCoordinate().getX()) ;
        int deplacementVertical = Math.abs((coordinate.getY() -persoToMove.getCoordinate().getY()));

        TranslateTransition ttX = new TranslateTransition(Duration.millis(400*deplacementHorizontal), persoToMove.getImageView());
        ttX.setToX((coordinate.getX()-persoToMove.getCoordinate().getX())*50);
        TranslateTransition ttY = new TranslateTransition(Duration.millis(400*deplacementVertical), persoToMove.getImageView());
        ttY.setToY((coordinate.getY()-persoToMove.getCoordinate().getY())*50);
        SequentialTransition seq = new SequentialTransition(ttX, ttY);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(1),
                (ActionEvent event)->{


                    persoToMove.getImageView().setTranslateY(0);
                    persoToMove.getImageView().setTranslateX(0);
                    if(ttX.getNode().getTranslateX()<0 && ttX.getNode().getTranslateX()!=ttX.getToX() &&
                            persoToMove.getOrientation()!=5)
                        persoToMove.setOrientation(5);
                    else if(ttX.getNode().getTranslateX()>0 && ttX.getNode().getTranslateX()!=ttX.getToX()&&
                            persoToMove.getOrientation()!=4)
                        persoToMove.setOrientation(4);
                    else if(ttY.getNode().getTranslateY()<0 && ttY.getNode().getTranslateY()!=ttY.getToY()&&
                            persoToMove.getOrientation()!=1)
                        persoToMove.setOrientation(1);
                    else if(ttY.getNode().getTranslateY()>0 && ttY.getNode().getTranslateY()!=ttY.getToY()&&
                            persoToMove.getOrientation()!=3){
                        persoToMove.setOrientation(3);
                    }


                    persoToMove.getImageView().setTranslateX(0);
                    persoToMove.getImageView().setTranslateY(0);
                    // persoToMove.nextPosition();

                    AffichageGraphique.group.getChildren().clear();
                    imgView = new ImageView(persoToMove.getImageView().getImage());

                    imgView.setFitHeight(AffichageGraphique.size);
                    imgView.setFitWidth(AffichageGraphique.size);
                    AffichageGraphique.group.add(imgView, persoToMove.getCoordinate().getX(),
                            persoToMove.getCoordinate().getY());

                    imgView.setTranslateX(ttY.getNode().getTranslateX());
                    imgView.setTranslateY(ttY.getNode().getTranslateY());
                    if(ttX.getNode().getTranslateX()== ttX.getToX() &&
                            ttY.getNode().getTranslateY() == ttY.getToY()){
                        persoToMove.getImageView().setTranslateX(0);
                        persoToMove.getImageView().setTranslateY(0);
                        AffichageGraphique.group.getChildren().clear();
                        timeline.stop();
                        seq.stop();
                        timeline.getKeyFrames().clear();
                        afficheMap.disaffectField(persoToMove);
                        persoToMove.setCoordinate(coordinate);
                        persoToMove.setOrientation(0);
                        persoToMove.setPresent(true);
                        if(!persoToMove.getBooleanAttack())
                            for(Coordinate c: getAttackAreaAfterMovement(persoToMove.getPersonnage(), persoToMove.getCoordinate()))
                                Event.addRectangle(grilleMvt, c, Color.rgb(255, 0, 0, 0.3));

                    }


                }
        ));
        timeline.play();
        seq.play();
    }

    public static Personnage getPersonnageAt(Coordinate coordinate){
        for(PersonnageDisplay p: listPersonnage){
            if(p.getCoordinate().equal(coordinate) && p.isAlive())
            {
                return p.getPersonnage();
            }
        }
        for(PersonnageDisplay p:listEnnemi){
            if(p.getCoordinate().equal(coordinate) && p.isAlive())
            {
                return p.getPersonnage();
            }
        }
        return null;
    }

    public static PersonnageDisplay getPersonnageDisplayAt(Coordinate coordinate){
        for(PersonnageDisplay p: listPersonnage){
            if(p.getCoordinate().equal(coordinate) && p.isAlive())
            {
                return p;
            }
        }
        for(PersonnageDisplay p:listEnnemi){
            if(p.getCoordinate().equal(coordinate) && p.isAlive())
            {
                return p;
            }
        }
        return null;
    }

    public static boolean isWin(){
        for(PersonnageDisplay p : listEnnemi){
            if(p.isAlive())
                return false;
        }
        return true;
    }

    public static boolean isLost(){
        for(PersonnageDisplay p : listPersonnage){
            if(p.isAlive())
                return false;
        }
        return true;
    }

}
