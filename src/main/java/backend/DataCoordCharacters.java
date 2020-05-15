package backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class DataCoordCharacters {
    private final String path;
    private final DataMap dataMap;

    public DataCoordCharacters(String map) {
        path = "src/main/resources/dataCoordCharacters/" + map + ".co";
        dataMap = new DataMap(map);

    }


    public void setCoord(String nameOrUnit, int x, int y, int camp) {
        File file = new File(path);

        try {
            Scanner scanner = new Scanner(file);


            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;

                if (line.contains(nameOrUnit)) {
                    if (DataPerso.getCharacter(nameOrUnit) != null) {
                        System.out.println("Personage deja dans le fichier");
                        return;
                    }

                }
                if (line.contains(x + ";" + y)) {
                    System.out.println("Coordonés deja dans le fichier");
                    return;
                }
            }
        } catch (java.io.FileNotFoundException e) {

        }


        if ((DataPerso.getCharacter(nameOrUnit) != null || DataPerso.getUnite(nameOrUnit) != null) && dataMap.getMapLimits().get(0) >= x && dataMap.getMapLimits().get(1) >= y) {
            try {


                String xCoo = String.valueOf(x);
                String yCoo = String.valueOf(y);
                Files.write(Paths.get(path), nameOrUnit.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), ";".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), xCoo.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), ";".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), yCoo.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), ";".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), Integer.toString(camp).getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), "\n".getBytes(), StandardOpenOption.APPEND);


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pas de personnage de ce nom");
        }
    }

    private ArrayList<Object> getData(int nameOrCoord1, int camp) {

        ArrayList<String> unite = new ArrayList<>();
        ArrayList<Object> statsunite = new ArrayList<>();

        try {
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                stream.forEach(x -> unite.add(x));
                int flag = 0;
                for (int i = 0; i < unite.size(); i++) {

                    String[] parts = unite.get(i).split(";");

                    if (parts[3].equals(String.valueOf(camp))) {
                        if (nameOrCoord1 == 0) {
                            statsunite.add(parts[0]);
                        } else if (nameOrCoord1 == 1) {
                            ArrayList<String> coord = new ArrayList<>();
                            coord.add(parts[1]);
                            coord.add(parts[2]);
                            statsunite.add(coord);
                        }

                    }


                }
                return statsunite;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ArrayList<String> getGentilCharactersList() {

        ArrayList test = new ArrayList();
        test = getData(0, 0);

        ArrayList<String> strList = (ArrayList<String>) (ArrayList<?>) (test);

        return strList;
    }

    public ArrayList<String> getMechantCharactersList() {
        ArrayList test = new ArrayList();
        test = getData(0, 1);

        ArrayList<String> strList = (ArrayList<String>) (ArrayList<?>) (test);

        return strList;
    }


    public ArrayList<ArrayList<String>> getGentilCoord() {
        ArrayList test = new ArrayList();
        test = getData(1, 0);

        ArrayList<ArrayList<String>> strList = (ArrayList<ArrayList<String>>) (ArrayList<?>) (test);

        return strList;
    }

    public ArrayList<ArrayList<String>> getMechantCoord() {
        ArrayList test = new ArrayList();
        test = getData(1, 1);

        ArrayList<ArrayList<String>> strList = (ArrayList<ArrayList<String>>) (ArrayList<?>) (test);

        return strList;
    }
}
