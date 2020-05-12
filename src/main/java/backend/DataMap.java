package backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataMap {


    public void setCoordinate() {
        File map = new File("src/main/resources/data/Map.txt");

        try (Stream<String> stream = Files.lines(Paths.get("Map.txt)"))) {
            stream.forEach(x -> System.out.println(x));


        } catch (IOException e) {

        }

    }

}
