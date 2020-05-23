package IA;

import backend.Personnage;

import java.util.ArrayList;

public class HeuristiqueBasique implements Heuristique {

    @Override
    public int calculerHeuristique(Etat e) {
        int value = 0;
        int som = 0;

        for (Personnage persoG : e.getGentils()){
           value += persoG.getCaracteristique().getHp();
        }
        for (Personnage persoM : e.getListMechant()){
            som += persoM.getCaracteristique().getHp();
        }

        if(som == 0 && value == 0){
            return 0;
        }

        if(som == 0){
            return 10000;
        }

        if(value == 0){
            return -10000;
        }

        return value - som;
    }
}
