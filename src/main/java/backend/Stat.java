package backend;

import java.util.Random;

public class Stat {

    public static int damage(Carac attacker, Carac defender){
        int accu = accuracy(attacker, defender);
        if(!rate(accu)){
            return 0;
        }
        int att = 0;
        int def = 0;
        if(attacker.getWep1().compareTo("Magie") == 0){
            att += attacker.getMag();
            att += weaponTriangle(attacker.getWep1(), defender.getWep1());

        }
        else {
            att += attacker.getStr();

        }

        if(defender.getWep1().compareTo("Magie") == 0){
            def += defender.getRes();
        }
        else {
            def += defender.getDef();
        }
        int crit = critical(attacker, defender);
        if(!rate(crit)){
            crit = 1;
        }
        else {
            crit = 3;
        }
        int damage = (att - def) * crit ;

        if(attacker.getSpd() - defender.getSpd() >= 4){
            damage = (int) (damage * 1.4);
        }
        if(damage < 0 ){
            return 0;
        }
        return damage;
    }

    private static int critical(Carac attacker, Carac defender){
        int crRate = attacker.getSkl() / 2;
        int crEvade = defender.getLck();
        return crRate - crEvade;
    }

    private static boolean rate(int rate){
        if(rate >= 100){
            return true;
        }
        Random r = new Random();
        int x = r.nextInt(100) + 1;

        return x <= rate;

    }
    private static int accuracy(Carac attacker, Carac defender){
        int accu = ((attacker.getSkl() *3) + (attacker.getLck()) + weaponTriangle(attacker.getWep1(), defender.getWep1()) * 15) *3;
        int avoid = defender.getSpd() + defender.getLck();

        int x = accu - avoid;
        if ((x) <= 15){
            if(x <= 0){
                x = 0;
            }
            Random r = new Random();
            return r.nextInt(15 - x) + x;
        }
        return x;
    }


    //Epee;Lance;Hache
    public static int weaponTriangle(String wepAttack, String wepDefence){
        if(wepAttack.compareTo(wepDefence) == 0 || wepAttack.compareTo("Magie") == 0 ||wepDefence.compareTo("Magie") == 0 ){
            return 0;
        }

        else if(wepAttack.compareTo("Epee") == 0){

            if(wepDefence.compareTo("Hache") == 0){
                return 1;
            }
            else{
                return -1;
            }
        }

        else if(wepAttack.compareTo("Hache") == 0){

            if(wepDefence.compareTo("Lance") == 0){
                return 1;
            }
            else{
                return -1;
            }
        }

        else if(wepAttack.compareTo("Lance") == 0){

            if(wepDefence.compareTo("Epee") == 0){
                return 1;
            }
            else{
                return -1;
            }
        }
        return 0; //nomalement inutile
    }

    public static void calculerStats(Personnage perso){
        Carac caracteristique = perso.getCaracteristique();
        String arme1 = caracteristique.getWep1();

        int str = caracteristique.getStr();
        switch (arme1){
            case "Epee":{

                caracteristique.setStr(str + 4);
                break;
            }
            case "Lance": {
                caracteristique.setStr(str + 3);

                break;
            }
            case "Hache": {

                caracteristique.setStr(str + 2);
                break;
            }
            case "Arc" : {
                caracteristique.setStr(str + 2);
                caracteristique.setPortee(2);
                break;
            }
            case "Magie" : {
                caracteristique.setPortee(2);
                break;
            }

        }
    }
}
