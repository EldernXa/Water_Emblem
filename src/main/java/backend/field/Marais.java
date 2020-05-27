package backend.field;

import backend.Carac;
import backend.Personnage;

public abstract class Marais extends Field {


    public Marais(String nomImg) {
        super(nomImg, true);
    }


    public void affect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();
        int skl = caracPerso.getSkl();
        int spd = caracPerso.getSpd();

        if(caracPerso.getDeplacement() == 1){
            caracPerso.setMov(1);
        }


        if (caracPerso.getDeplacement() == 0) {



            caracPerso.setMov(2);
        }
        caracPerso.setSkl(skl - 2);
        caracPerso.setSpd(spd - 5);


    }

    public void disaffect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();
        int skl = caracPerso.getSkl();
        int spd = caracPerso.getSpd();
        caracPerso.setSkl(skl+2);
        caracPerso.setSpd(spd + 5);
        caracPerso.setMov(caracPerso.getMaxMov());
    }
}
