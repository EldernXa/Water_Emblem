package IA;

import backend.Coordinate;
import backend.data.DataCoordCharacters;
import backend.field.Field;
import backend.Personnage;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

public class Etat {
    private ArrayList<Personnage> listMechant;
    private ArrayList<Personnage> gentils;
    private int valHeuristique;
    static Heuristique heuristique;
    private static DataCoordCharacters dataCoordCharacters;
    private Action actionPrecedent;
    private ArrayList<Action> listActionprec;

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, ArrayList<Action> listActionPrecedent) {
        this.listActionprec = listActionPrecedent;
        this.listMechant = listMechant;
        this.gentils = gentils;
        valHeuristique = heuristique.calculerHeuristique(this);
    }

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, Heuristique h , String map) {
        dataCoordCharacters = new DataCoordCharacters(map);
        this.listMechant = listMechant;
        this.gentils = gentils;
        heuristique = h;
        valHeuristique = heuristique.calculerHeuristique(this);
        //actionPrecedent = new Action();
        listActionprec = new ArrayList<>();
    }
    public ArrayList<Etat> getToutPossibilite(boolean gentilJoue){
        /* if (gentilJoue){
            return deplacerGentil();
        }
        else {
            return deplacerMechant();
        }*/
        ArrayList<Etat> list = new ArrayList<>();
        ArrayList<Etat> l = new ArrayList<>();
        list.add(this.cloner());
        if (gentilJoue){
            Etat e = this.cloner();
            for(Personnage p : gentils){
                for (Etat t : (ArrayList<Etat> )list.clone()){
                    l.addAll(t.deplacerUnCamp(p.getId()));
                    l.addAll(t.attaqueUnCamp(p.getId()));
                    l.add(t);
                }
                list = l;
            }
        }
        else {
            Etat e = this;
            for(Personnage p : listMechant){
                for (Etat t : (ArrayList<Etat>) list.clone()){
                    //ArrayList<Etat> etats = t.cloner().deplacerUnCamp(p.getId());
                    l.addAll(t.deplacerUnCamp(p.getId()));
                    l.addAll(t.attaqueUnCamp(p.getId()));
                    l.add(t);
                }
                list = l;
            }
        }
        //list.addAll(attaqueUnCamp(gentilJoue));
        return list;
    }
    /*
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

        System.out.println("deplacement : ");
        for (Etat e : listDeplacement){
            e.affEtat();
        }
        System.out.println("fin deplacement");
        return listDeplacement;
    }*/

    private ArrayList<Etat> deplacerUnCamp(boolean equipeGentil){
        ArrayList<Etat> listToutDeplacement = new ArrayList<>();
        ArrayList<Personnage> listAttaqe ;
        ArrayList<Personnage> listDef ;
        if(equipeGentil){
            listAttaqe = gentils;
            listDef = listMechant;
        }
        else {
            listAttaqe = listMechant;
            listDef = gentils;
        }
        for (Personnage persoAttaque : listAttaqe){
            for(Coordinate pos : dataCoordCharacters.getMovementArea(persoAttaque, persoAttaque.getPos())){
                if (persoAt(pos) == -1){
                    Etat etatDep = cloner();
                    Personnage persoA = persoAttaque.cloner();
                    persoA.setPos(pos);

                    if(equipeGentil){
                        etatDep.gentils.set(gentils.indexOf(persoAttaque), persoA);
                    }
                    else {
                        etatDep.listMechant.set(listMechant.indexOf(persoAttaque), persoA);
                    }
                    //etatDep.setActionPrecedent(new Action(persoAttaque.getPos().cloner(), pos.cloner()));
                    etatDep.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner()));

                    listToutDeplacement.add(etatDep);

                    for(Coordinate coor : dataCoordCharacters.getAttackAreaAfterMovement(persoA, persoA.getPos())){
                        int index;
                        if(equipeGentil){
                            index = mechantAt(coor);
                        }
                        else {
                            index = gentilAt(coor);
                        }
                        if (index != -1){
                            Etat etatDepAtt = etatDep.cloner();
                            Personnage persoDef = listDef.get(index);
                            Personnage newPersoDef = persoDef.cloner();
                            persoA.attack(newPersoDef);
                            int damage = persoDef.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                            if(equipeGentil){
                                etatDepAtt.listMechant.set(index, newPersoDef);
                            }
                            else {
                                etatDepAtt.gentils.set(index, newPersoDef);
                            }
                            //etatDepAtt.setActionPrecedent(new Action(persoAttaque.getPos().cloner(), pos.cloner(), coor.cloner(), damage ));
                            etatDepAtt.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner(), coor.cloner(), damage ));
                            listToutDeplacement.add(etatDepAtt);
                        }
                    }
                }
            }
        }
        return listToutDeplacement;
    }

    private ArrayList<Etat> deplacerUnCamp(int id){
        ArrayList<Action> actionPrec = (ArrayList<Action>) this.listActionprec.clone();
        boolean equipeGentil;
        ArrayList<Etat> listToutDeplacement = new ArrayList<>();
        ArrayList<Personnage> listAttaqe ;
        ArrayList<Personnage> listDef ;
        Personnage persoAttaque;

        if(mechantAt(id) != -1){
            persoAttaque = listMechant.get(mechantAt(id));
            equipeGentil = false;
            listDef = gentils;
        }
        else {
            persoAttaque = gentils.get(gentilAt(id));
            equipeGentil = true;
            listDef = listMechant;
        }

        for(Coordinate pos : dataCoordCharacters.getMovementArea(persoAttaque, persoAttaque.getPos())){
            if (persoAt(pos) == -1){
                Etat etatDep = cloner();
                Personnage persoA = persoAttaque.cloner();
                persoA.setPos(pos);
                if(equipeGentil){
                    etatDep.gentils.set(gentils.indexOf(persoAttaque), persoA);
                }
                else {
                    etatDep.listMechant.set(listMechant.indexOf(persoAttaque), persoA);
                }
                //etatDep.setActionPrecedent(new Action(persoAttaque.getPos().cloner(), pos.cloner()));
                etatDep.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner()));

                listToutDeplacement.add(etatDep);

                for(Coordinate coor : dataCoordCharacters.getAttackAreaAfterMovement(persoA, persoA.getPos())){
                    int index;
                    if(equipeGentil){
                        index = mechantAt(coor);
                    }
                    else {
                        index = gentilAt(coor);
                    }
                    if (index != -1){
                        Etat etatDepAtt = etatDep.cloner();
                        etatDepAtt.setListActionprec((ArrayList<Action>) actionPrec.clone());
                        Personnage persoDef = listDef.get(index);
                        Personnage newPersoDef = persoDef.cloner();
                        persoA.attack(newPersoDef);
                        int damage = persoDef.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                        if(equipeGentil){
                            etatDepAtt.listMechant.set(index, newPersoDef);
                        }
                        else {
                            etatDepAtt.gentils.set(index, newPersoDef);
                        }
                        //etatDepAtt.setActionPrecedent(new Action(persoAttaque.getPos().cloner(), pos.cloner(), coor.cloner(), damage ));
                        etatDepAtt.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner(), coor.cloner(), damage ));

                        listToutDeplacement.add(etatDepAtt);
                    }
                }
            }
        }
        return listToutDeplacement;
    }
    private ArrayList<Etat> attaqueUnCamp(int id){
        ArrayList<Etat> listToutAttaque = new ArrayList<>();
        ArrayList<Action> actionPrec = (ArrayList<Action>) this.listActionprec.clone();
        boolean estGentil;
        ArrayList<Personnage> listAttaqe ;
        ArrayList<Personnage> listDef ;
        Personnage personnageAttaque ;
        if(mechantAt(id) != -1){
            personnageAttaque = listMechant.get(mechantAt(id));
            estGentil = false;
            listDef = gentils;
        }
        else {
            personnageAttaque = gentils.get(gentilAt(id));
            estGentil = true;
            listDef = listMechant;
        }

        for(Coordinate pos : dataCoordCharacters.getAttackArea(personnageAttaque, personnageAttaque.getPos())){
            int index;
            if(estGentil){
                index = mechantAt(pos);
            }
            else {
                index = gentilAt(pos);
            }
            if(index != -1){
                Etat etatAttaque = this.cloner();
                etatAttaque.setListActionprec((ArrayList<Action>) actionPrec.clone());
                Personnage p = listDef.get(index);
                Personnage newPersoDef = p.cloner();
                personnageAttaque.attack(newPersoDef);
                int damage = p.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                if(estGentil){
                    etatAttaque.listMechant.set(index, newPersoDef);
                }
                else {
                    etatAttaque.gentils.set(index, newPersoDef);
                }
                //etatAttaque.setActionPrecedent(new Action(personnageAttaque.getPos(), pos,damage));
                etatAttaque.listActionprec.add(new Action(personnageAttaque.getPos().cloner(), pos.cloner(),damage));
                listToutAttaque.add(etatAttaque);
            }
        }
        return listToutAttaque;
    }

    private ArrayList<Etat> attaqueUnCamp(boolean estGentil){
        ArrayList<Etat> listToutAttaque = new ArrayList<>();
        ArrayList<Personnage> listAttaqe ;
        ArrayList<Personnage> listDef ;
        if(estGentil){
            listAttaqe = gentils;
            listDef = listMechant;
        }
        else {
            listAttaqe = listMechant;
            listDef = gentils;
        }
        for (Personnage personnageAttaque : listAttaqe){
            for(Coordinate pos : dataCoordCharacters.getAttackArea(personnageAttaque, personnageAttaque.getPos())){
                int index;
                if(estGentil){
                    index = mechantAt(pos);
                }
                else {
                    index = gentilAt(pos);
                }
                if(index != -1){
                    Etat etatAttaque = this.cloner();
                    Personnage p = listDef.get(index);
                    Personnage newPersoDef = p.cloner();
                    personnageAttaque.attack(newPersoDef);
                    int damage = p.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                    if(estGentil){
                        etatAttaque.listMechant.set(index, newPersoDef);
                    }
                    else {
                        etatAttaque.gentils.set(index, newPersoDef);
                    }
                    etatAttaque.setActionPrecedent(new Action(personnageAttaque.getPos(), pos,damage));
                    listToutAttaque.add(etatAttaque);
                }
            }
        }
        return listToutAttaque;
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

        return new Etat(lMechant, lGentil, (ArrayList<Action>) listActionprec.clone());
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
    private int mechantAt(int id){
        for (int i = 0; i < listMechant.size(); i++){
            if(listMechant.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    private int gentilAt(int id){
        for (int i = 0; i < gentils.size(); i++){
            if(gentils.get(i).getId() == id){
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
        int value = 0;
        int val = 0;
        for(Personnage p : gentils){
            value += p.getCaracteristique().getHp();
        }

        for(Personnage p : listMechant){
            val += p.getCaracteristique().getHp();
        }
        if(value == 0 || val == 0){
            return true;
        }
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


    public Action getActionPrecedent() {
        return actionPrecedent;
    }

    public void setActionPrecedent(Action actionPrecedent) {
        this.actionPrecedent = actionPrecedent;
    }

    public ArrayList<Action> getListActionprec() {
        return listActionprec;
    }

    public void setListActionprec(ArrayList<Action> listActionprec) {
        this.listActionprec = listActionprec;
    }
}

