package backend;

import java.util.ArrayList;

public class Personnage {
    private int id; // surement affecter par une valeur satic
    static private int nbPerso;
    private Carac caracteristique;
    private Coordinate pos;


    public Personnage(String name , String type) {
        id = nbPerso;
        nbPerso++;
        caracteristique = new Carac(name, type);
        this.pos = pos;
    }
    public Personnage(String type) {
        id = nbPerso;
        nbPerso++;
        caracteristique = new Carac(type);
        this.pos = pos;
    }
    private Personnage(Personnage perso){
        id = perso.id;
        pos = perso.pos;
        caracteristique = perso.caracteristique.cloner();//pas sur, peut etre pb d adresse
    }
    public void attack(Coordinate coodinate){
        backend.Personnage adversaire = getPersonnageAt(coodinate); // il faut une list de tout les personnage enregistré
        //adversaire.getAttacked(caracteristique.getStr());
    }

    private backend.Personnage getPersonnageAt(backend.Coordinate coodinate) {
        return null;
    }
/*
    public void getAttacked(int attackStenght){
        int hp = caracteristique.getHp();
        if(attackStenght <= caracteristique.getDef()){
            return;
        }
        hp = hp + caracteristique.getDef() - attackStenght;
        caracteristique.setHp(hp);
    }*/

    public Personnage cloner(){
        return new Personnage(this);

    }
    public ArrayList<Coordinate> getMovmentPossible(){
        return new ArrayList<>();
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
