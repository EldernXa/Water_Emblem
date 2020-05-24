package backend.field;

import backend.Carac;
import backend.Personnage;

public abstract class Bord extends Field {
    public Bord(String nomImg) {
        super(nomImg);
    }
    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){


    }
}
