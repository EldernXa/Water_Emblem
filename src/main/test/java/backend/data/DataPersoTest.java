package backend.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataPersoTest {

    @Test
    void createUnit() {
    }

    @Test
    void createCharacterUnit() {
    }

    @Test
    void getUnite() {


    }

    @Test
    void getCharacter() {
    }

    @Test
    void getCharacterLine() {

        String mercenaire = "Lyn;Epeiste;21;8;1;11;10;7;4;2;6;Epee";
        Assertions.assertEquals(mercenaire,DataPerso.getUniteLine("Lyn"));
    }

    @Test
    void getUniteLine() {
        String mercenaire = "Mercenaire;18;4;1;8;8;5;4;2;5;Epee";
        Assertions.assertEquals(mercenaire,DataPerso.getUniteLine("Mercenaire"));
    }

    @Test
    void getStatsCharacter() {
        Assertions.assertEquals(DataPerso.getStatsCharacter("Lyn","hp"),"21");
    }

    @Test
    void getStatsUnite() {
        Assertions.assertEquals(DataPerso.getStatsUnite("Mercenaire","hp"),"18");
    }

    @Test
    void setCharacterStats() {
    }

    @Test
    void getFightingType() {
        Assertions.assertEquals(DataPerso.getFightingType("Lyn"),0);
    }
}