package name.pehl.tire.shared.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class DurationTest
{
    @Test
    public void equalsAndHashcode()
    {
        new EqualsTester().addEqualityGroup(new Duration(0), new Duration(0, 0))
                .addEqualityGroup(new Duration(1), new Duration(0, 1))
                .addEqualityGroup(new Duration(1, 0), new Duration(0, 60))
                .addEqualityGroup(new Duration(2, 3), new Duration(1, 63), new Duration(0, 123)).testEquals();
    }


    @Test
    public void tostring()
    {
        Duration cut = new Duration(-1);
        assertEquals("0", cut.toString());
        cut = new Duration(0);
        assertEquals("0", cut.toString());
        cut = new Duration(15);
        assertEquals("15", cut.toString());
        cut = new Duration(1, 2);
        assertEquals("62", cut.toString());
    }


    @Test
    public void negativeValues()
    {
        Duration cut = new Duration(-1);
        assertEquals(Duration.EMPTY, cut);

        cut = new Duration(-1, -2);
        assertEquals(Duration.EMPTY, cut);
    }


    @Test
    public void empty()
    {
        Duration cut = new Duration(0);
        assertEquals(Duration.EMPTY, cut);

        cut = new Duration(0, 0);
        assertEquals(Duration.EMPTY, cut);
    }


    @Test
    public void _222()
    {
        Duration cut = new Duration(222);
        assertDuration(cut);
        cut = new Duration(1, 162);
        assertDuration(cut);
        cut = new Duration(2, 102);
        assertDuration(cut);
        cut = new Duration(3, 42);
        assertDuration(cut);

        long now = System.currentTimeMillis();
        Date start = new Date(now);
        Date end = new Date(now + 13320000);
        cut = new Duration(start, end);
        assertDuration(cut);

        start = new Date(now);
        end = new Date(now + 13320123);
        cut = new Duration(start, end);
        assertDuration(cut);
    }


    void assertDuration(Duration cut)
    {
        assertNotNull(cut);
        assertFalse(cut.isEmpty());
        assertFalse(cut.equals(Duration.EMPTY));

        assertEquals(3, cut.getHours());
        assertEquals(42, cut.getMinutes());
        assertEquals(222, cut.getTotalMinutes());
    }
}
