package IA;

import backend.Coordinate;
import backend.Field;
import backend.Personnage;

import java.util.ArrayList;

public class Etat {
    private ArrayList<Personnage> listMechant;
    private Personnage gentil;
    private Field field;

    public Etat(ArrayList<Personnage> listMechant, Personnage gentil, Field field) {
        this.listMechant = listMechant;
        this.gentil = gentil;
        this.field = field;
    }

    private ArrayList<Etat> déplacerGentil(){
        ArrayList<Etat> listDeplacement = new ArrayList<>();
        for(Coordinate pos : gentil.getMovmentPossible()){
            Personnage p = gentil.cloner();
            p.setPos(pos);
            listDeplacement.add(new Etat(listMechant, p, field));
        }
        return listDeplacement;
    }


    private ArrayList<Etat> attaqueGentil(){
        ArrayList<Etat> listAttaque = new ArrayList<>();
        for(Coordinate pos : gentil.getAttaquePossible()){
            for (Personnage p : listMechant){
                if(p.getPos() == pos){
                    Personnage newP = p.cloner();
                    newP.getAttacked(gentil.getCaracteristique().getStr());
                    listMechant.set(listMechant.indexOf(p), newP);
                }
            }
            listAttaque.add(new Etat(listMechant, gentil, field));
        }
        return listAttaque;
    }
}
