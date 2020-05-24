package backend.data;

import backend.Coordinate;

import backend.field.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DataMap {

    private String path;
    private ArrayList<ArrayList<Coordinate>> coordinates;
    private ArrayList<Integer> mapLimits;

    public DataMap(String map) {
        mapLimits = new ArrayList<>();
        path = "src/main/resources/dataMap/" + map + ".txt";
        String pathCoord = "src/main/resources/dataCoordCharacters/" + map + ".co";
        File f = new File(pathCoord);
        if (!(f.exists() && !f.isDirectory())) {

            try {

                f.getParentFile().mkdirs();
                f.createNewFile();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setCoordinate();
    }

    private void setCoordinate() {
        File map = new File(path);


        ArrayList<String> fileLine = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(x -> fileLine.add(x));
            ArrayList<ArrayList<Coordinate>> xCoordinates = new ArrayList<>();
            for (int i = 1; i < fileLine.size(); i++) {

                ArrayList<Coordinate> yCoordinates = new ArrayList<>();
                String[] ary = fileLine.get(i).split("");

                if (!ary[0].equals("/")) {
                    for (int j = 0; j < ary.length; j++) {


                        Coordinate test = new Coordinate(j, i - 1);
                        test.setField(getField(ary[j]));


                        yCoordinates.add(test);
                    }
                    xCoordinates.add(yCoordinates);
                }

            }


            this.coordinates = xCoordinates;

            /*for (int a = 0; a < coordinates.size(); a++) {
                for (int b = 0; b < coordinates.size(); b++) {

                    System.out.println(coordinates.get(a).get(b).getField());
                    System.out.println(coordinates.get(a).get(b).getX() + " " + coordinates.get(a).get(b).getY());
                }
                System.out.println();
            }*/
            mapLimits.add(coordinates.size());
            mapLimits.add(coordinates.get(0).size());


        } catch (IOException e) {
            coordinates = null;
        }

    }

    public ArrayList<ArrayList<Coordinate>> getcoordinates() {
        return coordinates;
    }

    public Coordinate getThisCoordinate(int x, int y) {

        return coordinates.get(x).get(y);
    }


    private Field getField(String field) {

        switch (field) {

            case "F":
                return new Forest();
            case "V":
                return new Volcan9();
            case "R":
                return new River();
            case "-":
                return new Plaine();
            case "[":
                return new CornicheD();
            case "=":
                return new CornicheM1();
            case "~":
                return new CornicheM2();
            case "]":
                return new CornicheF();
            case "H":
                return new CornicheM();
            case "$":
                return new CornicheB();
            case "P":
                return new Pilier();
            case "s":
                return new Sol();
            case "1":
                return new Volcan1();
            case "2":
                return new Volcan2();
            case "3":
                return new Volcan3();
            case "4":
                return new Volcan4();
            case "5":
                return new Volcan5();
            case "6":
                return new Volcan6();
            case "7":
                return new Volcan7();
            case "&":
                return new Gravier1();
            case "g":
                return new Gravier2();
            case "'":
                return new Gravier3();
            case "(":
                return new Gravier4();
            case "h":
                return new Gravier5();
            case "_":
                return new Gravier6();
            case "v":
                return new Gravier7();
            case "<":
                return new Gravier8();
            case ">":
                return new Gravier9();
            case "B" :
                return new Bastion();
            case "," :
                return new Barriere1();
            case ";" :
                return new Barriere2();
            case ":" :
                return new Barriere3();
            case "j" :
                return new BordD1();
            case "*" :
                return new BordD2();
            case "^" :
                return new BordD3();
            case ")" :
                return new BordD4();
            case "L" :
                return new BordD5();
            case "O" :
                return new BordD6();
            case "I" :
                return new BordD7();
            case "a" :
                return new BordG1();
            case "N" :
                return new BordG2();
            case "M" :
                return new BordG3();
            case "?" :
                return new BordG4();
            case "." :
                return new BordG5();
            case "A" :
                return new BordG6();
            case "Z" :
                return new BordG7();
            case "E" :
                return new Bord1();
            case "q":
                return new Eau1();
            case "f" :
                return new Eau3();
            case "d" :
                return new Eau4();
            case "+" :
                return new Eau6();
            case "X" :
                return new Eau7();
            case "p" :
                return new Escalier1();
            case "C" :
                return new Escalier2();
            case "{" :
                return new Escalier3();
            case "9" :
                return new Mur1();
            case "w" :
                return new Mur2();
            case "W" :
                return new Sol1();
            case "x" :
                return new Sol2();
            case "n" :
                return new Sol4();
            case "b" :
                return new Sol5();
            case "!" :
                return new Sol6();
            case "#" :
                return new Trappe();
            case "l" :
                return new Porte1();
            case "Q" :
                return new Porte2();
            case "S" : 
                return new Ponton1();
            case "D" : 
                return new Ponton2();
            case "z" :
                return new CoqueD();
            case "e" :
                return new CoqueG();
            case "r" :
                return new CoqueHaut();
            case "t" :
                return new CoqueHautD();

            case "y" :
                return new CoqueHautG();
            case "u" :
                return new CoqueM();
            case "i" :
                return new CoquePonton();

            default:
                return new Plaine();
        }


    }

    public ArrayList<Integer> getMapLimits() {
        return mapLimits;
    }


}
