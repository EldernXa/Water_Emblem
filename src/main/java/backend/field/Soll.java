package backend.field;

import backend.Personnage;

public abstract class Soll extends Field {
    public Soll(String nomImg) {
        super(nomImg,true);
    }
    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){


    }
}
