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
        LG.add(persoG);
        LG.add(persoG1);
        ArrayList<Personnage> LM = new ArrayList<>();
        Personnage persoM = new Personnage("Chevalier", new Coordinate(1,1));
        Personnage persoM1 = new Personnage("Cavalier", new Coordinate(4,4));
        LM.add(persoM);
        LM.add(persoM1);

        Etat e = new Etat(LM, LG, new Forest(), new HeuristiqueBasique(), "Map");
        /*DataCoordCharacters dataCoordCharacters = new DataCoordCharacters("Map");
        for(Coordinate c : dataCoordCharacters.getMovementArea(persoG, persoG.getPos())){
            c.affPos();
        }*/
        e.affEtat();
        Etat etat = Algo_Minimax.startMini(e,3, true);
        etat.affEtat();
        //etat.getListMechant().get(0).setPos(new Coordinate(7,7));
        //etat.affEtat();
        //e.affEtat();


    }
}
