package backend.field;

import backend.Carac;
import backend.Personnage;

public abstract class Porte extends Field {
    public Porte(String nomImg) {
        super(nomImg,false);
    }
    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        if (caracPerso.getDeplacement() != 2 && caracPerso.getDeplacement() != 3) {
            int heal = (int) (caracPerso.getMaxHp() * 0.80);
            personnage.healed(caracPerso.getMaxHp() - heal);
        }
    }


    public void disaffect(Personnage personnage){


    }
}
