package backend.field;


import backend.Carac;
import backend.Personnage;
import backend.field.Field;


public abstract class Corniche extends Field {

    public Corniche(String nameField){
        super(nameField, true);
    }

    public void affect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setLck((int) (caracPerso.getLck() * 1.20));
        caracPerso.setSpd((int) (caracPerso.getSpd() * 1.20));
        if(caracPerso.getLck() == 0){
            caracPerso.setLck(1);
        }
        if(caracPerso.getSpd() == 0){
            caracPerso.setSpd(1);
        }

    }

    public void disaffect(Personnage personnage){
        Carac caracPerso = personnage.getCaracteristique();
        caracPerso.setSpd(caracPerso.getMaxSpd());
        caracPerso.setLck(caracPerso.getMaxLck());

    }
}
