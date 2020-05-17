package frontend;

import backend.Coordinate;
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
        map.setVgap(AffichageGraphique.size);
        map.setHgap(AffichageGraphique.size);
        x = 0;
        y = 0;

        for(ArrayList<Coordinate> array: dataMap.getcoordinates()){
            for(Coordinate coordinate: array) {
                if(coordinate.getX()>x)
                    x = coordinate.getX();
                if(coordinate.getY()>y)
                    y = coordinate.getY();
                Pane pane = coordinate.getField().getApsect();
                map.add(pane, coordinate.getY(), coordinate.getX());
                map.setStyle("-fx-grid-lines-visible: true;");
            }
        }

        for(int i=0; i<AfficheMap.y;i++)
        {
            map.getColumnConstraints().add(new ColumnConstraints(0));
        }
        for(int i=0; i<AfficheMap.x;i++)
            map.getRowConstraints().add(new RowConstraints(0));
    }

    public GridPane getMap(){
        return this.map;
    }
}
