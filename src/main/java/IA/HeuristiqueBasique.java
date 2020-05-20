package IA;

import backend.Personnage;

import java.util.ArrayList;

public class HeuristiqueBasique implements Heuristique {

    @Override
    public int calculerHeuristique(Etat e) {
        int value = 0;
        for (Personnage persoG : e.getGentils()){
           value += persoG.getCaracteristique().getHp();
        }
        for (Personnage persoM : e.getListMechant()){
            value -= persoM.getCaracteristique().getHp();
        }

        return value;
    }
}
