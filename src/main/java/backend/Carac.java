package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Carac {
    private String name;
    private String type;
    private int maxHp;
    private int hp;
    private int str;
    private int def;
    private int spd;
    private int mag;
    private int skl;
    private int lck;
    private int res;
    private int mov;
    private String wep1;
    private String wep2;
    private String wep3;
    // Prenom / nomUnite / HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3
    //nom / type /HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3
    public Carac(String name, String type){

        ArrayList<String> list =  DataPerso.getCharacter(name);
        this.name = list.get(0);
        this.type = list.get(1);
        if(this.type.compareTo(type) != 0){
            System.out.println("pb de bd : pour : " + name + "on donne le type :" + type + "main on lit : "+ this.type );
            System.exit(3);
        }
        // maxHp = Integer.parseInt(list.get(2)); a voir si on garde
        hp = Integer.parseInt(list.get(2));
        str = Integer.parseInt(list.get(3));
        mag = Integer.parseInt(list.get(4));
        skl = Integer.parseInt(list.get(5));
        spd = Integer.parseInt(list.get(6));
        lck = Integer.parseInt(list.get(7));
        def = Integer.parseInt(list.get(8));
        res = Integer.parseInt(list.get(9));
        mov = Integer.parseInt(list.get(10));
        if(list.size() == 11){
            return;
        }
        wep1 = list.get(11);
        if(list.size() == 12){
            return;
        }
        wep2 = list.get(12);
        if(list.size() == 13){
            return;
        }
        wep3 = list.get(13);
    }

    public Carac(String type) {
        name = "mechant";
        ArrayList<String> list =  DataPerso.getUnite(type);

        for (String s : list){
            System.out.println(s);
        }
        this.type = list.get(0);
        // maxHp = Integer.parseInt(list.get(1)); a voir si on garde
        hp = Integer.parseInt(list.get(1));
        str = Integer.parseInt(list.get(2));
        mag = Integer.parseInt(list.get(3));
        skl = Integer.parseInt(list.get(4));
        spd = Integer.parseInt(list.get(5));
        lck = Integer.parseInt(list.get(6));
        def = Integer.parseInt(list.get(7));
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

    private Carac(Carac c){
        name = c.name;
        type = c.type;
        maxHp = c.maxHp;
        hp = c.hp;
        str = c.str;
        def = c.def;
        spd = c.spd;
        mag = c.mag;
        skl = c.skl;
        lck = c.lck;
        res = c.res;
        mov = c.mov;
        wep1 = c.wep1;
        wep2 = c.wep2;
        wep3 = c.wep3;
    }

    public Carac cloner(){
        return new Carac(this);
    }

    public String getName(){
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getMag() {
        return mag;
    }

    public void setMag(int mag) {
        this.mag = mag;
    }

    public int getSkl() {
        return skl;
    }

    public void setSkl(int skl) {
        this.skl = skl;
    }

    public int getLck() {
        return lck;
    }

    public void setLck(int lck) {
        this.lck = lck;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getMov() {
        return mov;
    }

    public void setMov(int mov) {
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
