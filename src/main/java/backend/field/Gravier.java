package backend.field;

import backend.Personnage;

public abstract class Gravier extends Field {
    public Gravier(String nomImg) {
        super(nomImg);
    }

    @Override
    public void affect(Personnage personnage) {

    }

    @Override
    public void disaffect(Personnage personnage) {

    }
}
