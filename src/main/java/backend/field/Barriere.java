package backend.field;

import backend.Personnage;

public abstract class Barriere extends Field {
    public Barriere(String nomImg) {
        super(nomImg);
    }
    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){


    }
}
