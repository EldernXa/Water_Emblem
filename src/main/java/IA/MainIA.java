package IA;


import backend.Coordinate;
import backend.Personnage;
import backend.data.DataCoordCharacters;
import backend.field.Forest;

import java.util.ArrayList;

public class MainIA {
    public static void main(String[] args) {

        ArrayList<Personnage> LG = new ArrayList<>();
        Personnage persoG = new Personnage("Marth", new Coordinate(1,1));
        Personnage persoG1 = new Personnage("Lyn", new Coordinate(1,2));
        persoG.isAlive();
        LG.add(persoG);
        LG.add(persoG1);
        ArrayList<Personnage> LM = new ArrayList<>();
        Personnage persoM = new Personnage("Soldat", new Coordinate(5,0));
        Personnage persoM1 = new Personnage("Archer", new Coordinate(4,4));
        LM.add(persoM);
        LM.add(persoM1);
        System.out.println("quoi                             " + persoM.getCaracteristique().getName());
        Etat e = new Etat(LM, LG, new HeuristiqueAmeliorer(), "Map");
        /*DataCoordCharacters dataCoordCharacters = new DataCoordCharacters("Map");
        for(Coordinate c : dataCoordCharacters.getMovementArea(persoG, persoG.getPos())){
            c.affPos();
        }*/
        Etat etat = Algo_Minimax.startMini(e,3, false);
        e.affEtat();
        etat.affEtat();
        for (Action a : etat.getListActionprec()){
            a.getPosDepart().affPos();
            a.getPosArrive().affPos();
            a.getPosAttaquant().affPos();
            a.getPosDefenceur().affPos();
            System.out.println(a.getDamage());
            System.out.println();
        }


        //etat.getListMechant().get(0).setPos(new Coordinate(7,7));
        //etat.affEtat();
        //e.affEtat();


    }
}
