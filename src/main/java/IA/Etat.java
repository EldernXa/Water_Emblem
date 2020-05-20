package IA;

import backend.Coordinate;
import backend.field.Field;
import backend.Personnage;

import java.util.ArrayList;

public class Etat {  /*
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
        return deplacerMechant();
    }

    private ArrayList<Etat> deplacerMechant(){
        ArrayList<Etat> listDeplacement = new ArrayList<>();
        for (Personnage mechant : listMechant){
            for(Coordinate pos : mechant.getMovmentPossible()){
                Etat test = cloner();
                Personnage p = mechant.cloner();
                p.setPos(pos);
                test.listMechant.set(listMechant.indexOf(mechant), p);
                listDeplacement.add(test);
            }
        }
        System.out.println("deplacement : ");
        for (Etat e : listDeplacement){
            e.affEtat();
        }
        System.out.println("fin deplacement");
        return listDeplacement;
    }


    private ArrayList<Etat> attaqueMechant(){
        ArrayList<Etat> listAttaque = new ArrayList<>();
        for (Personnage mechant : listMechant){
            for(Coordinate pos : mechant.getAttaquePossible()){
                Etat test = this.cloner();
                for (Personnage p : gentils){
                    if(p.getPos() == pos){
                        Personnage newP = p.cloner();
                        mechant.attack(newP);
                        test.listMechant.set(test.listMechant.indexOf(p), newP);
                    }
                }
                listAttaque.add(test);
            }

        }
        return listAttaque;
    }

    public Etat cloner(){
        ArrayList<Personnage> lMechant = new ArrayList<>();
        for (Personnage p : listMechant){
            lMechant.add(p.cloner());
        }
        ArrayList<Personnage> lGentil = new ArrayList<>();
        for (Personnage p : gentils){
            lGentil.add(p.cloner());
        }
        Etat e = new Etat(lMechant, lGentil, field);

        return e;
    }

    public void affEtat(){
        System.out.println("etat : ");
        for (Personnage p :listMechant){
            System.out.print(p.getCaracteristique().getName()+ " hp : "+p.getCaracteristique().getHp());
            p.getPos().affPos();
        }
        for (Personnage p : gentils){
            System.out.print(p.getCaracteristique().getName() + " hp : "+p.getCaracteristique().getHp());
            p.getPos().affPos();
        }
        System.out.println();
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
    } */
}

