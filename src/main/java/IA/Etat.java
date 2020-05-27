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
        listActionprec = new ArrayList<>();
    }
    public ArrayList<Etat> getToutPossibilite(boolean gentilJoue){
        ArrayList<Etat> list = new ArrayList<>();
        list.add(this.cloner());
        if (gentilJoue){
            Etat e,d;
            for(Personnage p : gentils){
                ArrayList<Etat> l = new ArrayList<>();
                for (Etat t : (ArrayList<Etat> )list.clone()){
                    d = t.deplacerUnCamp(p.getId());
                    if(d != null){
                        l.add(d);
                    }
                    e = t.attaqueUnCamp(p.getId());
                    if(e!=null){
                        l.add(e);
                    }
                }
                list = l;
            }
        }
        else {
            Etat d,e;
            for(Personnage p : listMechant){
                ArrayList<Etat> l = new ArrayList<>();
                for (Etat t : (ArrayList<Etat>) list.clone()){
                    d = t.deplacerUnCamp(p.getId());
                    if(d != null){
                        l.add(d);
                    }
                    e = t.attaqueUnCamp(p.getId());
                    if(e!=null){
                        l.add(e);
                    }
                }
                list = l;
            }
        }
        return list;
    }

    private Etat deplacerUnCamp(int id){
       // ArrayList<Action> actionPrec = (ArrayList<Action>) this.listActionprec.clone();
        boolean equipeGentil;
        ArrayList<Etat> listToutDeplacement = new ArrayList<>();
        ArrayList<Etat> listDeplacementAttaque = new ArrayList<>();
        ArrayList<Personnage> listDef ;
        Personnage persoAttaque;

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
                        if(newPersoDef.getCaracteristique().getPortee() > distanceEntre(newPersoDef.getPos(), persoA.getPos()) ){
                            newPersoDef.attack(persoA);
                        }

                        int damage = persoDef.getCaracteristique().getHp() - newPersoDef.getCaracteristique().getHp();
                        if(equipeGentil){
                            etatDepAtt.listMechant.set(index, newPersoDef);
                        }
                        else {
                            etatDepAtt.gentils.set(index, newPersoDef);
                        }
                        etatDepAtt.listActionprec.add(new Action(persoAttaque.getPos().cloner(), pos.cloner(), coor.cloner(), damage ));
                        listDeplacementAttaque.add(etatDepAtt);
                        //return etatDepAtt;
                    }
                }
            }
        }
        if(listDeplacementAttaque.isEmpty()){
            return heuristique.meilleurEtat(listToutDeplacement);
        }
        else {
            int damageMax = 0;
            Etat etatMax = listDeplacementAttaque.get(0);
            Action actionMax = etatMax.getListActionprec().get(etatMax.getListActionprec().size()-1);
            for(Etat etat : listDeplacementAttaque){
                Action action = etat.getListActionprec().get(etat.getListActionprec().size()-1);
                if(damageMax < action.getDamage()){
                    damageMax = action.getDamage();
                    etatMax = etat;
                }
                else if(damageMax == action.getDamage()){
                    if(action.getDistanceAttaque() > actionMax.getDistanceAttaque()){
                        damageMax = action.getDamage();
                        etatMax = etat;
                    }
                }
            }
            return etatMax;
        }
    }
    private Etat attaqueUnCamp(int id){

       // ArrayList<Action> actionPrec = (ArrayList<Action>) this.listActionprec.clone();
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
                //etatAttaque.setListActionprec((ArrayList<Action>) actionPrec.clone());
                Personnage p = listDef.get(index);
                Personnage newPersoDef = p.cloner();
                personnageAttaque.attack(newPersoDef);
                if(newPersoDef.getCaracteristique().getPortee() > distanceEntre(newPersoDef.getPos(), personnageAttaque.getPos()) ){
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
                etatAttaque.listActionprec.add(new Action(personnageAttaque.getPos().cloner(), pos.cloner(),damage));
                if(damageMax < damage + damageMax){
                    damageMax = damage + damageMax;
                    etatMax = etatAttaque;
                }
            }
        }
        return etatMax;
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




    public int valDistanceGlobal(){
        int x = 0;
        int y = 0;
        int sum = 0;
        for (Personnage p : gentils){
            x += p.getPos().getX() + p.getPos().getY();
        }
        for (Personnage per : listMechant){
            y += per.getPos().getX() + per.getPos().getY();
        }

        for (Personnage gentil : gentils){
            for (Personnage mechant : listMechant){
                x = Math.abs(gentil.getPos().getX() - mechant.getPos().getX());
                y = Math.abs(gentil.getPos().getY() - mechant.getPos().getY());
                sum += x +y;
            }

        }
        return sum;
    }

    public int valDistance(){
        int minDistance = 3000;
        for (Personnage p : gentils){
            for (Personnage per : listMechant){
                int x = distanceEntre(p.getPos(), per.getPos());
                if(x < minDistance){
                    minDistance = x;
                }
            }
        }
        return minDistance ;
    }

    private int distanceEntre(Coordinate perso, Coordinate p){
        int x,y;
        x = Math.abs(perso.getX() - p.getX());
        y = Math.abs(perso.getY() - p.getY());
        return x + y;
    }

    private boolean ameliorePos(Personnage personnage, Coordinate coor, boolean gentil){

        ArrayList<Personnage> ennemie;
        int x,y,i,j;
        int minPos = 1000;
        int minPerso = 1000;
        if(gentil){
            ennemie = listMechant;
        }
        else {
            ennemie = gentils;
        }

        for (Personnage p : ennemie){
            x = Math.abs(p.getPos().getX() - personnage.getPos().getX());
            y = Math.abs(p.getPos().getY() - personnage.getPos().getY());
            i = Math.abs(p.getPos().getX() - coor.getX());
            j = Math.abs(p.getPos().getY() - coor.getY());
            if ( minPerso > x + y){
                minPerso = x + y;
            }
            minPos += (x + y) - (i + j);
            if(minPos > i + j){
                minPos = i + j;
            }
        }
        return minPos < minPerso;
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

