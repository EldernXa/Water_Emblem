package backend.field;

import backend.Personnage;

public abstract class Coque extends Field {
    public Coque(String nomImg) {
        super(nomImg, true);
    }

    @Override
    public void affect(Personnage personnage) {

    }

    @Override
    public void disaffect(Personnage personnage) {

    }
}
