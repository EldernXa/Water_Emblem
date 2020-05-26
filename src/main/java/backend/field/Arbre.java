package backend.field;

import backend.Carac;
import backend.Personnage;

public abstract class Arbre extends Field {
    public Arbre(String nomImg) {
        super(nomImg, true);
    }

    @Override
    public void affect(Personnage personnage) {

        Carac caracPerso = personnage.getCaracteristique();


        int spd = caracPerso.getSpd();
        int skl = caracPerso.getSkl();

        caracPerso.setSpd(spd + 2);
        caracPerso.setSkl(skl + 1);

    }

    @Override
    public void disaffect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();

        int spd = caracPerso.getSpd();
        int skl = caracPerso.getSkl();

        caracPerso.setSpd(spd - 2);
        caracPerso.setSkl(skl - 1);

    }
}
