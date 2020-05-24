package backend.field;

import backend.Personnage;

public abstract class Escalier extends Field{
    public Escalier(String nomImg) {
        super(nomImg,true);
    }

    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){


    }
}
