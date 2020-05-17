package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class AffichageGraphique {
    static int size = 50;
    AfficheMap afficheMap;
    AffichePerso affichePerso;
    GridPane map;
    static public GridPane perso;
    static public GridPane grilleMvt;

    public Pane init() {
        GridPane root = new GridPane();
        root.setAlignment(Pos.TOP_LEFT);
        File file = new File("./src/main/resources/dataMap");
        ArrayList<String> list = new ArrayList<>();
        ObservableList<String> listMap;
        for(File file1 : Objects.requireNonNull(file.listFiles()))
        {
            if(file1.getName().substring(file1.getName().length()-4).compareTo(".txt")==0){
                list.add(file1.getName().substring(0, file1.getName().length()-4));
            }

        }
        listMap = FXCollections.observableArrayList(list);
        Label txt = new Label("Quel map voulez-vous ?");
        ChoiceBox<String> choiceMap = new ChoiceBox<>(listMap);
        choiceMap.setValue(choiceMap.getItems().get(0));
        Button start = new Button("Commencer");


        start.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                afficheMap = new AfficheMap(choiceMap.getValue());
                grilleMvt = new GridPane();
                grilleMvt.setAlignment(Pos.TOP_LEFT);
                grilleMvt.setVgap(AffichageGraphique.size);
                grilleMvt.setHgap(AffichageGraphique.size);
                for(int i=0; i<AfficheMap.y;i++)
                {
                    grilleMvt.getColumnConstraints().add(new ColumnConstraints(0));
                }
                for(int i=0; i<AfficheMap.x;i++)
                    grilleMvt.getRowConstraints().add(new RowConstraints(0));
                map = afficheMap.getMap();
                affichePerso = new AffichePerso(choiceMap.getValue());
                perso = affichePerso.getGridPanePerso();
                map.setAlignment(Pos.TOP_LEFT);
                perso.setAlignment(map.getAlignment());
                root.getChildren().clear();
                Event.clickOnMap(perso, affichePerso);
                root.getChildren().addAll(map, grilleMvt, perso);
            }
        });

        root.add(txt, 0, 0);
        root.add(choiceMap, 0, 1);
        root.add(start, 1, 1);
        //root.getChildren().addAll(txt, choiceMap);
        return root;
    }
}
