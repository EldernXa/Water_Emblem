package backend.field;

import backend.Carac;
import backend.Personnage;
import backend.field.Field;
import javafx.scene.paint.Color;

public class River extends Field {
    int move;
    public River(){
        super("river");
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        move = caracPerso.getMov();
        System.out.println(" move : " + move + " " + caracPerso.getName());
        caracPerso.setMov((int) (caracPerso.getMov() * 0.5));

    }

    public void disaffect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setMov(caracPerso.getMaxMov());
    }
}
