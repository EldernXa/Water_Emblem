package backend;

import backend.data.DataPerso;

import java.util.ArrayList;

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
    private int porte = 0;
    private String wep1;
    private String wep2;
    private String wep3;
    // Prenom / nomUnite / HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3
    //nom / type /HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3

    public Carac(String name){
        int i = 0;
        ArrayList<String> list;
        if(DataPerso.getCharacter(name) != null){
            list =  DataPerso.getCharacter(name);
            this.name = list.get(0);
            i++;
        }
        else if(DataPerso.getUnite(name) != null){
            list =  DataPerso.getUnite(name);
            this.name = "mechant";
        }

        else {
            System.out.println("creation d un carac sans un nom de perso ou de type, inconnu : " + name);
            System.exit(3);
            return;
        }

        this.type = list.get(i++);

        maxHp = hp = Integer.parseInt(list.get(i++));
        str = Integer.parseInt(list.get(i++));
        mag = Integer.parseInt(list.get(i++));
        skl = Integer.parseInt(list.get(i++));
        spd = Integer.parseInt(list.get(i++));
        lck = Integer.parseInt(list.get(i++));
        def = Integer.parseInt(list.get(i++));
        res = Integer.parseInt(list.get(i++));
        mov = Integer.parseInt(list.get(i++));
        if(list.size() == i){
            return;
        }
        wep1 = list.get(i++);
        if(list.size() == i){
            return;
        }
        wep2 = list.get(i++);
        if(list.size() == i){
            return;
        }
        wep3 = list.get(i);

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

    public int getPorte() {
        return porte;
    }

    public void setPorte(int porte) {
        this.porte = porte;
    }
}
