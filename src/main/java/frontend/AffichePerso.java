package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import backend.data.DataCoordCharacters;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class AffichePerso {
    static List<PersonnageDisplay> listPersonnage;
    static List<PersonnageDisplay> listEnnemi;
    static private GridPane perso;
    private DataCoordCharacters dataCoordCharacters;
    static HashMap<Coordinate, ImageView> listImageView;

    public AffichePerso(String dataCoordinate){
        dataCoordCharacters = new DataCoordCharacters(dataCoordinate);
        listPersonnage = new ArrayList<>();
        listEnnemi = new ArrayList<>();
        listImageView = new HashMap<>();
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

    void init() {
        perso = new GridPane();
        perso.setAlignment(Pos.TOP_LEFT);
        perso.setVgap(AffichageGraphique.size);
        perso.setHgap(AffichageGraphique.size);
        for(int i=0; i<AfficheMap.y;i++)
        {
            perso.getColumnConstraints().add(new ColumnConstraints(AfficheMap.x/2));
        }
        for(int i=0; i<AfficheMap.x;i++)
            perso.getRowConstraints().add(new RowConstraints(AfficheMap.y/2));

        for(PersonnageDisplay p: listPersonnage){
            p.getImageView().setFitWidth(AffichageGraphique.size);
            p.getImageView().setFitHeight(AffichageGraphique.size);
            perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
            GridPane.setHalignment(p.getImageView(), HPos.LEFT);
            GridPane.setValignment(p.getImageView(), VPos.TOP);
        }

        for(PersonnageDisplay p: listEnnemi){
            p.getImageView().setFitWidth(AffichageGraphique.size);
            p.getImageView().setFitHeight(AffichageGraphique.size);
            perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
            GridPane.setHalignment(p.getImageView(), HPos.LEFT);
            GridPane.setValignment(p.getImageView(), VPos.TOP);
        }

    }

    public GridPane getGridPanePerso(){
        return perso;
    }

    public void move(PersonnageDisplay persoToMove, Coordinate coordinate){
        AffichageGraphique.perso.getChildren().clear();
        persoToMove.setCoordinate(new Coordinate(coordinate.getX(), coordinate.getY()));
        for(PersonnageDisplay p: listPersonnage){
            perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
            GridPane.setHalignment(p.getImageView(), HPos.LEFT);
            GridPane.setValignment(p.getImageView(), VPos.TOP);
        }
        for(PersonnageDisplay p:listEnnemi){
            perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
            GridPane.setHalignment(p.getImageView(), HPos.LEFT);
            GridPane.setValignment(p.getImageView(), VPos.TOP);
        }
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
