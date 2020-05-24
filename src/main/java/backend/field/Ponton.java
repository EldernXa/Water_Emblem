package backend.field;

import backend.Personnage;

public abstract class Ponton extends Field {
    public Ponton(String nomImg) {
        super(nomImg,true);
    }
    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){


    }
}
