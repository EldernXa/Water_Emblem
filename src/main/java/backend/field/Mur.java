package backend.field;

import backend.Personnage;

public abstract class Mur extends Field {
    public Mur(String nomImg) {
        super(nomImg);
    }
    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){


    }
}
