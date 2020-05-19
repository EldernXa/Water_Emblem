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
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class AffichePerso {
    static List<PersonnageDisplay> listPersonnage;
    static List<PersonnageDisplay> listEnnemi;
    private GridPane perso;
    private DataCoordCharacters dataCoordCharacters;
    static ImageView imgView;
    private Timeline t;

    public AffichePerso(String dataCoordinate){
        dataCoordCharacters = new DataCoordCharacters(dataCoordinate);
        listPersonnage = new ArrayList<>();
        listEnnemi = new ArrayList<>();

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

        imgView = new ImageView(listPersonnage.get(0).getImageView().getImage());
        imgView.setFitHeight(AffichageGraphique.size);
        imgView.setFitWidth(AffichageGraphique.size);
        AffichageGraphique.group.setVgap(50);
        AffichageGraphique.group.setHgap(50);

        t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        for(PersonnageDisplay p: listPersonnage)
            p.setOrientation(0);
        for(PersonnageDisplay p : listEnnemi)
            p.setOrientation(0);
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(200),
                (ActionEvent event) -> {
                    perso.getChildren().clear();
                    for(PersonnageDisplay p: listPersonnage){
                        p.nextPosition();
                    }
                    for(PersonnageDisplay p:listEnnemi){
                        p.nextPosition();
                    }
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
            if(p.isPresent()) {
                p.getImageView().setFitWidth(AffichageGraphique.size);
                p.getImageView().setFitHeight(AffichageGraphique.size);
                perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
                GridPane.setHalignment(p.getImageView(), HPos.LEFT);
                GridPane.setValignment(p.getImageView(), VPos.TOP);
            }
        }
    }

    public GridPane getGridPanePerso(){
        return perso;
    }

    public void move(PersonnageDisplay persoToMove, Coordinate coordinate, GridPane perso){
        perso.getChildren().clear();
        persoToMove.setPresent(false);
        TranslateTransition ttX = new TranslateTransition(Duration.millis(600), persoToMove.getImageView());
        ttX.setToX((coordinate.getX()-persoToMove.getCoordinate().getX())*50);
        TranslateTransition ttY = new TranslateTransition(Duration.millis(600), persoToMove.getImageView());
        ttY.setToY((coordinate.getY()-persoToMove.getCoordinate().getY())*50);
        SequentialTransition seq = new SequentialTransition(ttX, ttY);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(200),
                (ActionEvent event)->{
                    if(ttX.getNode().getTranslateX()<0 && ttX.getNode().getTranslateX()!=ttX.getToX())
                        persoToMove.setOrientation(5);
                    else if(ttX.getNode().getTranslateX()>0 && ttX.getNode().getTranslateX()!=ttX.getToX())
                        persoToMove.setOrientation(4);
                    else if(ttY.getNode().getTranslateY()<0 && ttY.getNode().getTranslateY()!=ttY.getToY())
                        persoToMove.setOrientation(1);
                    else if(ttY.getNode().getTranslateY()>0 && ttY.getNode().getTranslateY()!=ttY.getToY())
                        persoToMove.setOrientation(3);

                    persoToMove.nextPosition();
                    AffichageGraphique.group.getChildren().clear();
                    ImageView imgView = new ImageView(persoToMove.getImageView().getImage());
                    imgView.setFitHeight(AffichageGraphique.size);
                    imgView.setFitWidth(AffichageGraphique.size);
                    AffichageGraphique.group.add(imgView, persoToMove.getCoordinate().getX(),
                            persoToMove.getCoordinate().getY());
                    imgView.setTranslateX(ttY.getNode().getTranslateX());
                    imgView.setTranslateY(ttY.getNode().getTranslateY());
                    if(ttX.getNode().getTranslateX()== ttX.getToX() &&
                    ttY.getNode().getTranslateY() == ttY.getToY()){
                        AffichageGraphique.group.getChildren().clear();
                        timeline.stop();
                        seq.stop();
                        timeline.getKeyFrames().clear();
                        persoToMove.setCoordinate(coordinate);
                        persoToMove.setPresent(true);
                        persoToMove.setOrientation(0);
                    }
                }
        ));
        timeline.play();
        seq.play();
    }

    public static Personnage getPersonnageAt(Coordinate coordinate){
        for(PersonnageDisplay p: listPersonnage){
            if(p.getCoordinate().equal(coordinate))
            {
                return p.getPersonnage();
            }
        }
        for(PersonnageDisplay p:listEnnemi){
            if(p.getCoordinate().equal(coordinate))
            {
                return p.getPersonnage();
            }
        }
        return null;
    }

    public static PersonnageDisplay getPersonnageDisplayAt(Coordinate coordinate){
        for(PersonnageDisplay p: listPersonnage){
            if(p.getCoordinate().equal(coordinate))
            {
                return p;
            }
        }
        for(PersonnageDisplay p:listEnnemi){
            if(p.getCoordinate().equal(coordinate))
            {
                return p;
            }
        }
        return null;
    }

}
