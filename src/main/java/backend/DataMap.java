package backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DataMap {

    String path;
    ArrayList<ArrayList<Coordinate>> coordiantes;

    public DataMap(String map) {
        path = "src/main/resources/data/" + map + ".txt";

    }

    public ArrayList<ArrayList<Coordinate>> setCoordinate() {
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


                        Coordinate test = new Coordinate(i-1,j);
                        test.setField(getField(ary[j]));
                        //System.out.println(test.getX()+" " +test.getY() + " " +test.getField());

                        yCoordinates.add(test);
                    }
                    xCoordinates.add(yCoordinates);
                }

            }

            coordiantes = xCoordinates;


//            for(int i =0; i < coordiantes.size(); i++){
//                for( int j = 0; j < coordiantes.get(i).size() ; j++){
//                    System.out.println(coordiantes.get(i).get(j).getX() + " " + coordiantes.get(i).get(j).getY() + " "+ coordiantes.get(i).get(j).getField() );
//                }
//            }


        return coordiantes;






        } catch (IOException e) {

        }
        return null;
    }

    private Field getField(String field) {

        switch (field ) {

            case "F":
                return new Forest();
            case "V":
                return new Volcano();
            case "R":
                return new River();
            case "-":
                return new Plaine();
        }

        return null;
    }


}
