package name.pehl.tire.shared.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * TODO Replace current date/times with fixed ones?
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivityTest
{
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
        Time now = now();
        Time plusOneSecond = minutes(now, 1);
        Time plusTwoSecond = minutes(now, 2);
        long oneMinuteInMillis = Duration.standardMinutes(1).getMillis();

        Activity blank = blankActivity(now);
        Activity cut = blank.plus(oneMinuteInMillis);
        assertTrue(cut.isTransient());
        assertEquals(plusOneSecond, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertBlank(blank); // origin must not be changed

        Activity running = runningActivity(now);
        cut = running.plus(oneMinuteInMillis);
        assertTrue(cut.isTransient());
        assertEquals(plusOneSecond, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertTrue(running.isRunning()); // origin must not be changed

        Activity oneMinute = oneMinuteActivity(now);
        cut = oneMinute.plus(oneMinuteInMillis);
        assertTrue(cut.isTransient());
        assertEquals(plusOneSecond, cut.getStart());
        assertEquals(plusTwoSecond, cut.getEnd());
        assertEquals(0, cut.getPause());
        assertEquals(1, cut.getMinutes());
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
        assertFalse(blank.plus(Duration.standardDays(1).getMillis()).isToday());
    }


    @Test
    public void start()
    {
        Activity cut = blankActivity();
        cut.start();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
    }


    @Test
    public void stop()
    {
        Activity cut = blankActivity();
        cut.stop();
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
    }


    @Test
    public void resume()
    {
        Activity cut = blankActivity();
        cut.resume();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());

        cut = blankActivity();
        Time plusOneMinute = minutes(now(), 1);
        cut.setStart(plusOneMinute);
        cut.setEnd(plusOneMinute);
        cut.resume();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());

        // TODO Test resume with pause
    }


    @Test
    public void tick()
    {
        Activity cut = blankActivity();
        cut.start();
        cut.tick();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());

        cut = blankActivity();
        cut.tick();
        assertBlank(cut);
    }


    @Test(expected = IllegalArgumentException.class)
    public void startMustNotBeNull()
    {
        blankActivity().setStart(null);
    }


    // --------------------------------------------------------- helper methods

    void assertBlank(Activity activity)
    {
        assertTrue(activity.isTransient());
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


    // -------------------------------------------------------- factory methods

    Activity blankActivity()
    {
        return blankActivity(now());
    }


    Activity blankActivity(Time start)
    {
        Activity blank = new Activity();
        blank.setStart(start);
        return blank;

    }


    Activity runningActivity()
    {
        return runningActivity(now());
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
        return oneMinuteActivity(now());
    }


    Activity oneMinuteActivity(Time start)
    {
        Activity oneMinute = new Activity();
        oneMinute.setStart(start);
        oneMinute.setEnd(minutes(start, 1));
        return oneMinute;
    }


    Time now()
    {
        DateTime now = new DateTime();
        return new Time(now.toDate(), now.year().get(), now.monthOfYear().get(), now.weekOfWeekyear().get(), now
                .dayOfMonth().get());
    }


    Time minutes(Time time, int minutes)
    {
        DateTime then = new DateTime(time.getDate().getTime()).plusMinutes(minutes);
        return new Time(then.toDate(), then.year().get(), then.monthOfYear().get(), then.weekOfWeekyear().get(), then
                .dayOfMonth().get());
    }


    Time days(Time time, int days)
    {
        DateTime then = new DateTime(time.getDate().getTime()).plusDays(days);
        return new Time(then.toDate(), then.year().get(), then.monthOfYear().get(), then.weekOfWeekyear().get(), then
                .dayOfMonth().get());
    }
}
