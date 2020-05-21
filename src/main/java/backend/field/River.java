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
        System.out.println(caracPerso.getMov());
        caracPerso.setMov((int) (caracPerso.getMov() * 0.80));

    }

    public void disaffect(Personnage personnage){

    }
}
