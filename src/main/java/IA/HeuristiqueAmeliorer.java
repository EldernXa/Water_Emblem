package IA;

import backend.Personnage;

import java.util.ArrayList;

public class HeuristiqueAmeliorer implements Heuristique {

    @Override
    public int calculerHeuristique(Etat e) {
        int x,y ;
        int min = 10000;
        int distance = 0;
        int valueG = 0;
        int valueM = 0;

        distance = e.valDistance();
        for (Personnage persoG : e.getGentils()){
            valueG += persoG.getCaracteristique().getHp();
        }

        for (Personnage persoM : e.getListMechant()){
            valueM += persoM.getCaracteristique().getHp();
        }

        if(valueG == 0 && valueM == 0){
            return 0;
        }
        if(valueM == 0){
            return 10000;
        }
        if(valueG == 0){
            return -10000;
        }

        return valueG - valueM + distance;
    }



}
