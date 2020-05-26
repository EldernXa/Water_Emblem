package backend;

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
    }

    @Test
    void affPos() {
    }

    @Test
    void getX() {
    }

    @Test
    void getY() {
    }

    @Test
    void getField() {
    }

    @Test
    void setField() {
    }
}