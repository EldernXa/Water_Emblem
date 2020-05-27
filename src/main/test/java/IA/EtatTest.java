package IA;

import backend.Coordinate;
import backend.Personnage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EtatTest {
    Etat etat;
    @BeforeEach
    void setUp() {
        ArrayList<Personnage> gentils = new ArrayList<>();
        ArrayList<Action> listAction = new ArrayList<>();
        listAction.add(new Action(new Coordinate(3,3), new Coordinate(2,2)));
        Personnage persoG = new Personnage("Marth", new Coordinate(1,1));
        Personnage persoG1 = new Personnage("Lyn", new Coordinate(1,2));
        gentils.add(persoG);
        gentils.add(persoG1);
        ArrayList<Personnage> mechants = new ArrayList<>();
        Personnage persoM = new Personnage("Soldat", new Coordinate(5,0));
        Personnage persoM1 = new Personnage("Archer", new Coordinate(4,4));
        mechants.add(persoM);
        mechants.add(persoM1);
        etat = new Etat(mechants, gentils, new HeuristiqueAmeliorer(), "Map");
        etat.setListActionprec(listAction);
    }

    @Test
    void getToutPossibilite() {
    }

    @Test
    void cloner() {
        Etat clone = etat.cloner();

        Assertions.assertTrue(clone.getGentils().get(0).getPos().equal(etat.getGentils().get(0).getPos()));

        clone.getGentils().get(0).getPos().setX(6);
        Assertions.assertFalse(clone.getGentils().get(0).getPos().equal(etat.getGentils().get(0).getPos()));

        Assertions.assertTrue(clone.getListActionprec().get(0).getPosDepart().equal(etat.getListActionprec().get(0).getPosDepart()));

        clone.getListActionprec().set(0,new Action(new Coordinate(1,8), new Coordinate(4,9)));
        Assertions.assertFalse(clone.getListActionprec().get(0).getPosDepart().equal(etat.getListActionprec().get(0).getPosDepart()));


    }


    @Test
    void estFinal() {
        Etat clone = etat.cloner();
        clone.getGentils().clear();
        Assertions.assertTrue(clone.estFinal());

        Etat clone2 = etat.cloner();
        for (Personnage p :  clone2.getListMechant()){
            p.getCaracteristique().setHp(0);
        }

        Assertions.assertTrue(clone2.estFinal());
        Assertions.assertFalse(etat.estFinal());

    }

}