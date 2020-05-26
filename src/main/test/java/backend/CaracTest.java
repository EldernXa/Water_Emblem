package backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaracTest {
    @Test
    void testConstructeur() {
        Carac carac = new Carac("Marth");
        Assertions.assertEquals(carac.getName(),"Marth");
        Assertions.assertEquals(carac.getMaxHp(),17);
    }

    @Test
    void cloner() {
        Carac carac = new Carac("Marth");
        Carac clone = carac.cloner();

        Assertions.assertEquals(carac.getMov(), clone.getMov());
        Assertions.assertEquals(carac.getSpd(), clone.getSpd());
        Assertions.assertEquals(carac.getHp(), clone.getHp());

        clone.setHp(300);
        Assertions.assertEquals(clone.getHp(), 300);
        Assertions.assertNotEquals(carac.getHp(), clone.getHp());
    }


}