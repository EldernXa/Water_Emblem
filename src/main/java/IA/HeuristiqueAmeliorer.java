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
        /*
        for (Personnage p : e.getListMechant()){
            for (Personnage g : e.getGentils()){
                x = Math.abs(p.getPos().getX() - g.getPos().getX());
                y = Math.abs(p.getPos().getY() - g.getPos().getY());
                if(min > x + y){
                    min = x + y;
                }
            }
            distance += min;
        }*/

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


    public Etat meilleurEtat(ArrayList<Etat> list){

        Etat meilleur = list.get(0);
        for (Etat e : list){
            if(meilleur.valDistance() > e.valDistance()){
                meilleur = e;
            }
        }
        return meilleur;
    }
}
