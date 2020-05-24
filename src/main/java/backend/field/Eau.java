package backend.field;

import backend.Carac;
import backend.Personnage;

public  abstract class Eau extends Field{
    public Eau(String nomImg) {
        super(nomImg,true);
    }
    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        if (caracPerso.getDeplacement() != 2 && caracPerso.getDeplacement() != 3) {
            caracPerso.setHp(0);
        }
    }

    public void disaffect(Personnage personnage){


    }
}
