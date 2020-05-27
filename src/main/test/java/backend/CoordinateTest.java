package backend;

import backend.field.Forest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    Coordinate coordinate;
    @BeforeEach
    void setUp() {
        coordinate = new Coordinate(3,3);
    }

    @Test
    void equal() {
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

        Coordinate clone = coordinate.cloner();
        Assertions.assertTrue(coordinate.equal(clone));

        clone.setX(2);
        Assertions.assertFalse(coordinate.equal(clone));
    }


    @Test
    void getX() {
        Assertions.assertEquals(coordinate.getX(),3);
    }

    @Test
    void getY() {
        Assertions.assertEquals(coordinate.getY(),3);
    }

    @Test
    void distanceEntre() {
        int distance = coordinate.distanceEntre(new Coordinate(3,6));
        Assertions.assertEquals(3,distance );

        coordinate.setX(1);
        distance = coordinate.distanceEntre(new Coordinate(2,4));
        Assertions.assertEquals(2,distance );
    }
}