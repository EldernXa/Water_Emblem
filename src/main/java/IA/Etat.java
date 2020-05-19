package IA;

import backend.Coordinate;
import backend.field.Field;
import backend.Personnage;

import java.util.ArrayList;

public class Etat {
    private ArrayList<Personnage> listMechant;
    private ArrayList<Personnage> gentils;
    private Field field;
    private int valHeuristique;
    static Heuristique heuristique;

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, Field field) {
        this.listMechant = listMechant;
        this.gentils = gentils;
        this.field = field;
        valHeuristique = heuristique.calculerHeuristique(this);
    }

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, Field field, Heuristique h) {
        this.listMechant = listMechant;
        this.gentils = gentils;
        this.field = field;
        heuristique = h;
        valHeuristique = heuristique.calculerHeuristique(this);
    }
    public ArrayList<Etat> getToutPossibilite(boolean gentilJoue){
        return new ArrayList<>();
    }

    private ArrayList<Etat> deplacerGentil(){
        ArrayList<Etat> listDeplacement = new ArrayList<>();
        for (Personnage gentil : gentils){
            for(Coordinate pos : gentil.getMovmentPossible()){
                Personnage p = gentil.cloner();
                p.setPos(pos);
                gentils.set(gentils.indexOf(gentil), p);
                listDeplacement.add(new Etat(listMechant, gentils, field));
            }
        }
        return listDeplacement;
    }


    private ArrayList<Etat> attaqueGentil(){
        ArrayList<Etat> listAttaque = new ArrayList<>();
        for (Personnage gentil : gentils){
            for(Coordinate pos : gentil.getAttaquePossible()){
                for (Personnage p : listMechant){
                    if(p.getPos() == pos){
                        Personnage newP = p.cloner();
                        //newP.getAttacked(gentil.getCaracteristique().getStr());
                        listMechant.set(listMechant.indexOf(p), newP);
                    }
                }
                listAttaque.add(new Etat(listMechant, gentils, field));
            }

        }
        return listAttaque;
    }

    public boolean estFinal(){
        return false;
    }

    public int getValHeuristique() {
        return valHeuristique;
    }

    public void setValHeuristique(int valHeuristique) {
        this.valHeuristique = valHeuristique;
    }

    public ArrayList<Personnage> getListMechant() {
        return listMechant;
    }

    public void setListMechant(ArrayList<Personnage> listMechant) {
        this.listMechant = listMechant;
    }

    public ArrayList<Personnage> getGentils() {
        return gentils;
    }

    public void setGentils(ArrayList<Personnage> gentils) {
        this.gentils = gentils;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}

