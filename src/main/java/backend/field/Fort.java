package backend.field;

import backend.Carac;
import backend.Personnage;

public class Fort extends Field {
    public Fort() {
        super("fort", true);
    }


    public void affect(Personnage personnage) {

        Carac caracPerso = personnage.getCaracteristique();

        int def = caracPerso.getDef();
        int res = caracPerso.getRes();
        int spd = caracPerso.getSpd();
        caracPerso.setDef( def + 5);
        caracPerso.setRes(res +5);
        caracPerso.setSpd(spd + 5);

        int heal = (int) (caracPerso.getMaxHp() * 0.60);
        personnage.healed(caracPerso.getMaxHp() - heal);


    }


    public void disaffect(Personnage personnage) {

        Carac caracPerso = personnage.getCaracteristique();
        int def = caracPerso.getDef();
        int res = caracPerso.getRes();
        int spd = caracPerso.getSpd();
        caracPerso.setDef( def - 5);
        caracPerso.setRes(res - 5);
        caracPerso.setSpd(spd - 5);

    }
}
