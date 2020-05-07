package main.java.backend;

public class Carac {
    private String name;
    private String type;
    private double maxHp;
    private double hp;
    private double defence;
    private double attacStrength;

    public Carac(String name, String type, double hp, double defence, double attacStrength) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.defence = defence;
        this.attacStrength = attacStrength;
    }

    public Carac(String type, double hp, double defence, double attacStrength) {
        name = "ennemis";
        this.type = type;
        this.hp = hp;
        this.defence = defence;
        this.attacStrength = attacStrength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getDefence() {
        return defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getAttacStrength() {
        return attacStrength;
    }

    public void setAttacStrength(double attacStrength) {
        this.attacStrength = attacStrength;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

}
