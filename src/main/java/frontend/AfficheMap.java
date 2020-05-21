package frontend;

import backend.Coordinate;
import backend.Personnage;
import backend.PersonnageDisplay;
import backend.data.DataMap;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class AfficheMap {

    private GridPane map;
    private DataMap dataMap;
    public static int x;
    public static int y;

    public AfficheMap(String nomDuFichier){
        map = new GridPane();
        dataMap = new DataMap(nomDuFichier);
        x = 0;
        y = 0;

        for(ArrayList<Coordinate> array: dataMap.getcoordinates()){
            for(Coordinate coordinate: array) {
                if(coordinate.getX()>x)
                    x = coordinate.getX();
                if(coordinate.getY()>y)
                    y = coordinate.getY();
                Pane pane = coordinate.getField().getApsect();
                map.add(pane, coordinate.getX(), coordinate.getY());
                map.setStyle("-fx-grid-lines-visible: true;");
            }
        }
    }

    public void effectField(){
        for(ArrayList<Coordinate>array: dataMap.getcoordinates()){
            for(Coordinate coordinate: array)
            {
                PersonnageDisplay p = AffichePerso.getPersonnageDisplayAt(new Coordinate(coordinate.getX(), coordinate.getY()));
                if(p!=null)
                {
                    System.out.println(coordinate.getField().getClass());
                    coordinate.getField().affect(p.getPersonnage());
                }
            }
        }
    }

    public void disaffectField(PersonnageDisplay personnage){
        for(ArrayList<Coordinate> array: dataMap.getcoordinates()){
            for(Coordinate coordinate: array){
                if(coordinate.equal(personnage.getCoordinate()))
                    coordinate.getField().disaffect(personnage.getPersonnage());
            }
        }
    }

    public GridPane getMap(){
        return this.map;
    }
}
