package backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DataCoordCharacters {
    private String path;
    private DataMap dataMap;

    public DataCoordCharacters(String map) {
        path = "src/main/resources/dataCoordCharacters/" + map + ".co";
        dataMap = new DataMap(map);

    }

    public void setCoordCharacter(String name, int x, int y) {


        if (DataPerso.getCharacter(name) != null && dataMap.getMapLimits().get(0) >= x && dataMap.getMapLimits().get(1) >= y) {
            try {


                String xCoo = String.valueOf(x);
                String yCoo = String.valueOf(y);
                //inclures une condition sur les limites de la map
                Files.write(Paths.get(path), name.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), ";".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), xCoo.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), ";".getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), yCoo.getBytes(), StandardOpenOption.APPEND);
                Files.write(Paths.get(path), "\n".getBytes(), StandardOpenOption.APPEND);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("NONO");
        }

    }


}
