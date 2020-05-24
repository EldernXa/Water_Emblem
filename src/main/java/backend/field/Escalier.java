package backend.field;

import backend.Carac;
import backend.Personnage;

public abstract class Escalier extends Field{
    public Escalier(String nomImg) {
        super(nomImg,true);
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();

            int heal = (int) (caracPerso.getMaxHp() * 0.80);
            personnage.healed(caracPerso.getMaxHp() - heal);

    }

    public void disaffect(Personnage personnage){


    }
}
