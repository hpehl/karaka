package name.pehl.tire.shared.model;

import org.joda.time.DateTime;
import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DayTest
{
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
    public void isEmpty()
    {
        Day cut = new Day();
        assertTrue(cut.isEmpty());
        assertFalse(cut.iterator().hasNext());

        Activity activity = new Activity("42", "foo");
        cut.add(activity);
        assertFalse(cut.isEmpty());
        assertTrue(cut.contains(activity));
        assertEquals(activity, cut.iterator().next());
    }


    @Test
    public void iterator()
    {
        Day cut = new Day();
        assertTrue(cut.isEmpty());
        assertFalse(cut.iterator().hasNext());

        Activity activity = new Activity("42", "foo");
        cut.add(activity);
        assertFalse(cut.isEmpty());
        assertTrue(cut.contains(activity));
        assertEquals(activity, cut.iterator().next());
    }


    @Test
    public void contains()
    {
        Day cut = new Day();
        Activity activity = new Activity("42", "foo");

        assertFalse(cut.contains(null));
        assertFalse(cut.contains(activity));
        cut.add(activity);
        assertTrue(cut.contains(activity));
    }


    @Test
    public void remove()
    {
        Day cut = new Day();
        Activity activity = new Activity("42", "foo");

        cut.remove(null);
        cut.remove(activity);

        cut.add(activity);
        assertTrue(cut.contains(activity));
        cut.remove(activity);
        assertFalse(cut.contains(activity));
    }


    @Test
    public void activitiesWithoutTime()
    {
        Day cut = new Day();
        assertNull(cut.getStart());
        assertNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());

        Activity a1 = new Activity("42", "foo");
        Activity a2 = new Activity("43", "bar");
        cut.add(a1);
        cut.add(a2);
        assertNotNull(cut.getStart());
        assertNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());
    }


    @Test
    public void time()
    {
        Day cut = new Day();
        Activity a1 = new Activity("42", "foo");
        Activity a2 = new Activity("43", "bar");
        DateTime a1Start = new DateTime();
        Time a1StartTime = new Time(a1Start.toDate(), a1Start.year().get(), a1Start.monthOfYear().get(), a1Start
                .weekOfWeekyear().get(), a1Start.dayOfMonth().get());
        a1.setStart(a1StartTime);
        DateTime a1End = a1Start.plusMinutes(5);
        Time a1EndTime = new Time(a1End.toDate(), a1End.year().get(), a1End.monthOfYear().get(), a1End.weekOfWeekyear()
                .get(), a1End.dayOfMonth().get());
        a1.setEnd(a1EndTime);
        DateTime a2Start = a1End.plusMinutes(5);
        Time a2StartTime = new Time(a2Start.toDate(), a2Start.year().get(), a2Start.monthOfYear().get(), a2Start
                .weekOfWeekyear().get(), a2Start.dayOfMonth().get());
        a2.setStart(a2StartTime);
        DateTime a2End = a2Start.plusMinutes(5);
        Time a2EndTime = new Time(a2End.toDate(), a2End.year().get(), a2End.monthOfYear().get(), a2End.weekOfWeekyear()
                .get(), a2End.dayOfMonth().get());
        a2.setEnd(a2EndTime);
        cut.add(a1);
        cut.add(a2);

        assertEquals(a1StartTime, cut.getStart());
        assertEquals(a2EndTime, cut.getEnd());
        assertEquals(10, cut.getMinutes());
    }
}
