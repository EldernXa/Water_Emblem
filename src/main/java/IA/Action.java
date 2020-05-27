package IA;

import backend.Coordinate;

public  class Action {
    private Coordinate posDepart ;
    private Coordinate posArrive;
    private Coordinate posAttaquant;
    private Coordinate posDefenceur;
    private int damage;
    private int contreAttaque;


    public Action(Coordinate posDepart, Coordinate posArrive, Coordinate posDefenceur, int damage, int contreAttaque) {
        this.posDepart = posDepart;
        this.posArrive = posArrive;
        this.posAttaquant = posArrive;
        this.posDefenceur = posDefenceur;
        this.damage = damage;
        this.contreAttaque = contreAttaque;
    }

    public Action(Coordinate posDepart, Coordinate posArrive) {
        this();
        this.posDepart = posDepart;
        this.posArrive = posArrive;
    }

    public Action(Coordinate posAttaquant, Coordinate posDefenceur, int damage, int contreAttaque) {
        this();
        this.posAttaquant = posAttaquant;
        this.posDefenceur = posDefenceur;
        this.damage = damage;
        this.contreAttaque = contreAttaque;
    }

    public Action(){
        this.posDepart = new Coordinate(-1, -1);
        this.posArrive = new Coordinate(-1, -1);
        this.posAttaquant = new Coordinate(-1, -1);
        this.posDefenceur = new Coordinate(-1, -1);
        this.damage = -1;
        contreAttaque = -1;
    }

    public Action cloner(){
        return new Action(posDepart.cloner() , posArrive.cloner(), posDefenceur.cloner(), damage, contreAttaque);
    }

    public int getDistanceAttaque(){
        if (damage == -1){
            return -1;
        }
        int x,y;
        x = Math.abs(posAttaquant.getX() - posDefenceur.getX());
        y = Math.abs(posAttaquant.getY() - posDefenceur.getY());
        return x + y;
    }

    public int getTotalDamage(){
        return damage - contreAttaque;
    }
    public Coordinate getPosDepart() {
        return posDepart;
    }

    public void setPosDepart(Coordinate posDepart) {
        this.posDepart = posDepart;
    }

    public Coordinate getPosArrive() {
        return posArrive;
    }

    public void setPosArrive(Coordinate posArrive) {
        this.posArrive = posArrive;
    }

    public Coordinate getPosAttaquant() {
        return posAttaquant;
    }

    public void setPosAttaquant(Coordinate posAttaquant) {
        this.posAttaquant = posAttaquant;
    }

    public Coordinate getPosDefenceur() {
        return posDefenceur;
    }

    public void setPosDefenceur(Coordinate posDefenceur) {
        this.posDefenceur = posDefenceur;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getContreAttaque() {
        return contreAttaque;
    }

    public void setContreAttaque(int contreAttaque) {
        this.contreAttaque = contreAttaque;
    }
}
