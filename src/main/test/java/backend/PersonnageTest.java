package backend;

import frontend.AffichePerso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PersonnageTest {

    @Test
    void attack() {
    }

    @Test
    void attackHorsPortee() {
    }

    @Test
    void attack1() {
        Personnage attaque = new Personnage("Marth");
        Personnage def = new Personnage("Chevalier");
        int damage = Stat.damageAfterCalc(attaque.getCaracteristique(), def.getCaracteristique());
        attaque.attack(def);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp() - damage);

    }

    @Test
    void attacked() {
        Personnage def = new Personnage("Chevalier");
        Personnage marth = new Personnage("Marth");
        def.attacked(4);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp() - 4);
        marth.attacked(0);
        Assertions.assertEquals(marth.getCaracteristique().getHp(), marth.getCaracteristique().getMaxHp());
    }

    @Test
    void healed() {
        Personnage def = new Personnage("Chevalier");
        def.attacked(3);
        def.healed(1);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp() -2);
        def.healed(10);
        Assertions.assertEquals(def.getCaracteristique().getHp(), def.getCaracteristique().getMaxHp());
    }

    @Test
    void isAlive() {
        Personnage def = new Personnage("Chevalier");
        Assertions.assertTrue(def.isAlive());
        def.getCaracteristique().setHp(0);
        Assertions.assertFalse(def.isAlive());
    }

    @Test
    void cloner() {
        Personnage perso = new Personnage("Marth");
        Personnage clone = perso.cloner();
        Assertions.assertEquals(perso.getId(), clone.getId());
        Assertions.assertTrue(clone.getPos().equal(perso.getPos()));
        clone.setPos(new Coordinate(2,3));
        Assertions.assertFalse(clone.getPos().equal(perso.getPos()));
        clone.setCaracteristique(new Carac("Chevalier"));
        Assertions.assertNotEquals(clone.getCaracteristique().getName(), perso.getCaracteristique().getName());
    }

    @Test
    void getId() {
        Personnage perso = new Personnage("Marth");
        perso.setId(0);
        Personnage perso2 = new Personnage("Marth");
        perso2.setId(1);
        Assertions.assertEquals(perso.getId(), 0);
        Assertions.assertEquals(perso2.getId(), 1);
    }

    @Test
    void setId() {
        Personnage perso = new Personnage("Marth");
        perso.setId(3);
        Assertions.assertEquals(perso.getId(), 3);
    }

    @Test
    void getCaracteristique() {
        Personnage perso = new Personnage("Marth");
        Assertions.assertEquals(perso.getCaracteristique().getName(), "Marth");
    }

    @Test
    void setCaracteristique() {
        Personnage perso = new Personnage("Marth");
        perso.setCaracteristique(new Carac("Chevalier"));
        Assertions.assertEquals(perso.getCaracteristique().getName(), "Chevalier");
    }

    @Test
    void getPos() {
        Personnage perso = new Personnage("Marth", new Coordinate(3,3));
        Assertions.assertTrue(perso.getPos().equal(new Coordinate(3,3)));
    }

    @Test
    void setPos() {
        Personnage perso = new Personnage("Marth");
        perso.setPos(new Coordinate(3,2));
        Assertions.assertTrue(perso.getPos().equal(new Coordinate(3,2)));
    }
}