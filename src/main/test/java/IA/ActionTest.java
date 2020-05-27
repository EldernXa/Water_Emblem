package IA;

import backend.Coordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    Action action;
    @BeforeEach
    void setUp() {
        action = new Action(new Coordinate(1,1), new Coordinate(2,2), new Coordinate(3,3), 7);
    }

    @Test
    void cloner() {
        Action clone = action.cloner();
        Assertions.assertTrue(action.getPosDepart().equal(clone.getPosDepart()));
        Assertions.assertTrue(action.getPosDefenceur().equal(clone.getPosDefenceur()));
        Assertions.assertEquals(action.getDamage(), clone.getDamage());

        clone.setPosDepart(new Coordinate(3,4));
        clone.setDamage(5);
        Assertions.assertFalse(action.getPosDepart().equal(clone.getPosDepart()));
        Assertions.assertNotEquals(action.getDamage(), clone.getDamage());
    }

    @Test
    void getDistanceAttaque() {
        int distance = action.getDistanceAttaque();
        Assertions.assertEquals(distance, 2);
        action.setPosDefenceur(new Coordinate(2,3));
        distance = action.getDistanceAttaque();
        Assertions.assertEquals(distance, 1);
        action.setPosDefenceur(new Coordinate(2,4));
        distance = action.getDistanceAttaque();
        Assertions.assertEquals(distance, 2);
    }

}