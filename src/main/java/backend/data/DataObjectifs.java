package backend.data;

public class DataObjectifs {

    private final String path;
    private final DataMap dataMap;


    public DataObjectifs(String map) {
        path = "src/main/resources/dataObjectif/" + map + ".obj";
        dataMap = new DataMap(map);

    }
}
