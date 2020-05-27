package IA;

import backend.Coordinate;
import backend.PersonnageDisplay;
import backend.data.DataCoordCharacters;
import backend.field.Field;
import backend.Personnage;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import frontend.AffichePerso;

import java.util.ArrayList;
import java.util.List;

public class Etat {
    private ArrayList<Personnage> listMechant;
    private ArrayList<Personnage> gentils;
    private int valHeuristique;
    private static Heuristique heuristique;
    private static DataCoordCharacters dataCoordCharacters;
    private ArrayList<Action> listActionprec;
    private static AffichePerso affichePerso;
  //  private List<PersonnageDisplay> listMechantDisplay;
   // private List<PersonnageDisplay> listGentilDisplay;

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
        listActionprec = new ArrayList<>();
    }

    public Etat(AffichePerso affichePersos, Heuristique h ) {
        gentils = new ArrayList<>();
        listMechant = new ArrayList<>();
        for (PersonnageDisplay personnageDisplay : AffichePerso.listPersonnage){
            Personnage p = personnageDisplay.getPersonnage();
            Personnage perso = p.cloner();
            perso.setPos(personnageDisplay.getCoordinate());
                gentils.add(perso);
        }

        for (PersonnageDisplay personnageDisplay : AffichePerso.listEnnemi){
            Personnage p = personnageDisplay.getPersonnage();
            Personnage perso = p.cloner();
            perso.setPos(personnageDisplay.getCoordinate());
            listMechant.add(perso);
        }
        heuristique = h;
        valHeuristique = heuristique.calculerHeuristique(this);
        listActionprec = new ArrayList<>();
        this.affichePerso = affichePersos;
    }


    public ArrayList<Etat> getToutPossibilite(boolean gentilJoue){
        ArrayList<Etat> list = new ArrayList<>();
        ArrayList<Etat> nouvelList;
        Etat nouvelEtatAttaque,nouvelEtatDeplacement, etatTemp;
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
                        if(newPersoDef.getCaracteristique().getPortee() > newPersoDef.getPos().distanceEntre(persoA.getPos()) ){
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
                        //return etatDepAtt;
                    }
                }
            }
        }

        if(listDeplacementAttaque.isEmpty()){

            if(listToutDeplacement.isEmpty()){
                return null;
            }
            for (Etat e : listToutDeplacement){
                if( e.listActionprec.get(e.listActionprec.size()-1).getDamage() != -1){
                    e.listActionprec.get(e.listActionprec.size()-1).affAction();
                }
            }
            return meilleurEtat(listToutDeplacement);
        }
        else {
            int damageMax = 0;
            int distanceMax = 0;
            Etat etatMax = listDeplacementAttaque.get(0);
            Action actionMax = etatMax.getListActionprec().get(etatMax.getListActionprec().size()-1);
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
/*
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
        listDeplacementPossible = dataCoordCharacters.getMovementArea(persoAttaque, persoAttaque.getPos());
        listDeplacementPossible.add(persoAttaque.getPos());
        for(Coordinate pos : listDeplacementPossible){
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
                etatDep.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner()));
                listToutDeplacement.add(etatDep);

                for(Coordinate coor : dataCoordCharacters.getAttackAreaAfterMovement(persoA, persoA.getPos())){
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
                        if(newPersoDef.getCaracteristique().getPortee() > newPersoDef.getPos().distanceEntre(persoA.getPos()) ){
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
                        //return etatDepAtt;
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
            Action actionMax = etatMax.getListActionprec().get(etatMax.getListActionprec().size()-1);
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
    }*/
/*
    private Etat attaqueUnCamp(int id){

        boolean estGentil;
        ArrayList<Personnage> listDef ;
        Personnage personnageAttaque ;
        if(mechantAt(id) != -1){
            personnageAttaque = listMechant.get(mechantAt(id));
            estGentil = false;
            listDef = gentils;
        }
        else if(gentilAt(id) != -1) {
            personnageAttaque = gentils.get(gentilAt(id));
            estGentil = true;
            listDef = listMechant;
        }
        else{
            return null;
        }

        int damageMax = 0;
        Etat etatMax = null;
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
                int contreDamage = 0;
                Personnage p = listDef.get(index);
                Personnage newPersoDef = p.cloner();
                personnageAttaque.attack(newPersoDef);

                if(newPersoDef.getCaracteristique().getPortee() > newPersoDef.getPos().distanceEntre(personnageAttaque.getPos()) ){
                    contreDamage = personnageAttaque.getCaracteristique().getHp();
                    newPersoDef.attack(personnageAttaque);
                    contreDamage -= personnageAttaque.getCaracteristique().getHp();
                }
                int damage = p.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                if(estGentil){
                    etatAttaque.listMechant.set(index, newPersoDef);
                }
                else {
                    etatAttaque.gentils.set(index, newPersoDef);
                }
                etatAttaque.listActionprec.add(new Action(personnageAttaque.getPos().cloner(), pos.cloner(),damage, contreDamage));
                if(damageMax < damage - contreDamage){
                    damageMax = damage - contreDamage;
                    etatMax = etatAttaque;
                }
            }
        }
        return etatMax;
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

        ArrayList<Action> newListAction = new ArrayList<>();
        for (Action action : listActionprec){
            newListAction.add(action.cloner());
        }

        return new Etat(lMechant, lGentil, newListAction);

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

    public Etat meilleurEtat(ArrayList<Etat> list){
        Etat meilleur = list.get(0);
        for (Etat e : list){
            if(meilleur.valDistance() > e.valDistance()){
                System.out.println("ancien : " + meilleur.valDistance() );
                meilleur.affEtat();
                System.out.println("nouv : " + e.valDistance());
                e.affEtat();
                System.out.println();
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

