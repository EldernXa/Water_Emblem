package backend;



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
