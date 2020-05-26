package backend;

import backend.field.Forest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void equal() {
        Coordinate coordinate = new Coordinate(3,3);
        Coordinate coor = new Coordinate(3,3);
        Coordinate troisDeux = new Coordinate(3,2);
        Coordinate deuxTrois = new Coordinate(2,3);
        Coordinate deuxDeux = new Coordinate(2,2);

        Assertions.assertTrue(coordinate.equal(coor));
        Assertions.assertFalse(coordinate.equal(troisDeux));
        Assertions.assertFalse(coordinate.equal(deuxDeux));
        Assertions.assertFalse(coordinate.equal(deuxTrois));

    }

    @Test
    void cloner() {
        Coordinate coordinate = new Coordinate(3,3);
        Coordinate clone = coordinate.cloner();
        Assertions.assertTrue(coordinate.equal(clone));

        clone.setX(2);
        Assertions.assertFalse(coordinate.equal(clone));
    }


    @Test
    void getX() {
        Coordinate coordinate = new Coordinate(3,2);
        Assertions.assertEquals(coordinate.getX(),3);
    }

    @Test
    void getY() {
        Coordinate coordinate = new Coordinate(3,2);
        Assertions.assertEquals(coordinate.getY(),2);
    }

}