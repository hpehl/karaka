package name.pehl.tire.shared.model;

import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TimeTest
{
    // ------------------------------------------------------------------ setup

    TestData td;


    @Before
    public void setUp()
    {
        td = new TestData();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void equalsAndHashcode()
    {
        Date now = new Date();
        Date future = new Date(System.currentTimeMillis() + 100);
        Time birthday = td.newTime(new DateMidnight(1973, 9, 2).toDateTime());
        new EqualsTester().addEqualityGroup(new Time(null), new Time(null))
                .addEqualityGroup(new Time(now), new Time(now, 0, 8, 1, 5))
                .addEqualityGroup(new Time(future), new Time(future, 0, 8, 1, 5)).addEqualityGroup(birthday, birthday)
                .testEquals();
    }


    @Test
    public void compare()
    {
        DateTime now = new DateTime();
        Time cut = td.newTime(now);
        Time past = td.newTime(now.minusSeconds(1));
        Time future = td.newTime(now.plusSeconds(1));

        assertEquals(0, cut.compareTo(cut));
        assertTrue(cut.compareTo(future) < 0);
        assertTrue(cut.compareTo(past) > 0);
    }


    @Test
    public void beforeAfter()
    {
        DateTime now = new DateTime();
        Date past = now.minusSeconds(1).toDate();
        Date future = now.plusSeconds(1).toDate();

        // before
        Time cut = new Time(null);
        assertFalse(cut.before(null));
        assertFalse(cut.before(cut.getDate()));
        cut = td.newTime(now);
        assertFalse(cut.before(null));
        assertFalse(cut.before(cut.getDate()));
        assertTrue(cut.before(future));
        assertFalse(cut.before(past));

        // after
        cut = new Time(null);
        assertFalse(cut.after(null));
        assertFalse(cut.after(cut.getDate()));
        cut = td.newTime(now);
        assertFalse(cut.after(null));
        assertFalse(cut.after(cut.getDate()));
        assertTrue(cut.after(past));
        assertFalse(cut.after(future));
    }
}
