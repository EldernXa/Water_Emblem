package IA;

import backend.Coordinate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AlgoCheminOpti {

    ArrayList<backend.Coordinate> posMorte;
    Coordinate X;
    Coordinate Y;
    Stack<Coordinate> pilePos;
    backend.Coordinate initialPos;
    backend.Coordinate arrivalPos;
    ArrayList<Coordinate> chemin;
    ArrayList<ArrayList<Coordinate>> map;
    int pointCardinal ;

    public AlgoCheminOpti(backend.Coordinate initialPos, backend.Coordinate arrivalPos, ArrayList<ArrayList<Coordinate>> map){
        this.initialPos = initialPos;
        this.arrivalPos = arrivalPos;
        this.map = map;
        pilePos = new Stack<>();
        pilePos.add(map.get(initialPos.getX()).get(initialPos.getY()));
    }

    /*public AlgoCheminOpti(backend.Coordinate initialPos, backend.Coordinate arrivalPos, ArrayList<ArrayList<Coordinate>> map){
        double distInitArri = calculDistance(initialPos,arrivalPos);
        Chemin chemin = new Chemin();
        chemin.addCoord(initialPos);

        if (!map.get(initialPos.getX()+1).get(initialPos.getY()).getField().isCrossable()){
            posMorte.add(map.get(initialPos.getX()+1).get(initialPos.getY()));
        }
        else(calculDistance(new Coordinate(initialPos.getX()+1,initialPos.getY()),arrivalPos)<distInitArri){

            if(!map.get(initialPos.getX()).get(initialPos.getY()).getField().compareField(map.get(initialPos.getX()+1).get(initialPos.getY()).getField())){

            }
        }*/

    public ArrayList<backend.Coordinate> AlgoCheminOptiFct(backend.Coordinate initialPos, backend.Coordinate arrivalPos, ArrayList<ArrayList<Coordinate>> map){
        double distInitArri = calculDistance(initialPos,arrivalPos);
        posMorte.add(initialPos);

        if(!initialPos.equal(arrivalPos)) {
            if (!map.get(initialPos.getX() + 1).get(initialPos.getY()).getField().isCrossable()) {
                posMorte.add(map.get(initialPos.getX() + 1).get(initialPos.getY()));
            } else if (calculDistance(map.get(initialPos.getX() + 1).get(initialPos.getY()), arrivalPos) < distInitArri) {
                if(!belongsToListCoord(posMorte,map.get(initialPos.getX() + 1).get(initialPos.getY()))) {
                    pilePos.push(map.get(initialPos.getX() + 1).get(initialPos.getY()));
                }
            }

            if (!map.get(initialPos.getX() - 1).get(initialPos.getY()).getField().isCrossable()) {
                posMorte.add(map.get(initialPos.getX() - 1).get(initialPos.getY()));
            } else if (calculDistance(map.get(initialPos.getX() - 1).get(initialPos.getY()), arrivalPos) < distInitArri) {
                if(!belongsToListCoord(posMorte,map.get(initialPos.getX() - 1).get(initialPos.getY()))) {
                    pilePos.push(map.get(initialPos.getX() - 1).get(initialPos.getY()));
                }
            }

            if (!map.get(initialPos.getX()).get(initialPos.getY() + 1).getField().isCrossable()) {
                posMorte.add(map.get(initialPos.getX()).get(initialPos.getY() + 1));
            } else if (calculDistance(map.get(initialPos.getX()).get(initialPos.getY() + 1), arrivalPos) < distInitArri) {
                if(!belongsToListCoord(posMorte,map.get(initialPos.getX()).get(initialPos.getY() + 1)))
                pilePos.push(map.get(initialPos.getX()).get(initialPos.getY() + 1));
            }

            if (!map.get(initialPos.getX()).get(initialPos.getY() - 1).getField().isCrossable()) {
                posMorte.add(map.get(initialPos.getX()).get(initialPos.getY() - 1));
            } else if (calculDistance(map.get(initialPos.getX()).get(initialPos.getY() - 1), arrivalPos) < distInitArri) {
                if(!belongsToListCoord(posMorte,map.get(initialPos.getX()).get(initialPos.getY() - 1))) {
                    pilePos.push(map.get(initialPos.getX()).get(initialPos.getY() - 1));
                }
            }

            AlgoCheminOptiFct(pilePos.peek(),arrivalPos,map);
            return null;
        }
        else{
            return chemin;
        }

    }

    /*public ArrayList<ArrayList<Coordinate>> AlgoCheminOptiFct1(backend.Coordinate initialPos, backend.Coordinate arrivalPos, ArrayList<ArrayList<Coordinate>> map){

        double distInitArri = calculDistance(initialPos,arrivalPos);

        if(!initialPos.equal(arrivalPos)) {
            if(initialPos.getX() < arrivalPos.getX()){
                if (!map.get(initialPos.getX() + 1).get(initialPos.getY()).getField().isCrossable()) {
                    posMorte.add(map.get(initialPos.getX() + 1).get(initialPos.getY()));
                }
                else {
                    if(!belongsToListCoord(posMorte,map.get(initialPos.getX() + 1).get(initialPos.getY()))) {
                        pilePos.offer(map.get(initialPos.getX() + 1).get(initialPos.getY()));
                        AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                    }
                }
            }

            else if(initialPos.getX() == arrivalPos.getX()){
                if(initialPos.getY() < arrivalPos.getY()){
                    if (!map.get(initialPos.getX()).get(initialPos.getY() + 1).getField().isCrossable()) {
                        posMorte.add(map.get(initialPos.getX()).get(initialPos.getY() + 1));
                        AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                    }
                    else {
                        if(!belongsToListCoord(posMorte,map.get(initialPos.getX()).get(initialPos.getY() + 1))) {
                            pilePos.offer(map.get(initialPos.getX()).get(initialPos.getY() + 1));
                            AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                        }
                    }
                }
                else {
                    if (!map.get(initialPos.getX()).get(initialPos.getY() - 1).getField().isCrossable()) {
                        posMorte.add(map.get(initialPos.getX()).get(initialPos.getY() - 1));
                        AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                    }
                    else {
                        if(!belongsToListCoord(posMorte,map.get(initialPos.getX()).get(initialPos.getY() - 1))) {
                            pilePos.offer(map.get(initialPos.getX()).get(initialPos.getY() - 1));
                            AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                        }
                    }
                }
            }

            else{
                if (!map.get(initialPos.getX() -1).get(initialPos.getY()).getField().isCrossable()) {
                    posMorte.add(map.get(initialPos.getX() - 1).get(initialPos.getY()));
                    AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                }
                else {
                    if(!belongsToListCoord(posMorte,map.get(initialPos.getX() - 1).get(initialPos.getY()))) {
                        pilePos.offer(map.get(initialPos.getX() - 1).get(initialPos.getY()));
                        AlgoCheminOptiFct1(pilePos.poll(),arrivalPos,map);
                    }
                }
            }
        }
        return null;
    }*/

    /*public ArrayList<Coordinate> AlgoChemin(Coordinate depart, Coordinate arrivee, ArrayList<ArrayList<Coordinate>> map){
        if(!depart.equal(arrivalPos)){
            if(depart.getX() < arrivee.getX()){
                if (depart.getY() < arrivee.getY()){

                }
                else {

                }
            }
            else{
                if (depart.getY() < arrivee.getY()){

                }
                else{

                }

            }
        }



        return chemin;
    }*/

    public ArrayList<Coordinate> algoChemin(Coordinate debut, Coordinate fin, ArrayList<ArrayList<Coordinate>> map){
        if(debut.getX() < fin.getX()){
            if(map.get(debut.getX()+1).get(debut.getY()).getField().isCrossable()){
                chemin.add(map.get(debut.getX()+1).get(debut.getY()));
                X = map.get(debut.getX()).get(debut.getY());
                algoChemin(map.get(debut.getX()+1).get(debut.getY()),fin,map);
            }
            else{
                if(debut.getY() < fin.getY()){
                    if(map.get(debut.getX()).get(debut.getY()+1).getField().isCrossable()){
                        algoChemin(map.get(debut.getX()).get(debut.getY()+1),fin,map);
                    }

                }
            }
        }
        if(debut.getX() == fin.getX()){
            if(debut.getY() < fin.getY()){
                chemin.add(map.get(debut.getX()).get(debut.getY()+1));
                algoChemin(map.get(debut.getX()).get(debut.getY()+1),fin,map);
            }
        }
        return chemin;
    }

    public static boolean belongsToListCoord(ArrayList<Coordinate> listCoord, Coordinate coordinate){
        for(Coordinate coord : listCoord){
            if(coordinate.equal(coord)){
                return true;
            }
        }
        return false;
    }
    public double calculDistance(backend.Coordinate initialPos, backend.Coordinate arrivalPos){
        return Math.sqrt(Math.pow(arrivalPos.getX()-initialPos.getX(),2) + Math.pow(arrivalPos.getY()-initialPos.getY(),2));
    }
}
