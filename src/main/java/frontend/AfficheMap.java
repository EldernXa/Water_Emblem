package frontend;

import backend.Coordinate;
import backend.data.DataMap;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class AfficheMap {

    private GridPane map;
    private DataMap dataMap;

    public AfficheMap(String nomDuFichier){
        map = new GridPane();
        dataMap = new DataMap(nomDuFichier);

        for(ArrayList<Coordinate> array: dataMap.getcoordinates()){
            for(Coordinate coordinate: array)
                map.add(new Rectangle(50, 50, coordinate.getField().getColor()), coordinate.getY(), coordinate.getX());
        }
    }

    public GridPane getMap(){
        return this.map;
    }
}
