package backend.field;


import backend.Carac;
import backend.Personnage;
import backend.field.Field;
import javafx.scene.paint.Color;

public abstract class Corniche extends Field {

    public Corniche(String nameField){
        super(nameField);
    }

    public void affect(Personnage personnage){

    }

    public void disaffect(Personnage personnage){

    }
}
