package name.pehl.tire.shared.model;

import java.util.UUID;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.junit.Test;

import static name.pehl.tire.shared.model.TimeUnit.DAY;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TODO Replace current date/times with fixed ones? TODO Move common code to
 * 'TestBlock'
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivitiesTest
{
    // ------------------------------------------------------------------ tests

    @Test
    public void add()
    {
        Activity activity = newActivity();
        Activity anotherActivity = newActivity();

        // null
        Activities cut = newActivities(WEEK);
        cut.add(null);
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));

        // month, week and day
        internatAdd(month(), activity, anotherActivity);
        internatAdd(week(), activity, anotherActivity);
        internatAdd(day(), activity, anotherActivity);
    }


    private void internatAdd(Activities cut, Activity activity, Activity anotherActivity)
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
        Activity activity = newActivity();
        Activity anotherActivity = newActivity();

        // null
        Activities cut = newActivities(WEEK);
        cut.remove(null);
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(anotherActivity));

        // month, week and day
        internalRemove(month(), activity, anotherActivity);
        internalRemove(week(), activity, anotherActivity);
        internalRemove(day(), activity, anotherActivity);
    }


    private void internalRemove(Activities cut, Activity activity, Activity anotherActivity)
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
        Activity activity = newActivity();
        Activity anotherActivity = newActivity();

        // null
        Activities cut = newActivities(WEEK);
        assertFalse(cut.contains(null));

        // month, week and day
        internalContains(month(), activity, anotherActivity);
        internalContains(week(), activity, anotherActivity);
        internalContains(day(), activity, anotherActivity);
    }


    private void internalContains(Activities cut, Activity activity, Activity anotherActivity)
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
    public void update()
    {
        DateTime now = new DateTime();
        Activity activity = newActivity(now, now.plusHours(1));
        activity.setName("A");

        Activity sameIdOtherData = new Activity(activity.getId(), activity.getName());
        sameIdOtherData.setName("A'");
        DateTime later = now.plusHours(2);
        Time newEnd = new Time(later.toDate(), later.year().get(), later.monthOfYear().get(), later.weekOfWeekyear()
                .get(), later.dayOfMonth().get());
        sameIdOtherData.setEnd(newEnd);

        // null
        Activities cut = newActivities(WEEK);
        cut.update(null);
        assertEquals(0, cut.activities().size());
        assertFalse(cut.contains(activity));
        assertFalse(cut.contains(sameIdOtherData));

        // month, week and day
        internalUpdate(month(), activity, sameIdOtherData, newEnd);
        internalUpdate(week(), activity, sameIdOtherData, newEnd);
        internalUpdate(day(), activity, sameIdOtherData, newEnd);
    }


    private void internalUpdate(Activities cut, Activity activity, Activity sameIdOtherData, Time newEnd)
    {
        cut.add(activity);
        assertEquals(1, cut.activities().size());
        assertTrue(cut.contains(activity));
        assertEquals("A", cut.activities().first().getName());
        cut.update(sameIdOtherData);
        assertEquals(1, cut.activities().size());
        assertTrue(cut.contains(sameIdOtherData));
        assertEquals("A'", cut.activities().first().getName());
        assertEquals(newEnd, cut.activities().first().getEnd());
    }


    @Test
    public void activities()
    {

    }


    @Test
    public void matchingRange()
    {

    }


    @Test
    public void getRunningActivity()
    {

    }


    @Test
    public void getStart()
    {

    }


    @Test
    public void getEnd()
    {

    }


    @Test
    public void getMinutes()
    {
        // empty
        Activities cut = newActivities(WEEK);
        assertEquals(0, cut.getMinutes());

        // month, week and day
        assertEquals(10 * 60, month(10).getMinutes());
        assertEquals(10 * 60, week(10).getMinutes());
        assertEquals(10 * 60, day(10).getMinutes());
    }


    @Test
    public void getNumberOfDays()
    {
        // empty
        Activities cut = newActivities(WEEK);
        assertEquals(0, cut.getNumberOfDays());

        // month, week and day
        assertEquals(10, month(10).getNumberOfDays());
        assertEquals(10, week(10).getNumberOfDays());
        assertEquals(1, day(10).getNumberOfDays());
    }


    // -------------------------------------------------------- factory methods

    Activities month()
    {
        return newActivities(MONTH);
    }


    Activities month(int numberOfActivities)
    {
        Activities activities = newActivities(MONTH);
        DateTime start = new MutableDateTime().dayOfMonth().set(1).hourOfDay().set(8).toDateTime();
        for (int i = 0; i < numberOfActivities; i++)
        {
            DateTime end = start.plusHours(1);
            activities.add(newActivity(start, end));
            start = start.plusDays(1);
        }
        return activities;
    }


    Activities week()
    {
        return newActivities(WEEK);
    }


    Activities week(int numberOfActivities)
    {
        Activities activities = newActivities(WEEK);
        DateTime start = new MutableDateTime().dayOfWeek().set(1).hourOfDay().set(8).toDateTime();
        for (int i = 0; i < numberOfActivities; i++)
        {
            DateTime end = start.plusHours(1);
            activities.add(newActivity(start, end));
            start = start.plusDays(1);
        }
        return activities;
    }


    Activities day()
    {
        return newActivities(DAY);
    }


    Activities day(int numberOfActivities)
    {
        Activities activities = newActivities(DAY);
        DateTime start = new MutableDateTime().dayOfMonth().set(1).hourOfDay().set(8).toDateTime();
        for (int i = 0; i < numberOfActivities; i++)
        {
            DateTime end = start.plusHours(1);
            activities.add(newActivity(start, end));
            start = end.plusHours(1);
        }
        return activities;
    }


    Activities newActivities(TimeUnit unit)
    {
        DateMidnight now = new DateMidnight();
        return new Activities(now.getYear(), now.getMonthOfYear(), now.getWeekOfWeekyear(), now.getDayOfMonth(), unit);
    }


    Activity newActivity()
    {
        DateTime now = new DateTime();
        return newActivity(now, now.plusMillis(100));
    }


    Activity newActivity(DateTime start, DateTime end)
    {
        Activity activity = new Activity(UUID.randomUUID().toString(), "Test activity");
        if (start != null)
        {
            activity.setStart(new Time(start.toDate(), start.year().get(), start.monthOfYear().get(), start
                    .weekOfWeekyear().get(), start.dayOfMonth().get()));
        }
        if (end != null)
        {
            activity.setEnd(new Time(end.toDate(), end.year().get(), end.monthOfYear().get(), end.weekOfWeekyear()
                    .get(), end.dayOfMonth().get()));
        }
        return activity;
    }
}
