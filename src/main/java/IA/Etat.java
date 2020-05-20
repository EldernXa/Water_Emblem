package IA;

import backend.Coordinate;
import backend.data.DataCoordCharacters;
import backend.field.Field;
import backend.Personnage;

import java.util.ArrayList;

public class Etat {
    private ArrayList<Personnage> listMechant;
    private ArrayList<Personnage> gentils;
    private Field field;
    private int valHeuristique;
    static Heuristique heuristique;
    private static DataCoordCharacters dataCoordCharacters;

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, Field field) {
        this.listMechant = listMechant;
        this.gentils = gentils;
        this.field = field;
        valHeuristique = heuristique.calculerHeuristique(this);
    }

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, Field field, Heuristique h , String map) {
        dataCoordCharacters = new DataCoordCharacters(map);
        this.listMechant = listMechant;
        this.gentils = gentils;
        this.field = field;
        heuristique = h;
        valHeuristique = heuristique.calculerHeuristique(this);
    }
    public ArrayList<Etat> getToutPossibilite(boolean gentilJoue){
        if (gentilJoue){
            return deplacerGentil();
        }
        else {
            return deplacerMechant();
        }
    }
    private ArrayList<Etat> deplacerGentil(){
        ArrayList<Etat> listDeplacement = new ArrayList<>();
        for (Personnage gentil : gentils){
            for(Coordinate pos : dataCoordCharacters.getMovementArea(gentil, gentil.getPos())){
                if(persoAt(pos) == -1){
                    Etat etatDep = cloner();
                    Personnage persoG = gentil.cloner();
                    persoG.setPos(pos);
                    etatDep.gentils.set(gentils.indexOf(gentil), persoG);
                    listDeplacement.add(etatDep);
                    for(Coordinate coor : dataCoordCharacters.getAttackAreaAfterMovement(persoG, persoG.getPos())){
                        int index = mechantAt(coor);
                        if (index != -1){
                            Etat etatDepAtt = etatDep.cloner();
                            Personnage persoM = listMechant.get(index);
                            Personnage newPersoM = persoM.cloner();
                            persoG.attack(newPersoM);
                            etatDepAtt.listMechant.set(index, newPersoM);
                            listDeplacement.add(etatDepAtt);
                        }
                    }
                }
            }
        }
        return listDeplacement;
    }

    private ArrayList<Etat> deplacerMechant(){
        ArrayList<Etat> listDeplacement = new ArrayList<>();
        for (Personnage mechant : listMechant){
            for(Coordinate pos : dataCoordCharacters.getMovementArea(mechant, mechant.getPos())){

                if(persoAt(pos) == -1){
                    Etat etatDep = cloner();
                    Personnage persoM = mechant.cloner();
                    persoM.setPos(pos);
                    etatDep.listMechant.set(listMechant.indexOf(mechant), persoM);
                    listDeplacement.add(etatDep);

                    for(Coordinate coor : dataCoordCharacters.getAttackAreaAfterMovement(persoM, persoM.getPos())){
                        int index = gentilAt(coor);
                        if (index != -1){
                            Etat etatDepAtt = etatDep.cloner();
                            Personnage persoG = etatDep.gentils.get(index);
                            Personnage newPersoG = persoG.cloner();
                            persoM.attack(newPersoG);
                            etatDepAtt.gentils.set(index, newPersoG);
                            listDeplacement.add(etatDepAtt);

                        }
                    }
                }
            }
        }
        /*
        System.out.println("deplacement : ");
        for (Etat e : listDeplacement){
            e.affEtat();
        }
        System.out.println("fin deplacement");*/
        return listDeplacement;
    }
/*

    private ArrayList<Etat> attaqueMechant(){
        ArrayList<Etat> listAttaque = new ArrayList<>();
        for (Personnage mechant : listMechant){
            for(Coordinate pos : dataCoordCharacters.getMovementArea(mechant, mechant.getPos())){
                if(gentilAt(pos)){
                    Etat test = this.cloner();
                    Personnage p = getPersoAt(pos);
                    Personnage newP = p.cloner();
                    mechant.attack(newP);
                    test.listMechant.set(test.listMechant.indexOf(p), newP);
                    listAttaque.add(test);
                }
            }
        }
        return listAttaque;
    }
*/
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

    private int persoAt(Coordinate pos){
        if(mechantAt(pos) != -1){
            return mechantAt(pos);
        }
        else return gentilAt(pos);
    }
    private int mechantAt(Coordinate pos){
        for (int i = 0; i < listMechant.size(); i++){
            if(listMechant.get(i).getPos().equal(pos)){
                return i;
            }
        }
        return -1;
    }

    private int gentilAt(Coordinate pos){
        for (int i = 0; i < gentils.size(); i++){
            if(gentils.get(i).getPos().equal(pos)){
                return i;
            }
        }
        return -1;
    }

    private Personnage getPersoAt(Coordinate pos){
        for (Personnage p : listMechant){
            if (p.getPos().equal(pos)){
                return p;
            }
        }
        for (Personnage p : gentils){
            if (p.getPos().equal(pos)){
                return p;
            }
        }
        return null;
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

