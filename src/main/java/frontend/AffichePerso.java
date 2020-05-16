package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import backend.data.DataCoordCharacters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AffichePerso {
    static List<PersonnageDisplay> listPersonnage;
    static List<PersonnageDisplay> listEnnemi;
    private GridPane perso;
    private DataCoordCharacters dataCoordCharacters;

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

    void init() {
        perso = new GridPane();
        perso.setVgap(50);
        perso.setHgap(50);

        for(PersonnageDisplay p: listPersonnage){
            p.getImageView().setFitWidth(50);
            p.getImageView().setFitHeight(50);
            perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
        }

        for(PersonnageDisplay p: listEnnemi){
            p.getImageView().setFitWidth(50);
            p.getImageView().setFitHeight(50);
            perso.add(p.getImageView(), p.getCoordinate().getX(), p.getCoordinate().getY());
        }
/*
        try {
            FileInputStream inputStream = new FileInputStream("./src/main/resources/spritesPersos/SrpriteEliwwod/EliwoodAvant1.png");
            Image img = new Image(inputStream);
            imgView = new ImageView(img);
            imgView.setFitHeight(50);
            imgView.setFitWidth(50);
            perso.add(imgView, 0, 0);
        }catch(FileNotFoundException exception){
            System.out.println("Image non existant.");
        }*/
    }

    public GridPane getGridPanePerso(){
        return perso;
    }

    public void move(PersonnageDisplay persoToMove, Coordinate coordinate){
        GridPane.setColumnIndex(persoToMove.getImageView(), coordinate.getX());
        GridPane.setRowIndex(persoToMove.getImageView(), coordinate.getY());
    }

    public static Personnage getPersonnageAt(Coordinate coordinate){
        for(PersonnageDisplay p: listPersonnage){
            if(p.getCoordinate().equal(coordinate))
            {
                return p.getPersonnage();
            }
        }
        for(PersonnageDisplay p:listEnnemi){
            if(p.getCoordinate().equals(coordinate))
            {
                return p.getPersonnage();
            }
        }
        return null;
    }

}
