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
        return pos.x == x && pos.y == y;
    }

    public Coordinate cloner(){
        Coordinate coor = new Coordinate(x,y);
        coor.setField(field);
        return coor;
    }

    public int distanceEntre(Coordinate coor){
        int a,b;
        a = Math.abs(x - coor.getX());
        b = Math.abs(y - coor.getY());
        return a + b;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
