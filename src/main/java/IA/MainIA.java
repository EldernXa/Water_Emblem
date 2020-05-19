package IA;


import backend.Coordinate;
import backend.Personnage;
import backend.field.Forest;

import java.util.ArrayList;

public class MainIA {
    public static void main(String[] args) {
        ArrayList<Personnage> LG = new ArrayList<>();
        Personnage persoG = new Personnage("Marth", new Coordinate(1,1));
        Personnage persoG1 = new Personnage("Lyn", new Coordinate(1,2));
        LG.add(persoG);
        LG.add(persoG1);
        ArrayList<Personnage> LM = new ArrayList<>();
        Personnage persoM = new Personnage("Chevalier", new Coordinate(5,5));
        Personnage persoM1 = new Personnage("Cavalier", new Coordinate(5,6));
        LM.add(persoM);
        LM.add(persoM1);

        Etat e = new Etat(LM, LG, new Forest(), new HeuristiqueBasique());
        e.affEtat();
        Etat etat = Algo_Minimax.startMini(e,2, true);
        etat.affEtat();
        //etat.getListMechant().get(0).setPos(new Coordinate(7,7));
        //etat.affEtat();
        //e.affEtat();


    }
}
