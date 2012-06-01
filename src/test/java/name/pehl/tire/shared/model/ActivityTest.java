package name.pehl.tire.shared.model;

import java.util.Date;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ActivityTest
{
    final static long ONE_MINUTES_IN_MILLIS = 60000;
    final static long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;


    // ------------------------------------------------------------------ tests

    @Test
    public void newInstance()
    {
        Activity cut = blankActivity();
        assertBlank(cut);

        cut = new Activity("foo");
        assertBlank(cut);

        cut = new Activity("0815", "foo");
        assertEquals("0815", cut.getId());
        assertNotNull(cut.getStart());
        assertNull(cut.getEnd());
        assertEquals(0, cut.getMinutes());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
    }


    @Test
    public void equalsAndHashcode()
    {
        Activity blank = blankActivity();
        Activity withId = new Activity("0815", "foo");
        new EqualsTester().addEqualityGroup(blank, blank).addEqualityGroup(withId, withId).testEquals();
    }


    @Test
    public void compare() throws InterruptedException
    {
        Activity a1 = blankActivity();
        Thread.sleep(100);
        Activity a2 = blankActivity();
        assertEquals(0, a1.compareTo(a1));
        assertEquals(0, a2.compareTo(a2));
        assertEquals(1, a1.compareTo(a2));
        assertEquals(-1, a2.compareTo(a1));
    }


    @Test
    public void copy()
    {
        Activity blank = blankActivity();
        Activity cut = blank.copy();
        assertBlank(cut);
        assertBlank(blank); // origin must not be changed

        Activity running = runningActivity();
        cut = running.copy();
        assertBlank(cut);
        assertTrue(running.isRunning()); // origin must not be changed

        Activity oneMinute = oneMinuteActivity();
        cut = oneMinute.copy();
        assertBlank(cut);
        assertEquals(1, oneMinute.getMinutes()); // origin must not be changed
    }


    @Test
    public void plus()
    {
        Time now = new Time();
        Time plusOneSecond = new Time(new Date(now.getDate().getTime() + ONE_MINUTES_IN_MILLIS));
        Time plusTwoSecond = new Time(new Date(now.getDate().getTime() + ONE_MINUTES_IN_MILLIS * 2));

        Activity blank = blankActivity(now);
        Activity cut = blank.plus(ONE_MINUTES_IN_MILLIS);
        assertNull(cut.getId());
        assertEquals(plusOneSecond, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertBlank(blank); // origin must not be changed

        Activity running = runningActivity(now);
        cut = running.plus(ONE_MINUTES_IN_MILLIS);
        assertNull(cut.getId());
        assertEquals(plusOneSecond, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertTrue(running.isRunning()); // origin must not be changed

        Activity oneMinute = oneMinuteActivity(now);
        cut = oneMinute.plus(ONE_MINUTES_IN_MILLIS);
        assertNull(cut.getId());
        assertEquals(plusOneSecond, cut.getStart());
        assertEquals(plusTwoSecond, cut.getEnd());
        assertEquals(1, cut.getMinutes());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertEquals(1, oneMinute.getMinutes()); // origin must not be changed
    }


    @Test
    public void isToday()
    {
        Activity blank = blankActivity();
        assertTrue(blank.isToday());
        assertFalse(blank.plus(ONE_DAY_IN_MILLIS).isToday());
    }


    @Test
    public void resume()
    {

    }


    @Test
    public void start()
    {

    }


    @Test
    public void stop()
    {

    }


    @Test
    public void tick()
    {

    }


    // --------------------------------------------------------- helper methods

    void assertBlank(Activity activity)
    {
        assertNull(activity.getId());
        assertNotNull(activity.getStart());
        assertNull(activity.getEnd());
        assertEquals(0, activity.getMinutes());
        assertEquals(0, activity.getPause());
        assertTrue(activity.isStopped());
        assertFalse(activity.isRunning());
        assertFalse(activity.isBillable());
        assertNull(activity.getProject());
        assertTrue(activity.getTags().isEmpty());
    }


    // -------------------------------------------------------------- factories

    Activity blankActivity()
    {
        return blankActivity(new Time());
    }


    Activity blankActivity(Time start)
    {
        Activity blank = new Activity();
        blank.setStart(start);
        return blank;

    }


    Activity runningActivity()
    {
        return runningActivity(new Time());
    }


    Activity runningActivity(Time start)
    {
        Activity running = new Activity();
        running.start();
        running.setStart(start);
        return running;
    }


    Activity oneMinuteActivity()
    {
        return oneMinuteActivity(new Time());
    }


    Activity oneMinuteActivity(Time start)
    {
        Activity oneMinute = new Activity();
        oneMinute.setStart(start);
        oneMinute.setEnd(new Time(new Date(start.getDate().getTime() + ONE_MINUTES_IN_MILLIS)));
        return oneMinute;
    }
}
