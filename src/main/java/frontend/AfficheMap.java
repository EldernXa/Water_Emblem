package frontend;

import backend.Coordinate;
import backend.data.DataMap;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class AfficheMap {

    private GridPane map;
    private DataMap dataMap;

    public AfficheMap(String nomDuFichier){
        map = new GridPane();
        dataMap = new DataMap(nomDuFichier);

        for(ArrayList<Coordinate> array: dataMap.getcoordinates()){
            for(Coordinate coordinate: array)
                map.add(coordinate.getField().getApsect(), coordinate.getY(), coordinate.getX());
        }
    }

    public GridPane getMap(){
        return this.map;
    }
}
