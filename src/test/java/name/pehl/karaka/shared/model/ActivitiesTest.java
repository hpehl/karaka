package name.pehl.karaka.shared.model;

import static name.pehl.karaka.shared.model.TimeUnit.DAY;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import name.pehl.karaka.TestData;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.google.common.testing.EqualsTester;

/**
 * TODO Replace current date/times with fixed ones? TODO Move common code to
 * 'TestBlock'
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivitiesTest
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
        new EqualsTester().addEqualityGroup(td.month(), td.month()).addEqualityGroup(td.week(), td.week())
                .addEqualityGroup(td.day(), td.day())
                .addEqualityGroup(new Activities(1, 1, 1, 1, DAY), new Activities(1, 1, 1, 1, DAY))
                .addEqualityGroup(new Activities(2, 1, 1, 1, DAY), new Activities(2, 1, 1, 1, DAY))
                .addEqualityGroup(new Activities(1, 2, 1, 1, DAY), new Activities(1, 2, 1, 1, DAY))
                .addEqualityGroup(new Activities(1, 1, 2, 1, DAY), new Activities(1, 1, 2, 1, DAY))
                .addEqualityGroup(new Activities(1, 1, 1, 2, DAY), new Activities(1, 1, 1, 2, DAY)).testEquals();
    }


    @Test
    public void add()
    {
        Activity activity = td.newActivity();
        Activity anotherActivity = td.newActivity();

        // null
        Activities cut = td.newActivities(WEEK);
        cut.add(null);
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));

        // month, week and day
        internatAdd(td.month(), activity, anotherActivity);
        internatAdd(td.week(), activity, anotherActivity);
        internatAdd(td.day(), activity, anotherActivity);
    }


    void internatAdd(Activities cut, Activity activity, Activity anotherActivity)
    {
        cut.add(activity);
        assertEquals(1, cut.activities().size());
        assertTrue(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));

        cut.add(activity);
        assertEquals(1, cut.activities().size());
        assertTrue(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));

        cut.add(anotherActivity);
        assertEquals(2, cut.activities().size());
        assertTrue(cut.contains(activity));
        assertTrue(cut.contains(anotherActivity));
    }


    @Test
    public void remove()
    {
        Activity activity = td.newActivity();
        Activity anotherActivity = td.newActivity();

        // null
        Activities cut = td.newActivities(WEEK);
        cut.remove(null);
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));

        // month, week and day
        internalRemove(td.month(), activity, anotherActivity);
        internalRemove(td.week(), activity, anotherActivity);
        internalRemove(td.day(), activity, anotherActivity);
    }


    void internalRemove(Activities cut, Activity activity, Activity anotherActivity)
    {
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));
        cut.add(activity);
        cut.add(anotherActivity);
        assertEquals(2, cut.activities().size());
        assertTrue(cut.contains(activity));
        assertTrue(cut.contains(anotherActivity));
        cut.remove(activity);
        assertEquals(1, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertTrue(cut.contains(anotherActivity));
        cut.remove(anotherActivity);
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));
    }


    @Test
    public void contains()
    {
        Activity activity = td.newActivity();
        Activity anotherActivity = td.newActivity();

        // null
        Activities cut = td.newActivities(WEEK);
        assertFalse(cut.contains(null));

        // month, week and day
        internalContains(td.month(), activity, anotherActivity);
        internalContains(td.week(), activity, anotherActivity);
        internalContains(td.day(), activity, anotherActivity);
    }


    void internalContains(Activities cut, Activity activity, Activity anotherActivity)
    {
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));
        cut.add(activity);
        assertTrue(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));
        cut.add(anotherActivity);
        assertTrue(cut.contains(activity));
        assertTrue(cut.contains(anotherActivity));
    }


    @Test
    public void weekOf()
    {
        Activity activity = td.newActivity();

        // null
        Activities cut = td.newActivities(WEEK);
        assertNull(cut.weekOf(null));

        // month
        cut = td.month();
        assertNull(cut.weekOf(activity));
        cut.add(activity);
        assertNotNull(cut.weekOf(activity));

        // week
        cut = td.week();
        assertNull(cut.weekOf(activity));
        cut.add(activity);
        assertNull(cut.weekOf(activity));

        // day
        cut = td.day();
        assertNull(cut.weekOf(activity));
        cut.add(activity);
        assertNull(cut.weekOf(activity));
    }


    @Test
    public void dayOf()
    {
        Activity activity = td.newActivity();

        // null
        Activities cut = td.newActivities(WEEK);
        assertNull(cut.dayOf(null));

        // month
        cut = td.month();
        assertNull(cut.dayOf(activity));
        cut.add(activity);
        assertNull(cut.dayOf(activity));

        // week
        cut = td.week();
        assertNull(cut.dayOf(activity));
        cut.add(activity);
        assertNotNull(cut.dayOf(activity));

        // day
        cut = td.day();
        assertNull(cut.dayOf(activity));
        cut.add(activity);
        assertNull(cut.dayOf(activity));
    }


    @Test
    public void activities()
    {
        // empty
        Activities cut = td.newActivities(WEEK);
        assertEquals(0, cut.activities().size());

        // month, week and day
        assertEquals(10, td.month(10).activities().size());
        assertEquals(10, td.week(10).activities().size());
        assertEquals(10, td.day(10).activities().size());

    }


    @Test
    public void matchingRange()
    {
        Activity activity = td.newActivity();
        DateTime date = new DateTime().minusYears(1);
        Activity lastYear = td.newActivity(date, date.plusHours(1));

        // empty
        Activities cut = td.newActivities(WEEK);
        assertTrue(cut.matchingRange(activity));
        assertFalse(cut.matchingRange(lastYear));

        // month, week, day
        internalMatchingRange(td.month(), activity, lastYear);
        internalMatchingRange(td.week(), activity, lastYear);
        internalMatchingRange(td.day(), activity, lastYear);
    }


    void internalMatchingRange(Activities cut, Activity activity, Activity lastYear)
    {
        assertTrue(cut.matchingRange(activity));
        assertFalse(cut.matchingRange(lastYear));
    }


    @Test
    public void getRunningActivity()
    {
        // empty
        Activities cut = td.newActivities(WEEK);
        assertNull(cut.getRunningActivity());

        // month, week, day
        internalGetRunningActivity(td.month());
        internalGetRunningActivity(td.week());
        internalGetRunningActivity(td.day());
    }


    void internalGetRunningActivity(Activities cut)
    {
        Activity activity = td.newActivity();
        assertNull(cut.getRunningActivity());
        cut.add(activity);
        assertNull(cut.getRunningActivity());
        activity.start();
        assertEquals(activity, cut.getRunningActivity());
        activity.stop();
        assertNull(cut.getRunningActivity());
    }


    @Test
    public void getStartEnd()
    {

        // empty
        Activities cut = td.newActivities(WEEK);
        assertNull(cut.getStart());
        assertNull(cut.getEnd());

        // month
        DateTime startDate = new DateTime().dayOfMonth().withMinimumValue();
        DateTime endDate = new DateTime().dayOfMonth().withMaximumValue();
        Activity start = td.newActivity(startDate, startDate.plusHours(1));
        Activity end = td.newActivity(endDate, endDate.plusHours(1));
        internalGetStartEnd(td.month(), start, end);

        // week
        startDate = new DateTime().dayOfWeek().withMinimumValue();
        endDate = new DateTime().dayOfWeek().withMaximumValue();
        start = td.newActivity(startDate, startDate.plusHours(1));
        end = td.newActivity(endDate, endDate.plusHours(1));
        internalGetStartEnd(td.week(), start, end);

        // day
        startDate = new DateTime().hourOfDay().withMinimumValue();
        endDate = new DateTime().hourOfDay().withMaximumValue().minusHours(1);
        start = td.newActivity(startDate, startDate.plusHours(1));
        end = td.newActivity(endDate, endDate.plusHours(1));
        internalGetStartEnd(td.day(), start, end);
    }


    void internalGetStartEnd(Activities cut, Activity start, Activity end)
    {
        assertNull(cut.getStart());
        assertNull(cut.getEnd());
        cut.add(start);
        assertEquals(start.getStart(), cut.getStart());
        assertEquals(start.getEnd(), cut.getEnd());
        cut.add(end);
        assertEquals(start.getStart(), cut.getStart());
        assertEquals(end.getEnd(), cut.getEnd());
    }


    @Test
    public void getMinutes()
    {
        // empty
        Activities cut = td.newActivities(WEEK);
        assertEquals(Duration.ZERO, cut.getDuration());

        // month, week and day
        assertEquals(new Duration(10 * 60), td.month(10).getDuration());
        assertEquals(new Duration(10 * 60), td.week(10).getDuration());
        assertEquals(new Duration(10 * 60), td.day(10).getDuration());
    }


    @Test
    public void getNumberOfDays()
    {
        // empty
        Activities cut = td.newActivities(WEEK);
        assertEquals(0, cut.getNumberOfDays());

        // month, week and day
        assertEquals(10, td.month(10).getNumberOfDays());
        assertEquals(10, td.week(10).getNumberOfDays());
        assertEquals(1, td.day(10).getNumberOfDays());
    }


    @Test
    public void toStringTest()
    {
        Activities cut = td.newActivities(WEEK);
        assertNotNull(cut.toString());
        assertNotNull(td.month().toString());
        assertNotNull(td.week().toString());
        assertNotNull(td.day().toString());
    }
}
