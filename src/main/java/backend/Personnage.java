package backend;

public class Personnage {
    private int id; // surement affecter par une valeur satic
    private backend.Carac caracteristique;

    public Personnage(int id, backend.Carac caracteristique) {
        this.id = id;
        this.caracteristique = caracteristique;
    }

    public void attack(backend.Coordinate coodinate){
        backend.Personnage adversaire = getPersonnageAt(coodinate); // il faut une list de tout les personnage enregistré
        adversaire.getAttacked(caracteristique.getAttacStrength());
    }

    private backend.Personnage getPersonnageAt(backend.Coordinate coodinate) {
        return null;
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

    public backend.Carac getCaracteristique() {
        return caracteristique;
    }

    public void setCaracteristique(backend.Carac caracteristique) {
        this.caracteristique = caracteristique;
    }
}
