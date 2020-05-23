package backend.field;

import backend.Carac;
import backend.Personnage;
import backend.field.Field;
import javafx.scene.paint.Color;

public class River extends Field {

    public River(){
        super("river");
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        if (caracPerso.getDeplacement() != 2  && caracPerso.getDeplacement() != 3) {
            caracPerso.setMov((int) (caracPerso.getMov() * 0.5));
            if (caracPerso.getMov() == 0) {
                caracPerso.setMov(1);
            }
        }


    }

    public void disaffect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setMov(caracPerso.getMaxMov());
    }
}
