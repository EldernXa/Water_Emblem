package frontend;

import backend.PersonnageDisplay;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

import java.awt.*;
import java.beans.Expression;
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
    private ScrollPane scrollPane;
    private VBox console;
    public static GridPane group;

    public Pane init() {
        Button move = new Button("Bouger");
        try {
            Image mvtImg = new Image(new FileInputStream("src/main/resources/icoButtons/bouger.png"));
            ImageView mvtImage = new ImageView(mvtImg);
            Image mvtImgHov = new Image(new FileInputStream("src/main/resources/icoButtons/bouger_hover.png"));
            ImageView mvtImageHov = new ImageView(mvtImgHov);
            move.graphicProperty().bind(Bindings.when(move.hoverProperty()).then(mvtImageHov).otherwise(mvtImage));
        }catch(Exception e){
            System.out.println(e);
        }

        Button attack = new Button("Attaquer");
        try {

            Image attackImg = new Image(new FileInputStream("src/main/resources/icoButtons/attaquer.png"));
            ImageView attackImage = new ImageView(attackImg);
            Image attackImgHov = new Image(new FileInputStream("src/main/resources/icoButtons/attaquer_hover.png"));
            ImageView attackImageHov = new ImageView(attackImgHov);
            attack.graphicProperty().bind(Bindings.when(attack.hoverProperty()).then(attackImageHov).otherwise(attackImage));
        }catch(Exception e){
            System.out.println(e);
        }

        Button stay = new Button("Attendre");
        Button endTurn = new Button("Terminer le tour");
        Button carac = new Button("Caracteristique");
        carac.setVisible(false);
        move.setVisible(false);
        attack.setVisible(false);
        stay.setVisible(false);
        endTurn.setVisible(false);
        endTurn.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent evnt){
               for(PersonnageDisplay p: AffichePerso.listPersonnage) {
                   p.setOrientation(5);
                   p.setEndTurn(true);
               }
               stay.fire();
           }
        });
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
                group = new GridPane();
                group.setAlignment(Pos.TOP_LEFT);
                afficheMap = new AfficheMap(choiceMap.getValue());
                panel = new VBox();
                HBox informationAndCombat = new HBox();
                VBox glancing = new VBox();
                VBox combat = new VBox();
                combat.setMaxHeight(200);
                combat.setMaxHeight(200);
                information = new VBox();
                information.setMaxHeight(200);
                information.setMinHeight(200);
                informationAndCombat.getChildren().addAll(information, combat, glancing);
                console = new VBox();
                scrollPane = new ScrollPane();
                console.heightProperty().addListener(observable->scrollPane.setVvalue(1D));
                scrollPane.setContent(console);
                scrollPane.setMinSize(500, 500);
                scrollPane.setMaxSize(500, 500);
                scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                console.setStyle("-fx-background-color: #36393F;");
                console.setVisible(true);
                console.setMinSize(scrollPane.getMinWidth(), scrollPane.getMinHeight());
                button = new VBox();
                endTurn.setVisible(true);
                button.setSpacing(10);
                button.getChildren().addAll(carac, move, attack, stay, endTurn);
                panel.getChildren().addAll(informationAndCombat, button, scrollPane);
                grilleMvt = initGridPane();
                grilleAttack = initGridPane();
                map = afficheMap.getMap();
                affichePerso = new AffichePerso(choiceMap.getValue());
                perso = affichePerso.getGridPanePerso();
                map.setAlignment(Pos.TOP_LEFT);
                perso.setAlignment(map.getAlignment());
                root.getChildren().clear();
                map.setMaxHeight(root.getHeight());
                map.setMinHeight(root.getHeight());
                perso.setMaxHeight(root.getHeight());
                perso.setMinHeight(root.getHeight());
                grilleAttack.setMaxHeight(root.getHeight());
                grilleAttack.setMinHeight(root.getHeight());
                grilleMvt.setMaxHeight(root.getHeight());
                grilleMvt.setMinHeight(root.getHeight());
                Event.buttonStay(stay, move, attack, endTurn, grilleMvt, grilleAttack, afficheMap, affichePerso, perso, console,
                        group, map, root, choiceMap, start, txt, information, scrollPane, panel, choiceMap.getValue(), carac, combat, glancing);
                Event.clickOnMap(perso, affichePerso, grilleMvt, grilleAttack, information,
                        move, attack, stay, afficheMap, console, endTurn, carac, combat, glancing);
                Event.buttonCarac(carac, console);
                root.getChildren().addAll(map, grilleMvt, grilleAttack, group, perso);
                window.getChildren().add(panel);
                afficheMap.effectField(AffichePerso.listPersonnage);
                afficheMap.effectField(AffichePerso.listEnnemi);
            }
        });

        root.add(txt, 0, 0);
        root.add(choiceMap, 0, 1);
        root.add(start, 1, 1);
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
