package backend;

import javafx.scene.paint.Color;

public class Plaine extends Field {

    public Plaine(){
        super(Color.WHITE);
    }

    void affect(Personnage personnage) {
        Carac caracPerso = personnage.getCaracteristique();

    }


    void disaffect(Personnage personnage) {

    }
}
