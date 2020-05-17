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
                caracPerso.setPorte(2);
            }
            case "Hache": {
                caracPerso.setStr(str + 2);
            }
            case "Arc" : {
                caracPerso.setStr(str + 2);
                caracPerso.setPorte(5);
            }
            case "Magie" : {
                caracPerso.setPorte(3);

            }
        }
    }

}
