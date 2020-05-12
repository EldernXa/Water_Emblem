package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Carac {
    private String name;
    private String type;
    private double maxHp;
    private double hp;
    private double str;
    private double def;
    private double mag;
    private double skl;
    private double lck;
    private double res;
    private double mov;
    private String wep1;
    private String wep2;
    private String wep3;

    //nom / type /HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3
    public Carac(String name, String type){

        ArrayList<String> list =  DataPerso.getCharacter(name);
        name = list.get(0);
        this.type = list.get(1);
        if(this.type.compareTo(type) != 0){
            System.out.println("pb de bd : pour : " + name + "on donne le type :" + type + "main on lit : "+ this.type );
            System.exit(3);
        }
        // maxHp = Integer.parseInt(list.get(2)); a voir si on garde
        hp = Integer.parseInt(list.get(2));
        str = Integer.parseInt(list.get(3));
        def = Integer.parseInt(list.get(4));
        mag = Integer.parseInt(list.get(5));
        skl = Integer.parseInt(list.get(6));
        lck = Integer.parseInt(list.get(7));
        res = Integer.parseInt(list.get(8));
        mov = Integer.parseInt(list.get(9));
        if(list.size() == 10){
            return;
        }
        wep1 = list.get(10);
        if(list.size() == 11){
            return;
        }
        wep2 = list.get(11);
        if(list.size() == 12){
            return;
        }
        wep3 = list.get(12);


    }

    public Carac(String type) {
        name = "mechant";
        ArrayList<String> list =  DataPerso.getUnite(type);
        type = list.get(0);
        // maxHp = Integer.parseInt(list.get(1)); a voir si on garde
        hp = Integer.parseInt(list.get(1));
        str = Integer.parseInt(list.get(2));
        def = Integer.parseInt(list.get(3));
        mag = Integer.parseInt(list.get(4));
        skl = Integer.parseInt(list.get(5));
        lck = Integer.parseInt(list.get(6));
        res = Integer.parseInt(list.get(7));
        mov = Integer.parseInt(list.get(8));
        if(list.size() == 9){
            return;
        }
        wep1 = list.get(9);
        if(list.size() == 10){
            return;
        }
        wep2 = list.get(10);
        if(list.size() == 11){
            return;
        }
        wep3 = list.get(11);
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

    public double getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getStr() {
        return str;
    }

    public void setStr(double str) {
        this.str = str;
    }

    public double getDef() {
        return def;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public double getSkl() {
        return skl;
    }

    public void setSkl(double skl) {
        this.skl = skl;
    }

    public double getLck() {
        return lck;
    }

    public void setLck(double lck) {
        this.lck = lck;
    }

    public double getRes() {
        return res;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public double getMov() {
        return mov;
    }

    public void setMov(double mov) {
        this.mov = mov;
    }

    public String getWep1() {
        return wep1;
    }

    public void setWep1(String wep1) {
        this.wep1 = wep1;
    }

    public String getWep2() {
        return wep2;
    }

    public void setWep2(String wep2) {
        this.wep2 = wep2;
    }

    public String getWep3() {
        return wep3;
    }

    public void setWep3(String wep3) {
        this.wep3 = wep3;
    }
}
