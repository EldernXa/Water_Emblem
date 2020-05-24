package backend.field;

import backend.Carac;
import backend.Personnage;

public  abstract class Eau extends Field{
    public Eau(String nomImg) {
        super(nomImg,true);
    }
    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setHp(0);
    }

    public void disaffect(Personnage personnage){


    }
}
