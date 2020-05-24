package backend.field;

import backend.Carac;
import backend.Personnage;

public class Escalier3 extends Field {
    public Escalier3() {
        super("hautEscalier");
    }

    @Override
    public void affect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();
        if (caracPerso.getDeplacement() != 2 && caracPerso.getDeplacement() != 3) {
            int heal = (int) (caracPerso.getMaxHp() * 0.80);
            personnage.healed(caracPerso.getMaxHp() - heal);
        }
    }

    @Override
    public void disaffect(Personnage personnage) {

    }
}
