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
        caracPerso.setMov((int) (caracPerso.getMov() * 0.5));
        if(caracPerso.getMov() == 0 ){
            caracPerso.setMov(1);
        }
        System.out.println(" move : " + move + " " + caracPerso.getName());

    }

    public void disaffect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setMov(caracPerso.getMaxMov());
    }
}
