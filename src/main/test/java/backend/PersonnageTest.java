package backend;

import frontend.AffichePerso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonnageTest {
    private Personnage cloud;
    private Personnage sniper;
    @BeforeEach
    void setUp() {
        cloud = new Personnage("Cloud", new Coordinate(3,3));
        sniper = new Personnage("Sniper", new Coordinate(2,1));
    }

    @Test
    void attacked() {
        Personnage def = cloud.cloner();
        Personnage attaque = sniper.cloner();
        def.attacked(4);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp() - 4);
        attaque.attacked(0);
        Assertions.assertEquals(attaque.getCaracteristique().getHp(), attaque.getCaracteristique().getMaxHp());
    }

    @Test
    void healed() {
        Personnage def = sniper.cloner();
        def.attacked(3);
        def.healed(1);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp() -2);
        def.healed(10);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp());
    }

    @Test
    void isAlive() {
        Personnage def = cloud.cloner();
        Assertions.assertTrue(def.isAlive());
        def.getCaracteristique().setHp(0);
        Assertions.assertFalse(def.isAlive());
    }

    @Test
    void cloner() {
        Personnage perso = cloud.cloner();
        Personnage clone = perso.cloner();
        Assertions.assertEquals(perso.getId(), clone.getId());
        Assertions.assertTrue(clone.getPos().equal(perso.getPos()));
        clone.setPos(new Coordinate(2,3));
        Assertions.assertFalse(clone.getPos().equal(perso.getPos()));
        clone.setCaracteristique(new Carac("Sniper"));
        Assertions.assertNotEquals(clone.getCaracteristique().getName(), perso.getCaracteristique().getName());
    }

    @Test
    void getId() {
        Personnage perso = cloud.cloner();
        perso.setId(0);
        Personnage perso2 = sniper.cloner();
        perso2.setId(1);
        Assertions.assertEquals(perso.getId(), 0);
        Assertions.assertEquals(perso2.getId(), 1);
    }

    @Test
    void setId() {
        Personnage perso = cloud.cloner();
        perso.setId(3);
        Assertions.assertEquals(perso.getId(), 3);
    }

    @Test
    void getCaracteristique() {
        Personnage perso = cloud.cloner();
        Assertions.assertEquals(perso.getCaracteristique().getName(), "Cloud");
    }

    @Test
    void setCaracteristique() {
        Personnage perso = cloud.cloner();
        perso.setCaracteristique(new Carac("Sniper"));
        Assertions.assertEquals(perso.getCaracteristique().getName(), "Sniper");
    }

    @Test
    void getPos() {
        Personnage perso = cloud.cloner();
        Assertions.assertTrue(perso.getPos().equal(new Coordinate(3,3)));
    }

    @Test
    void setPos() {
        Personnage perso = cloud.cloner();
        perso.setPos(new Coordinate(3,2));
        Assertions.assertTrue(perso.getPos().equal(new Coordinate(3,2)));
        Assertions.assertFalse(perso.getPos().equal(new Coordinate(3,3)));
    }
}