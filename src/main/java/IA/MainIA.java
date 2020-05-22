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
        Personnage persoM = new Personnage("Chevalier", new Coordinate(4,1));
        Personnage persoM1 = new Personnage("Cavalier", new Coordinate(4,4));
        LM.add(persoM);
        LM.add(persoM1);

        Etat e = new Etat(LM, LG, new HeuristiqueBasique(), "Map");
        /*DataCoordCharacters dataCoordCharacters = new DataCoordCharacters("Map");
        for(Coordinate c : dataCoordCharacters.getMovementArea(persoG, persoG.getPos())){
            c.affPos();
        }*/
        Etat etat = Algo_Minimax.startMini(e,3, true);
        Etat t = Algo_Minimax.startMini(etat,3, false);
        e.affEtat();
        etat.affEtat();
        Action a = etat.getActionPrecedent();
        a.getPosDepart().affPos();
        a.getPosArrive().affPos();
        a.getPosAttaquant().affPos();
        a.getPosDefenceur().affPos();
        System.out.println(a.getDamage());


        t.affEtat();
        Action ab = t.getActionPrecedent();
        ab.getPosDepart().affPos();
        ab.getPosArrive().affPos();
        ab.getPosAttaquant().affPos();
        ab.getPosDefenceur().affPos();
        System.out.println(ab.getDamage());
        //etat.getListMechant().get(0).setPos(new Coordinate(7,7));
        //etat.affEtat();
        //e.affEtat();


    }
}
