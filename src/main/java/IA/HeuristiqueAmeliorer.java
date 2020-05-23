package IA;

import backend.Personnage;

import java.util.ArrayList;

public class HeuristiqueAmeliorer implements Heuristique {

    @Override
    public int calculerHeuristique(Etat e) {
        int value = 0;
        int som = 0;
        int x;
        int y;
        int min = 0;
        int valFinal = 0;
        for (Personnage persoG : e.getGentils()){
            value += persoG.getCaracteristique().getHp();
            for (Personnage p : e.getListMechant()){
                x = Math.abs(p.getPos().getX() - persoG.getPos().getX());
                y = Math.abs(p.getPos().getY() - persoG.getPos().getY());
                if(min > x + y){
                    min = x + y;
                }
            }
        }
        valFinal += 50 - min ;
        min = 0;
        for (Personnage persoM : e.getListMechant()){
            som += persoM.getCaracteristique().getHp();
            for (Personnage p : e.getListMechant()){
                x = Math.abs(p.getPos().getX() - persoM.getPos().getX());
                y = Math.abs(p.getPos().getY() - persoM.getPos().getY());
                if(min > x + y){
                    min = x + y;
                }
            }
        }
        valFinal += 50 - min;
        if(som == 0 && value == 0){
            return 0;
        }
        if(som == 0){
            return 10000;
        }
        if(value == 0){
            return -10000;
        }
        valFinal += value - som;

        return valFinal;
    }
}
