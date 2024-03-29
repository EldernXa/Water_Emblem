package IA;

public class Algo_Minimax {


    static public Etat startMini(Etat etat, int depth, boolean maxi){
        int value ;
        int alpha = -100000;
        int beta = 100000;
        Etat etatFin = etat;
        if(maxi){
            value = -100000;
            for (Etat e : etat.getToutPossibilite(maxi)){
                int x =  minimax(e, depth - 1, !maxi, alpha, beta);
                if(alpha >= x){
                    return e;
                }
                beta = Math.min(beta, x);
                if(x > value){
                    value = x;
                    etatFin = e;
                }
            }
            return etatFin;
        }

        else{
            value = 100000;
            for (Etat e : etat.getToutPossibilite(maxi)){
                int x =  minimax(e, depth - 1, !maxi, alpha , beta);
                if(x >= beta){
                    return e;
                }
                alpha = Math.max(alpha, x);
                if(x < value){
                    value = x;
                    etatFin = e;
                }
            }
            return etatFin;
        }
    }

    private static int minimax(Etat etat, int depth, boolean maxi, int alpha, int beta){
        int value;
        if(depth == 0 || etat.estFinal()){
            return etat.getValHeuristique();
        }

        if(maxi){
            value = -100000;
            for (Etat e : etat.getToutPossibilite(maxi)){
                value = Math.max(value, minimax(e, depth - 1, !maxi, alpha, beta));
                if(alpha >= value){
                    return value;
                }
                beta = Math.min(beta, value);
            }
            return value;
        }

        else{
            value = 100000;
            for (Etat e : etat.getToutPossibilite(maxi)){
                value = Math.min(value, minimax(e, depth - 1, !maxi, alpha, beta));
                if(value >= beta){
                    return value;
                }
                alpha = Math.max(alpha, value);
            }
            return value;
        }
    }
}
