package backend.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataPersoTest {

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

}