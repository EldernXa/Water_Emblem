package backend.field;

import backend.Carac;
import backend.Personnage;

public class Escalier1 extends Escalier {
    public Escalier1() {
        super("escalier");
    }


    public void affect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();

        int heal = (int) (caracPerso.getMaxHp() * 0.80);
        personnage.healed(caracPerso.getMaxHp() - heal);

    }
}
