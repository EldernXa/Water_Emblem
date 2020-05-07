package main.java.backend;

public class Personnage {
    private int id; // surement affecter par une valeur satic
    private Carac caracteristique;

    public Personnage(int id, Carac caracteristique) {
        this.id = id;
        this.caracteristique = caracteristique;
    }

    public void attack(Coordinate coodinate){
        Personnage adversaire = getPersonnageAt(coodinate); // il faut une list de tout les personnage enregistré
        adversaire.getAttacked(caracteristique.getAttacStrength());
    }

    public void getAttacked(double attackStenght){
        double hp = caracteristique.getHp();
        if(attackStenght <= caracteristique.getDefence()){
            return;
        }
        hp = hp + caracteristique.getDefence() - attackStenght;
        caracteristique.setHp(hp);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Carac getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(Carac caracteristique) {
        this.caracteristique = caracteristique;
    }
}
