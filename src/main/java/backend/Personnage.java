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

        Personnage adversaire = AffichePerso.getPersonnageAt(coodinate); // il faut une list de tout les personnage enregistré
        if(adversaire!=null) {
            int damage = Stat.damage(caracteristique, adversaire.getCaracteristique());
            if(caracteristique.getHp() >0) {
                adversaire.attacked(damage);
            }
            damage = Stat.damage(adversaire.getCaracteristique(), caracteristique);
            if(adversaire.getCaracteristique().getHp() > 0) {
                this.attacked(damage);
            }
        }
    }

    public void attackHorsPortee(Coordinate coordinate){
        Personnage adversaire = AffichePerso.getPersonnageAt(coordinate);
        if(adversaire != null){
            int damage = Stat.damage(caracteristique, adversaire.getCaracteristique());
            if(caracteristique.getHp()>0)
                adversaire.attacked(damage);
        }
    }

    public void attack(Personnage adversaire){
        int damage = Stat.damage(caracteristique, adversaire.caracteristique);
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


    public boolean isMage(){
        return caracteristique.getWep1().compareTo("Magie") == 0;
    }


    public Personnage cloner(){
        return new Personnage(this);
    }

    public ArrayList<Coordinate> getMovmentPossible(){
        ArrayList<Coordinate> lCoor = new ArrayList<>();
        for(int i = pos.getX() - 2 ; i <= pos.getX() + 2; i++){
            for(int j = pos.getY() - 2 ; j <= pos.getY() + 2; j++){
                if(i != pos.getX() || j != pos.getY()){
                    lCoor.add(new Coordinate(i,j));
                }
            }
        }
        return lCoor;
    }

    public ArrayList<Coordinate> getAttaquePossible(){
        return new ArrayList<>();
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
