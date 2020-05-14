package backend;

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


                        Coordinate test = new Coordinate(i-1,j);
                        test.setField(getField(ary[j]));
    

                        yCoordinates.add(test);
                    }
                    xCoordinates.add(yCoordinates);
                }

            }

            this.coordinates = xCoordinates;


            mapLimits.add(coordinates.size());
            mapLimits.add(coordinates.get(0).size());



        



        } catch (IOException e) {
            coordinates = null;
        }

    }

    public ArrayList<ArrayList<Coordinate>> getcoordinates(){
        return coordinates;
    }

d

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

    public ArrayList<Integer> getMapLimits(){
        return mapLimits;
    }


}
