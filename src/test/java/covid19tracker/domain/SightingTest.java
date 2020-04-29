package covid19tracker.domain;
import org.junit.*;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SightingTest {

    @Test
   public void ähnliche_Örtlichkeit_andere_Zeit_returnFalse(){

        //given:
        Sighting sighting1 = new Sighting(25.44, 24.55, new Date(1588151005L) );
        Sighting sighting2 = new Sighting(25.44, 24.55, new Date(1587686400L) );

        //when:
        boolean isClose = sighting1.closeTo(sighting2);

        //then;

        assertEquals(false, isClose);

    }

    @Test
    public void andere_Örtlichkeit_andere_Zeit_returnFalse(){

        //given:
        Sighting sighting1 = new Sighting(25.44, 24.55, new Date(1588151005L) );
        Sighting sighting2 = new Sighting(232.443, 232.535, new Date(1587686400L) );

        //when:
        boolean isClose = sighting1.closeTo(sighting2);

        //then;
        assertEquals( false, isClose);

    }

    @Test
    public void andere_Örtlichkeit_ähnliche_Zeit_returnFalse(){

        //given:
        Sighting sighting1 = new Sighting(25.44, 24.55, new Date(1588151005L) );
        Sighting sighting2 = new Sighting(232.443, 232.535, new Date(1588151005L) );

        //when:
        boolean isClose = sighting1.closeTo(sighting2);

        //then;

        assertEquals( false, isClose);

    }

    @Test
    public void ähnliche_Örtlichkeit_ähnliche_Zeit_returnTrue(){

        //given:
        Sighting sighting1 = new Sighting(25.44, 24.55, new Date(1588151005L) );
        Sighting sighting2 = new Sighting(25.44, 24.55, new Date(1588151005L) );

        //when:
        boolean isClose = sighting1.closeTo(sighting2);

        //then;

        assertEquals(true, isClose);

    }
}