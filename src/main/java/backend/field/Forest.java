package backend.field;


import backend.Carac;
import backend.Personnage;
import backend.field.Field;
import javafx.scene.paint.Color;

public class Forest extends Field {

    public Forest(){
        super("forest");
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        int heal = (int) (caracPerso.getMaxHp() * 0.80);
        personnage.healed(caracPerso.getMaxHp() - heal);
    }

    public void disaffect(Personnage personnage){

    }
}
