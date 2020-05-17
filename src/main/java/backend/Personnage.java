package backend;

import frontend.AffichePerso;

import java.util.ArrayList;

public class Personnage {
    private int id;
    static private int nbPerso;
    private Carac caracteristique;
    private Coordinate pos;


    public Personnage(String name) {
        id = nbPerso;
        nbPerso++;
        caracteristique = new Carac(name);
        this.pos = pos;
    }

    private Personnage(Personnage perso){
        id = perso.id;
        pos = perso.pos;
        caracteristique = perso.caracteristique.cloner();//pas sur, peut etre pb d adresse
    }
    public void attack(Coordinate coodinate){
        Personnage adversaire = AffichePerso.getPersonnageAt(coodinate); // il faut une list de tout les personnage enregistré

    }



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

    public void calculerStats(){
        String arme1 = caracteristique.getWep1();
        int str = caracteristique.getStr();
        switch (arme1){
            case "Epee":{
                caracteristique.setStr(str + 4);
            }
            case "Lance": {
                caracteristique.setStr(str + 3);
                caracteristique.setPorte(2);
            }
            case "Hache": {
                caracteristique.setStr(str + 2);
            }
            case "Arc" : {
                caracteristique.setStr(str + 2);
                caracteristique.setPorte(5);
            }
            case "Magie" : {
                caracteristique.setPorte(3);
            }
        }
    }

}
