package backend;

public class CalculStats {

    public CalculStats(Personnage personnage) {
            Carac caracPerso = personnage.getCaracteristique();
            String arme1 = caracPerso.getWep1();
            int str = caracPerso.getStr();
            switch (arme1){
                case "Epee":{
                    caracPerso.setStr(str + 4);
                }
                case "Lance": {
                    caracPerso.setStr(str + 3);
                }
                case "Hache": {
                    caracPerso.setStr(str + 2);

                }
            }
    }

}