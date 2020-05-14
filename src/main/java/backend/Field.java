package backend;

import javafx.scene.paint.Color;

public abstract class Field {
    private Color color;

    public Field(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    abstract void affect(Personnage personnage);
    abstract void disaffect(Personnage personnage);
}
