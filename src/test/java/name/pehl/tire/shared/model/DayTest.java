package name.pehl.tire.shared.model;

import name.pehl.tire.TestData;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DayTest
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
        Day birthday = new Day(1973, 9, 2);
        Day foo = new Day(1, 2, 3);
        Day bar = new Day(1, 2, 3);
        bar.add(new Activity());
        new EqualsTester().addEqualityGroup(new Day(), new Day()).addEqualityGroup(new Day(1, 1, 1), new Day(1, 1, 1))
                .addEqualityGroup(new Day(1, 1, 2), new Day(1, 1, 2))
                .addEqualityGroup(new Day(1, 2, 1), new Day(1, 2, 1))
                .addEqualityGroup(new Day(2, 1, 1), new Day(2, 1, 1)).addEqualityGroup(birthday, birthday)
                .addEqualityGroup(foo, bar).testEquals();
    }


    @Test
    public void weight()
    {
        Day silvester2000 = new Day(2000, 12, 31);
        Day newYear2001 = new Day(2001, 1, 1);
        assertTrue(silvester2000.weight() < newYear2001.weight());
        assertTrue(newYear2001.weight() > silvester2000.weight());
    }


    @Test
    public void compare()
    {
        Day silvester2000 = new Day(2000, 12, 31);
        Day newYear2001 = new Day(2001, 1, 1);
        assertEquals(0, silvester2000.compareTo(silvester2000));
        assertEquals(0, newYear2001.compareTo(newYear2001));
        assertEquals(1, newYear2001.compareTo(silvester2000));
        assertEquals(-1, silvester2000.compareTo(newYear2001));
    }


    @Test
    public void iteratorIsEmpty()
    {
        Day cut = new Day();
        assertTrue(cut.isEmpty());
        assertFalse(cut.iterator().hasNext());

        Activity activity = td.newActivity();
        cut.add(activity);
        assertFalse(cut.isEmpty());
        assertTrue(cut.contains(activity));
        assertEquals(activity, cut.iterator().next());
    }


    @Test
    public void contains()
    {
        Day cut = new Day();
        Activity activity = td.newActivity();

        assertFalse(cut.contains(null));
        assertFalse(cut.contains(activity));
        cut.add(activity);
        assertTrue(cut.contains(activity));
    }


    @Test
    public void remove()
    {
        Day cut = new Day();
        Activity activity = td.newActivity();

        cut.remove(null);
        cut.remove(activity);

        cut.add(activity);
        assertTrue(cut.contains(activity));
        cut.remove(activity);
        assertFalse(cut.contains(activity));
    }


    @Test
    public void time()
    {
        DateTime startDate = new DateTime().dayOfMonth().withMinimumValue();
        DateTime endDate = new DateTime().dayOfMonth().withMaximumValue();
        Activity start = td.newActivity(startDate, startDate.plusHours(1));
        Activity end = td.newActivity(endDate, endDate.plusHours(1));

        Day cut = new Day();
        assertNull(cut.getStart());
        assertNull(cut.getEnd());
        cut.add(start);
        assertEquals(start.getStart(), cut.getStart());
        assertEquals(start.getEnd(), cut.getEnd());
        cut.add(end);
        assertEquals(start.getStart(), cut.getStart());
        assertEquals(end.getEnd(), cut.getEnd());
        assertEquals(120, cut.getMinutes());
    }
}
