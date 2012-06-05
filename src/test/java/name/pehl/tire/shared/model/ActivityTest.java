package name.pehl.tire.shared.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
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
    // ------------------------------------------------------------------ setup

    TestData td;


    @Before
    public void setUp()
    {
        td = new TestData();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void newInstance()
    {
        Activity cut = td.newActivity();
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
        Activity blank = td.newActivity();
        Activity withCustomId = new Activity("0815", "foo");
        new EqualsTester().addEqualityGroup(blank, blank).addEqualityGroup(withCustomId, withCustomId).testEquals();
    }


    @Test
    public void copy()
    {
        Activity blank = td.newActivity();
        Activity cut = blank.copy();
        assertBlank(cut);
        assertBlank(blank); // origin must not be changed

        Activity running = td.runningActivity();
        cut = running.copy();
        assertBlank(cut);
        assertTrue(running.isRunning()); // origin must not be changed

        Activity oneMinute = td.oneMinuteActivity();
        cut = oneMinute.copy();
        assertBlank(cut);
        assertEquals(1, oneMinute.getMinutes()); // origin must not be changed
    }


    @Test
    public void plus()
    {
        DateTime now = new DateTime();
        DateTime m1 = now.plusMinutes(1);
        DateTime m2 = now.plusMinutes(2);
        Time plusOneMinute = td.newTime(m1);
        Time plusTwoMinutes = td.newTime(m2);
        long oneMinuteInMillis = Duration.standardMinutes(1).getMillis();

        Activity blank = td.newActivity();
        Activity cut = blank.plus(oneMinuteInMillis);
        assertTrue(cut.isTransient());
        assertEquals(plusOneMinute, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertBlank(blank); // origin must not be changed

        Activity running = td.runningActivity();
        cut = running.plus(oneMinuteInMillis);
        assertTrue(cut.isTransient());
        assertEquals(plusOneMinute, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertTrue(running.isRunning()); // origin must not be changed

        Activity oneMinute = td.oneMinuteActivity();
        cut = oneMinute.plus(oneMinuteInMillis);
        assertTrue(cut.isTransient());
        assertEquals(plusOneMinute, cut.getStart());
        assertEquals(plusTwoMinutes, cut.getEnd());
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
        Activity blank = td.newActivity();
        assertTrue(blank.isToday());
        assertFalse(blank.plus(Duration.standardDays(1).getMillis()).isToday());
    }


    @Test
    public void start()
    {
        Activity cut = td.newActivity();
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
        Activity cut = td.newActivity();
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
        Activity cut = td.newActivity();
        cut.resume();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());

        cut = td.newActivity();
        Time plusOneMinute = td.newTime(new DateTime().plusMinutes(1));
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
        Activity cut = td.newActivity();
        cut.start();
        cut.tick();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(0, cut.getPause());

        cut = td.newActivity();
        cut.tick();
        assertBlank(cut);
    }


    @Test(expected = IllegalArgumentException.class)
    public void startMustNotBeNull()
    {
        td.newActivity().setStart(null);
    }


    // --------------------------------------------------------- helper methods

    void assertBlank(Activity activity)
    {
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
}
