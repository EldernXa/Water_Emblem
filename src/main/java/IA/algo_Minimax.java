package IA;

public class algo_Minimax {


    static public Etat startMini(Etat etat, int depth, boolean maxi){
        int value ;
        Etat etatFin = etat;
        if(maxi){
            value = -100000;
            for (Etat e : etat.getToutPossibilité(maxi)){
                int x =  minimax(e, depth - 1, !maxi);
                if(x > value){
                    value = x;
                    etatFin = e;
                }
            }
            return etatFin;
        }

        else{
            value = 100000;
            for (Etat e : etat.getToutPossibilité(maxi)){
                int x =  minimax(e, depth - 1, !maxi);
                if(x < value){
                    value = x;
                    etatFin = e;
                }
            }
            return etatFin;
        }
    }

    private static int minimax(Etat etat, int depth, boolean maxi){
        int value;
        if(depth == 0 || etat.estFinal()){
            return etat.getValHeuristique();
        }

        if(maxi){
            value = -100000;
            for (Etat e : etat.getToutPossibilité(maxi)){
                value = Math.max(value, minimax(e, depth - 1, !maxi));
            }
            return value;
        }

        else{
            value = 100000;
            for (Etat e : etat.getToutPossibilité(maxi)){
                value = Math.min(value, minimax(e, depth - 1, !maxi));
            }
            return value;
        }
    }
}
