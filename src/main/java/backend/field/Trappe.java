package backend.field;

import backend.Carac;
import backend.Personnage;

public class Trappe extends Field {
    public Trappe() {
        super("trappe",true);
    }

    @Override
    public void affect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();
        int heal = (int) (caracPerso.getMaxHp() * 0.80);
        personnage.healed(caracPerso.getMaxHp() - heal);

    }

    @Override
    public void disaffect(Personnage personnage) {

    }
}
