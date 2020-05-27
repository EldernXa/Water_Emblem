package backend.data;

import backend.Coordinate;
import backend.field.Field;
import backend.field.Forest;
import backend.field.River;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataMapTest {

    @Test
    void getThisCoordinate() {

        DataMap dataMap = new DataMap("Map");
        Field forest = new Forest();
        Coordinate coordinate = new Coordinate(0,0);
        coordinate.setField(forest);


        Assertions.assertEquals(coordinate,dataMap.getThisCoordinate(0,0));


    }

    @Test
    void getMapLimits() {

        DataMap dataMap = new DataMap("Map");

        ArrayList<Integer>  mapLimits = new ArrayList<>();
        mapLimits.add(8);
        mapLimits.add(8);
        Assertions.assertEquals(dataMap.getMapLimits(),mapLimits);
    }

}