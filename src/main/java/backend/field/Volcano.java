package backend.field;

import backend.Carac;
import backend.Personnage;
import backend.field.Field;


public abstract class Volcano extends Field {

    public Volcano(String fieldName){
        super(fieldName);
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();


        caracPerso.setHp((int) (caracPerso.getHp() - caracPerso.getMaxHp() * 0.50));
    }

    public void disaffect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        //caracPerso.setMov(1);
        personnage.getCaracteristique().setMov(1);
    }
}
