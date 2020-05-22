package backend.field;

import backend.Carac;
import backend.Personnage;

public abstract class Gravier extends Field {
    public Gravier(String nomImg) {
        super(nomImg);
    }


    @Override
    public void affect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setMov((int) (caracPerso.getMov() * 0.5));
        if(caracPerso.getMov() == 0 ){
            caracPerso.setMov(1);
        }

    }

    @Override
    public void disaffect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setMov(caracPerso.getMaxMov());
    }
}
