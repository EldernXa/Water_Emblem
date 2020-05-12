package backend;

public class Personnage {
    private int id; // surement affecter par une valeur satic
    static private int nbPerso;
    private Carac caracteristique;

    public Personnage(String name , String type) {
        id = nbPerso;
        nbPerso++;
        caracteristique = new Carac(name, type);
    }
    public Personnage(String type) {
        id = nbPerso;
        nbPerso++;
        caracteristique = new Carac(type);
    }
    public void attack(Coordinate coodinate){
        backend.Personnage adversaire = getPersonnageAt(coodinate); // il faut une list de tout les personnage enregistré
        adversaire.getAttacked(caracteristique.getStr());
    }

    private backend.Personnage getPersonnageAt(backend.Coordinate coodinate) {
        return null;
    }

    public void getAttacked(double attackStenght){
        double hp = caracteristique.getHp();
        if(attackStenght <= caracteristique.getDef()){
            return;
        }
        hp = hp + caracteristique.getDef() - attackStenght;
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
