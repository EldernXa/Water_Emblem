package backend;

import frontend.AffichePerso;

import java.util.ArrayList;

public class Personnage {
    private int id;
    static private int nbPerso;
    private Carac caracteristique;
    private Coordinate pos;

    public Personnage(String name, Coordinate coordinate){
        this(name);
        pos = coordinate;
    }
    public Personnage(String name) {
        id = nbPerso;
        nbPerso++;
        caracteristique = new Carac(name);
        pos = new Coordinate(0,0);
        Stat.calculerStats(this);

    }
    private Personnage(Personnage perso){
        id = perso.id;
        pos = perso.pos;
        caracteristique = perso.caracteristique.cloner();
    }
    public void attack(Coordinate coodinate){
        Personnage adversaire = AffichePerso.getPersonnageAt(coodinate);
        if(adversaire!=null) {
            attack(adversaire);
            adversaire.attack(this);
        }
    }

    public void attackHorsPortee(Coordinate coordinate){
        Personnage adversaire = AffichePerso.getPersonnageAt(coordinate);
        if(adversaire != null){
            attack(adversaire);
        }
    }

    public void attack(Personnage adversaire){
        int damage = Stat.damageAfterCalc(caracteristique, adversaire.caracteristique);
        if(caracteristique.getHp() > 0) {
            adversaire.attacked(damage);
        }
    }


    public void attacked(int damage) {
        int hp;
        if (damage > 0) {
             hp= caracteristique.getHp() - damage;
        }
        else {
             hp = caracteristique.getHp() + damage;
        }
        if (hp <= 0) {
            hp = 0;
        }
        caracteristique.setHp(hp);
    }

    public void healed(int hpHeal){
        int hp;

        if((caracteristique.getHp() + hpHeal) > caracteristique.getMaxHp()){
            hp = caracteristique.getMaxHp();
        }
        else {
         hp = caracteristique.getHp() + hpHeal;
        }
        caracteristique.setHp(hp);

    }

    public Personnage cloner(){
        return new Personnage(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public backend.Carac getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(backend.Carac caracteristique) {
        this.caracteristique = caracteristique;
    }

    public Coordinate getPos() {
        return pos;
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }



}
