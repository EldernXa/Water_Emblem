package backend;

import java.util.Random;

public class Stat {

    public static int damage(Carac attacker, Carac defender) {
        int att = 0;
        int def = 0;
        if (attacker.getWep1().compareTo("Magie") == 0) {
            att += attacker.getMag();
            att += weaponTriangle(attacker.getWep1(), defender.getWep1());
            def += defender.getRes();

        } else {
            att += attacker.getStr();
            def += defender.getDef();
        }

        int adv = defender.getDeplacement();
        if (attacker.getType().equals("Sniper") && (adv == 2 || adv == 3)) {
            att = att * 2;
        }
        int damage = (att - def);
        if (attacker.getSpd() - defender.getSpd() >= 4) {
            damage = damage * 2;
        }
        if (damage < 0) {
            return 0;
        }


        return damage;
    }

    private static int critical(Carac attacker, Carac defender) {
        int crRate = attacker.getSkl() / 2;
        int crEvade = defender.getLck();
        return crRate - crEvade;
    }

    private static boolean rate(int rate) {
        if (rate >= 100) {
            return true;
        }
        Random r = new Random();
        int x = r.nextInt(100) + 1;
        return x <= rate;

    }

    public static int accuracy(Carac attacker, Carac defender) {
        int accu = (((attacker.getSkl()) + (attacker.getLck())) * 8 + weaponTriangle(attacker.getWep1(), defender.getWep1()) * 7);
        int avoid = defender.getSpd()  + defender.getLck();
        return accu - avoid;
    }


    //Epee;Lance;Hache
    public static int weaponTriangle(String wepAttack, String wepDefence) {
        if (wepAttack.compareTo(wepDefence) == 0 || wepAttack.compareTo("Magie") == 0 || wepDefence.compareTo("Magie") == 0) {
            return 0;
        } else if (wepAttack.compareTo("Epee") == 0) {

            if (wepDefence.compareTo("Hache") == 0) {
                return 1;
            } else {
                return -1;
            }
        } else if (wepAttack.compareTo("Hache") == 0) {

            if (wepDefence.compareTo("Lance") == 0) {
                return 1;
            } else {
                return -1;
            }
        } else if (wepAttack.compareTo("Lance") == 0) {

            if (wepDefence.compareTo("Epee") == 0) {
                return 1;
            } else {
                return -1;
            }
        }
        return 0; //nomalement inutile
    }

    public static void calculerStats(Personnage perso) {
        Carac caracteristique = perso.getCaracteristique();
        String arme1 = caracteristique.getWep1();

        int str = caracteristique.getStr();
        int spd = caracteristique.getSpd();
        int skl = caracteristique.getSkl();
        int mag = caracteristique.getMag();
        int lck = caracteristique.getLck();
        switch (arme1) {
            case "Epee": {

                caracteristique.setStr(str + 4);
                caracteristique.setSpd(spd + 2);
                break;
            }
            case "Lance": {
                if(caracteristique.getType().equals("Roi")){
                    caracteristique.setPortee(2);
                }
                caracteristique.setStr(str + 3);
                caracteristique.setLck(lck + 2);

                break;
            }
            case "Hache": {

                caracteristique.setStr(str + 6);
                break;
            }
            case "Arc": {
                caracteristique.setStr(str + 2);
                caracteristique.setSkl(skl + 3);
                caracteristique.setPortee(2);
                break;
            }
            case "Magie": {
                caracteristique.setMag(mag + 4);
                caracteristique.setPortee(2);
                break;
            }

        }
    }

    public static int damageAfterCalc(Carac attacker, Carac defender) {
//        System.out.println(attacker.getName() +" attaque "+ defender.getName());
        int accu = accuracy(attacker, defender);
//        System.out.println("accuracy :" + accu);
//        System.out.println();
        if (!rate(accu)) {
            return 0;
        }
        int att = 0;
        int def = 0;
        if (attacker.getWep1().compareTo("Magie") == 0) {
            att += attacker.getMag();
            att += weaponTriangle(attacker.getWep1(), defender.getWep1());
            def += defender.getRes();

        } else {

            att += attacker.getStr();
            def += defender.getDef();
        }


        int crit = critical(attacker, defender);
        if (!rate(crit)) {
            crit = 1;
        } else {
            crit = 3;
        }
        int adv = defender.getDeplacement();
        if (attacker.getType().equals("Sniper") && (adv == 2 || adv == 3)) {
            att = att * 2;
        }

        int damage = (att - def) * crit;

        if (attacker.getSpd() - defender.getSpd() >= 4) {

            if (rate(accu)) {
                if (rate(critical(attacker, defender))) {
                    damage = (damage + (damage * 3));
                } else {
                    damage = damage * 2;
                }
            }
        }
        if (damage < 0) {
            return 0;
        }
        return damage;
    }
}
