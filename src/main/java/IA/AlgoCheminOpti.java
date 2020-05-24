package IA;

import backend.Coordinate;

import java.util.ArrayList;
import java.util.Stack;

public class AlgoCheminOpti {

    ArrayList<backend.Coordinate> posMorte;
    Stack<Coordinate> pilePos;
    backend.Coordinate initialPos;
    backend.Coordinate arrivalPos;
    ArrayList<Coordinate> chemin;
    ArrayList<ArrayList<Coordinate>> map;

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
