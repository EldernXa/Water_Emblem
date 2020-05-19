package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;


public class AffichageGraphique {
    static int size = 50;
    private AfficheMap afficheMap;
    private AffichePerso affichePerso;
    private GridPane map;
    private GridPane perso;
    private GridPane grilleMvt;
    private GridPane grilleAttack;
    private VBox panel;
    private HBox window;
    private VBox information;
    private VBox button;

    public Pane init() {
        Button move = new Button("Bouger");
        try {
            Image mvtImg = new Image(new FileInputStream("src/main/resources/bouger.png"));
            ImageView mvtImage = new ImageView(mvtImg);
            move.setGraphic(mvtImage);
        }catch(Exception e){
            System.out.println("Image bouger pose probleme");
        }
        move.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Image mvtImgHov = new Image(new FileInputStream("src/main/resources/bouger_hover.png"));
                    ImageView mvtImageHov = new ImageView(mvtImgHov);
                    move.setGraphic(mvtImageHov);
                }catch(Exception e){
                    System.out.println("Image bouger_hover pose probleme");
                }
            }
        });
        move.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Image mvtImg = new Image(new FileInputStream("src/main/resources/bouger.png"));
                    ImageView mvtImage = new ImageView(mvtImg);
                    move.setGraphic(mvtImage);
                }catch(Exception e){
                    System.out.println("Image bouger pose probleme");
                }
            }
        });
        Button attack = new Button("Attaquer");
        try {
            Image attackImg = new Image(new FileInputStream("src/main/resources/attaquer.png"));
            ImageView attackImage = new ImageView(attackImg);
            attack.setGraphic(attackImage);
        }catch(Exception e){
            System.out.println("Image attaquer pose probleme");
        }
        Button stay = new Button("Rien faire");
        move.setVisible(false);
        attack.setVisible(false);
        stay.setVisible(false);
        window = new HBox();
        window.setSpacing(50);
        GridPane root = new GridPane();
        window.getChildren().add(root);
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
                panel = new VBox();
                information = new VBox();
                information.setMaxHeight(150);
                information.setMinHeight(150);
                button = new VBox();
                button.getChildren().addAll(move, attack, stay);
                panel.getChildren().addAll(information, button);
                grilleMvt = initGridPane();
                grilleAttack = initGridPane();
                map = afficheMap.getMap();
                affichePerso = new AffichePerso(choiceMap.getValue());
                perso = affichePerso.getGridPanePerso();
                map.setAlignment(Pos.TOP_LEFT);
                perso.setAlignment(map.getAlignment());
                root.getChildren().clear();
                Event.buttonStay(stay, information, move, attack, grilleMvt, grilleAttack);
                Event.clickOnMap(perso, affichePerso, grilleMvt, grilleAttack, information,
                        move, attack, stay);

                root.getChildren().addAll(map, grilleMvt, grilleAttack, perso);
                window.getChildren().add(panel);
            }
        });

        root.add(txt, 0, 0);
        root.add(choiceMap, 0, 1);
        root.add(start, 1, 1);
        //root.getChildren().addAll(txt, choiceMap);
        return window;
    }

    private GridPane initGridPane(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setVgap(AffichageGraphique.size);
        gridPane.setHgap(AffichageGraphique.size);
        for(int i=0; i<AfficheMap.x;i++)
            gridPane.getRowConstraints().add(new RowConstraints(0));
        for(int i=0; i<AfficheMap.y; i++)
            gridPane.getColumnConstraints().add(new ColumnConstraints(0));
        return gridPane;
    }
}
