package backend;


import backend.field.Field;

public class Coordinate {
    private int x, y;
    private Field field;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equal(Coordinate pos){
        if(pos.x != x || pos.y != y){
            return false;
        }
        return true;
    }

    public Coordinate cloner(){
        return new Coordinate(x,y);
    }

    public void affPos(){
        System.out.println(" x : "+ x + ", y : " + y);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
