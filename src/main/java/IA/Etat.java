package IA;

import backend.Coordinate;
import backend.PersonnageDisplay;
import backend.data.DataCoordCharacters;
import backend.field.*;
import backend.Personnage;
import backend.field.Void;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import frontend.AffichePerso;

import java.util.ArrayList;
import java.util.List;

public class Etat {
    private ArrayList<Personnage> listMechant;
    private ArrayList<Personnage> gentils;
    private int valHeuristique;
    private static Heuristique heuristique;
    private ArrayList<Action> listActionprec;
    private static AffichePerso affichePerso;

    public Etat(ArrayList<Personnage> listMechant, ArrayList<Personnage> gentils, ArrayList<Action> listActionPrecedent) {
        this.listActionprec = listActionPrecedent;
        this.listMechant = listMechant;
        this.gentils = gentils;
        valHeuristique = heuristique.calculerHeuristique(this);
    }

    public Etat(AffichePerso affichePersos, Heuristique h ) {
        gentils = new ArrayList<>();
        listMechant = new ArrayList<>();
        for (PersonnageDisplay personnageDisplay : AffichePerso.listPersonnage){
            if(personnageDisplay.isAlive()){
                Personnage p = personnageDisplay.getPersonnage();
                Personnage perso = p.cloner();
                perso.setPos(personnageDisplay.getCoordinate());
                gentils.add(perso);
            }
        }

        for (PersonnageDisplay personnageDisplay : AffichePerso.listEnnemi){
            if(personnageDisplay.isAlive()){
                Personnage p = personnageDisplay.getPersonnage();
                Personnage perso = p.cloner();
                perso.setPos(personnageDisplay.getCoordinate());
                listMechant.add(perso);
            }
        }
        heuristique = h;
        valHeuristique = heuristique.calculerHeuristique(this);
        listActionprec = new ArrayList<>();
        Etat.affichePerso = affichePersos;
    }


    public ArrayList<Etat> getToutPossibilite(boolean gentilJoue){
        ArrayList<Etat> list = new ArrayList<>();
        ArrayList<Etat> nouvelList;
        Etat nouvelEtatDeplacement;
        list.add(this.cloner());

        if (gentilJoue){
            for(Personnage p : gentils){
                nouvelList = new ArrayList<>();
                for (Etat etat : (ArrayList<Etat> )list.clone()){
                    nouvelEtatDeplacement = etat.deplacerUnCamp(p.getId());
                    if(nouvelEtatDeplacement != null){
                        nouvelList.add(nouvelEtatDeplacement);
                    }

                }
                list = nouvelList;
            }
        }
        else {
            for(Personnage p : listMechant){
                nouvelList = new ArrayList<>();
                for (Etat etat : (ArrayList<Etat>) list.clone()){
                    nouvelEtatDeplacement = etat.deplacerUnCamp(p.getId());
                    if(nouvelEtatDeplacement != null){
                        nouvelList.add(nouvelEtatDeplacement);
                    }
                }
                list = nouvelList;
            }
        }
        return list;
    }
    private Etat deplacerUnCamp(int id){

        boolean equipeGentil;
        ArrayList<Etat> listToutDeplacement = new ArrayList<>();
        ArrayList<Etat> listDeplacementAttaque = new ArrayList<>();
        ArrayList<Personnage> listDef ;
        Personnage persoAttaque;
        ArrayList<Coordinate> listDeplacementPossible;
        if(mechantAt(id) != -1){
            persoAttaque = listMechant.get(mechantAt(id));
            equipeGentil = false;
            listDef = gentils;
        }
        else if (gentilAt(id) != -1){
            persoAttaque = gentils.get(gentilAt(id));
            equipeGentil = true;
            listDef = listMechant;
        }
        else {
            return null;
        }
        listDeplacementPossible = affichePerso.getCoordinate(persoAttaque, persoAttaque.getPos());
        listDeplacementPossible.add(persoAttaque.getPos());

        for(Coordinate pos : listDeplacementPossible){
            if (persoAt(pos) == -1 && !pos.getField().compareField(new Void())  && !aEviterAToutPris(pos.getField())){
                System.out.println(pos.getField().getClass().toString());
                Etat etatDep = cloner();
                Personnage persoA = persoAttaque.cloner();
                persoA.setPos(pos);
                if(equipeGentil){
                    etatDep.gentils.set(gentils.indexOf(persoAttaque), persoA);
                }
                else {
                    etatDep.listMechant.set(listMechant.indexOf(persoAttaque), persoA);
                }
                etatDep.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner()));
                listToutDeplacement.add(etatDep);

                for(Coordinate coor : affichePerso.getAttackAreaAfterMovement(persoA, persoA.getPos())){
                    int index;
                    if(equipeGentil){
                        index = etatDep.mechantAt(coor);
                    }
                    else {
                        index = etatDep.gentilAt(coor);
                    }
                    if (index != -1){
                        Etat etatDepAtt = etatDep.cloner();
                        etatDepAtt.setListActionprec((ArrayList<Action>) listActionprec.clone());
                        Personnage persoDef = listDef.get(index).cloner();
                        Personnage newPersoDef = persoDef.cloner();
                        persoA.attack(newPersoDef);
                        int contreAttaque = 0;
                        if(newPersoDef.getCaracteristique().getPortee() > newPersoDef.getPos().distanceEntre(persoA.getPos()) && newPersoDef.isAlive() ){
                            contreAttaque = persoA.getCaracteristique().getHp();
                            newPersoDef.attack(persoA);
                            contreAttaque -= persoA.getCaracteristique().getHp();
                        }

                        int damage = persoDef.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                        if(equipeGentil){
                            etatDepAtt.listMechant.set(index, newPersoDef);
                        }
                        else {
                            etatDepAtt.gentils.set(index, newPersoDef);
                        }
                        etatDepAtt.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner(), coor.cloner(), damage, contreAttaque ));
                        listDeplacementAttaque.add(etatDepAtt);
                        }
                }
            }
        }

        if(listDeplacementAttaque.isEmpty()){

            if(listToutDeplacement.isEmpty()){
                return null;
            }
            return meilleurEtat(listToutDeplacement);
        }
        else {
            int damageMax = 0;
            int distanceMax = 0;
            Etat etatMax = listDeplacementAttaque.get(0);
            for(Etat etat : listDeplacementAttaque){
                Action action = etat.getListActionprec().get(etat.getListActionprec().size()-1);

                if(action.getDistanceAttaque() > distanceMax){
                    damageMax = action.getTotalDamage();
                    distanceMax = action.getDistanceAttaque();
                    etatMax = etat;
                }
                else if(action.getDistanceAttaque() == distanceMax){
                    if(action.getTotalDamage() > damageMax){
                        damageMax = action.getTotalDamage();
                        distanceMax = action.getDistanceAttaque();
                        etatMax = etat;
                    }
                }

            }
            return etatMax;
        }
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

        ArrayList<Action> newListAction = new ArrayList<>();
        for (Action action : listActionprec){
            newListAction.add(action.cloner());
        }

        return new Etat(lMechant, lGentil, newListAction);

    }

    private int persoAt(Coordinate pos){
        if(mechantAt(pos) != -1){
            return mechantAt(pos);
        }
        else return gentilAt(pos);
    }

    private int mechantAt(Coordinate pos){
        for (int i = 0; i < listMechant.size(); i++){
            if(listMechant.get(i).getPos().equal(pos) && listMechant.get(i).isAlive()){
                return i;
            }
        }
        return -1;
    }

    private int gentilAt(Coordinate pos){
        for (int i = 0; i < gentils.size(); i++){
            if(gentils.get(i).getPos().equal(pos) && gentils.get(i).isAlive()){
                return i;
            }
        }
        return -1;
    }

    private int mechantAt(int id){
        for (int i = 0; i < listMechant.size(); i++){
            if(listMechant.get(i).getId() == id  && listMechant.get(i).isAlive()){
                return i;
            }
        }
        return -1;
    }
    private int gentilAt(int id){
        for (int i = 0; i < gentils.size(); i++){
            if(gentils.get(i).getId() == id && gentils.get(i).isAlive()){
                return i;
            }
        }
        return -1;
    }

    public boolean aEviterAToutPris(Field field){
        if(field.compareField(new Void())){
            return true;
        }
        return false;
    }

    public Etat meilleurEtat(ArrayList<Etat> list){
        Etat meilleur = list.get(0);
        for (Etat e : list){
            if(meilleur.valDistance() > e.valDistance()){
                meilleur = e;
            }
        }
        return meilleur;
    }

    public int valDistance(){
        int distanceToto = 0;
        for (Personnage p : listMechant){
            int minDistance = 31000;
            for (Personnage per : gentils){
                int x = p.getPos().distanceEntre(per.getPos());
                if(x < minDistance){
                    minDistance = x;
                }
            }
            distanceToto += minDistance;
        }
        return distanceToto ;
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

    public ArrayList<Action> getListActionprec() {
        return listActionprec;
    }

    public void setListActionprec(ArrayList<Action> listActionprec) {
        this.listActionprec = listActionprec;
    }



}

