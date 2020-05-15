package backend.data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllLines;

public class DataPerso {

    private String file;


    public void createUnit() {
        File BDPerso = new File("src/main/resources/dataPerso/Unites.txt");
        try {


            //nomUnite /HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3
            System.out.println("nom de l'Unité: ");
            Scanner scanner = new Scanner(System.in);
            String nomUnite = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), nomUnite.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("Hp l'Unité: ");
            String hp = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), hp.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("STR de l'Unité: ");
            String str = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), str.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("MAG de l'Unité: ");
            String mag = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), mag.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("SKL de l'Unité: ");
            String skl = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), skl.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("SPD de l'Unité: ");
            String spd = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), spd.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("LCK de l'Unité: ");
            String lck = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), lck.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("DEF de l'Unité: ");
            String def = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), def.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("res de l'Unité: ");
            String res = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), res.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("mov de l'Unité: ");
            String mov = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), mov.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);

            System.out.println("arme1 de l'Unité: ");
            String arme1 = scanner.nextLine();
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), arme1.getBytes(), StandardOpenOption.APPEND);


            System.out.println("l'Unité a-elle d'autres armes ? oui : 1 | non : 0 ");
            String test = scanner.nextLine();
            while (test.equals("0") == false) {


                if (test.equals("1")) {
                    Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), ";".getBytes(), StandardOpenOption.APPEND);
                    System.out.println("arme de l'Unité: ");
                    String arme = scanner.nextLine();
                    Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), arme.getBytes(), StandardOpenOption.APPEND);

                    System.out.println("l'Unité a-elle d'autres armes ? oui : 1 | non : 0 ");
                    test = scanner.nextLine();

                } else if (test.equals("0") == false) {
                    System.out.println("rentrez une réponse correcte");
                    System.out.println("l'Unité a-elle d'autres armes ? oui : 1 | non : 0 ");
                    test = scanner.nextLine();
                }

            }
            Files.write(Paths.get("src/main/resources/dataPerso/Unites.txt"), "\n".getBytes(), StandardOpenOption.APPEND);


        } catch (IOException e) {

        }


    }

    public void createCharacterUnit(String name, String Unite) {

        try {
            File test = new File("src/main/resources/dataPerso/Character.txt");
            Files.write(Paths.get("src/main/resources/dataPerso/Character.txt"), name.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Character.txt"), ";".getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Character.txt"), getUniteLine(Unite).getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get("src/main/resources/dataPerso/Character.txt"), "\n".getBytes(), StandardOpenOption.APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public ArrayList<String> getUnite(String nomUnite) {
        return getArrayListInfo("src/main/resources/dataPerso/Unites.txt", nomUnite);

    }

    static public ArrayList<String> getCharacter(String nom) {
        return getArrayListInfo("src/main/resources/dataPerso/Character.txt", nom);

    }

    static private ArrayList<String> getArrayListInfo(String file, String nomUnite) {
        ArrayList<String> unite = new ArrayList<String>();

        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach(x -> unite.add(x));
            int flag = 0;
            for (int i = 0; i < unite.size(); i++) {

                String[] parts = unite.get(i).split(";");
                ArrayList<String> statsunite = new ArrayList<String>();

                if (parts[0].equals(nomUnite)) {
                    flag = 1;
                    for (String subString : parts) {

                        statsunite.add(subString);

                    }

                    return statsunite;
                }


            }
            if (flag == 0) {
                //System.out.println("Ce que vous recherchez n'existe pas");
                return null;
            }

        } catch (IOException e) {

        }
        return unite;
    }


    static public String getCharacterLine(String nom) {
        ArrayList<String> character = getCharacter(nom);
        return  getLine(character);
    }
    static public String getUniteLine(String nomUnite) {
        ArrayList<String> unite = getUnite(nomUnite);
        return getLine(unite);

    }
    static private String getLine(ArrayList unite) {

        String name = "";
        for (int i = 0; i < unite.size(); i++) {
            name = name + unite.get(i) + ";";
        }
         name = name.substring(0,name.length()-1);

        return name;
    }


    static public String getStatsCharacter(String name, String stat) {
        ArrayList<String> character = getCharacter(name);
        character.remove(0);
        return getStats(character, stat);
    }

    static public String getStatsUnite(String uniteChoisis, String stat) {
        ArrayList<String> unite = getUnite(uniteChoisis);

        return getStats(unite, stat);

    }

    static private String getStats(ArrayList<String> unite, String stat) {

        switch (stat) {
            case "unite":
                return unite.get(0);

            case "hp":
                return unite.get(1);

            case "str":
                return unite.get(2);

            case "mag":
                return unite.get(3);

            case "skl":
                return unite.get(4);

            case "spd":
                return unite.get(5);

            case "lck":
                //HP / STR / MAG / Skl / Spd /Lck / Def / Res / Mov / Arme1 / Arme2 / Arme3
                return unite.get(6);

            case "def":
                return unite.get(7);

            case "res":
                return unite.get(8);

            case "mov":
                return unite.get(9);

            case "armes":

                if (!(11 >= unite.size())) {
                    if (!(12 >= unite.size())) {
                        return unite.get(10) + ";" + unite.get(11) + ";" + unite.get(12);
                    }
                    return unite.get(10) + ";" + unite.get(11);
                }
                return unite.get(10);

        }
        return null;
    }


    static public void setCharacterStats(String name, String stat, int value) {

        ArrayList<String> character = getCharacter(name);
        String characterLine = getCharacterLine(name);

        switch (stat) {
            case "hp":
                character.set(2, String.valueOf(value));
                break;

            case "str":
                character.set(3, String.valueOf(value));
                break;

            case "mag":
                character.set(4, String.valueOf(value));
                break;

            case "skl":
                character.set(5, String.valueOf(value));
                break;

            case "spd":
                character.set(6, String.valueOf(value));
                break;

            case "lck":
                character.set(7, String.valueOf(value));
                break;

            case "def":

                character.set(8, String.valueOf(value));
                break;

            case "res":
                character.set(9, String.valueOf(value));
                break;

            case "mov":
                character.set(10, String.valueOf(value));
                break;

        }
        String newCharacterLine = getLine(character);



        try {
            List<String> fileContent = new ArrayList<String>();
            fileContent = readAllLines(Paths.get("src/main/resources/dataPerso/Character.txt"));


            for (int i = 0; i < fileContent.size(); i++) {
               // System.out.println(fileContent.get(i).equals(characterLine));

                if (fileContent.get(i).equals(characterLine)) {
                    fileContent.set(i, newCharacterLine);
                    break;
                }
            }

            Files.write(Paths.get("src/main/resources/dataPerso/Character.txt"), fileContent, StandardCharsets.UTF_8);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
