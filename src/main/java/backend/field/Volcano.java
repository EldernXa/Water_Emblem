package backend.field;

import backend.Carac;
import backend.Personnage;
import backend.field.Field;
import javafx.scene.paint.Color;

public class Volcano extends Field {

    public Volcano(){
        super("volcano");
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();

        System.out.println("maxhp " + caracPerso.getMaxHp());
        caracPerso.setHp((int) (caracPerso.getHp() *0.80));
    }

    public void disaffect(Personnage personnage){
    }
}
