package backend;

import javafx.scene.paint.Color;

public class Volcano extends Field {

    public Volcano(){
        super(Color.RED);
    }

    void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        int burn = (int) (caracPerso.getMaxHp() * 0.95);
        caracPerso.setHp(caracPerso.getHp() - burn);
    }

    void disaffect(Personnage personnage){

    }
}
