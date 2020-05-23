package IA;

import backend.Personnage;

public class HeuristiqueTest implements Heuristique {
    @Override
    public int calculerHeuristique(Etat e) {
        int x,y, distance = 0;
        int min = 10000;
        for (Personnage p : e.getListMechant()){
            for (Personnage g : e.getGentils()){
                x = Math.abs(p.getPos().getX() - g.getPos().getX());
                y = Math.abs(p.getPos().getY() - g.getPos().getY());
                if(min > x + y){
                    min = x + y;
                }
            }
            distance += min;
        }
        System.out.println("distance : " + distance);
        e.affEtat();
        return  distance;
    }
}
