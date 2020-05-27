package backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatTest {

    @Test
    void damage() {
    }

    @Test
    void accuracy() {
    }

    @Test
    void weaponTriangle() {
        String epee ="Epee" ;
        String hache = "Hache";
        String lance = "Lance";
        String mage = "Magie";

        int x = Stat.weaponTriangle(epee, hache);
        int y = Stat.weaponTriangle(hache, lance);
        int z = Stat.weaponTriangle(lance, epee);
        int w = Stat.weaponTriangle(mage, epee);

        Assertions.assertEquals(x , 1 );
        Assertions.assertEquals(y, 1);
        Assertions.assertEquals( z, 1);
        Assertions.assertEquals(w, 0);
    }

    @Test
    void calculerStats() {
    }

    @Test
    void damageAfterCalc() {
    }
}