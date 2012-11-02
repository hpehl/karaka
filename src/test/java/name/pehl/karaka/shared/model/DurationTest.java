package name.pehl.karaka.shared.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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
        assertEquals(Duration.ZERO, cut);

        cut = new Duration(-1, -2);
        assertEquals(Duration.ZERO, cut);
    }


    @Test
    public void zero()
    {
        Duration cut = new Duration(0);
        assertEquals(Duration.ZERO, cut);

        cut = new Duration(0, 0);
        assertEquals(Duration.ZERO, cut);

        cut = new Duration(null, null);
        assertEquals(Duration.ZERO, cut);

        cut = new Duration(new Date(), null);
        assertEquals(Duration.ZERO, cut);

        cut = new Duration(null, new Date());
        assertEquals(Duration.ZERO, cut);
    }


    @Test
    public void value222()
    {
        Duration cut = new Duration(222);
        assert222(cut);
        cut = new Duration(1, 162);
        assert222(cut);
        cut = new Duration(2, 102);
        assert222(cut);
        cut = new Duration(3, 42);
        assert222(cut);

        long now = System.currentTimeMillis();
        Date start = new Date(now);
        Date end = new Date(now + 13320000);
        cut = new Duration(start, end);
        assert222(cut);

        start = new Date(now);
        end = new Date(now + 13320123);
        cut = new Duration(start, end);
        assert222(cut);
    }


    void assert222(Duration cut)
    {
        assertNotNull(cut);
        assertFalse(cut.isZero());
        assertFalse(cut.equals(Duration.ZERO));

        assertEquals(3, cut.getHours());
        assertEquals(42, cut.getMinutes());
        assertEquals(222, cut.getTotalMinutes());
        assertEquals(3.7, cut.getTotalHours(), .01);
    }


    @Test
    public void plus()
    {
        Duration twentytwo = new Duration(22);
        Duration start = new Duration(22);

        Duration cut = start.plus(null);
        assertSame(start, cut);

        cut = cut.plus(twentytwo);
        assertEquals(0, cut.getHours());
        assertEquals(44, cut.getMinutes());
        assertEquals(44, cut.getTotalMinutes());
        assertEquals(0.73, cut.getTotalHours(), .01);

        cut = cut.plus(twentytwo);
        assertEquals(1, cut.getHours());
        assertEquals(6, cut.getMinutes());
        assertEquals(66, cut.getTotalMinutes());
        assertEquals(1.1, cut.getTotalHours(), .01);
    }


    @Test
    public void minus()
    {
        Duration twentyfour = new Duration(24);
        Duration start = new Duration(44);

        Duration cut = start.minus(null);
        assertSame(start, cut);

        cut = cut.minus(twentyfour);
        assertEquals(0, cut.getHours());
        assertEquals(20, cut.getMinutes());
        assertEquals(20, cut.getTotalMinutes());

        cut = cut.minus(twentyfour);
        assertTrue(cut.isZero());
        assertEquals(0, cut.getHours());
        assertEquals(0, cut.getMinutes());
        assertEquals(0, cut.getTotalMinutes());
        assertEquals(0, cut.getTotalHours(), .01);
    }
}
