package IA;

import java.util.ArrayList;

public class Chemin {

    static int compt = 0;
    int ID;
    int nbChangeField;
    ArrayList<backend.Coordinate> listCoord;

    public Chemin(){
        this.ID = compt;
        compt++;
        this.nbChangeField = 0;
    }

    public void addCoord(backend.Coordinate coordinate){
        this.listCoord.add(coordinate);
    }

    /*backend.Coordinate initialPos;
    backend.Coordinate arrivalPos;*/

}
