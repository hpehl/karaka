package name.pehl.karaka.shared.model;

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
        assertEquals(Duration.ZERO, cut.getDuration());
        assertEquals(Duration.ZERO, cut.getPause());
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

        Activity running = td.newActivity();
        running.start();
        cut = running.copy();
        assertBlank(cut);
        assertTrue(running.isRunning()); // origin must not be changed
    }


    @Test
    public void plus()
    {
        DateTime now = new DateTime();
        DateTime m1 = now.plusMinutes(1);
        Time plusOneMinute = td.newTime(m1);
        long oneMinuteInMillis = org.joda.time.Duration.standardMinutes(1).getMillis();

        Activity blank = td.newActivity(now, null);
        Activity cut = blank.plus(oneMinuteInMillis);
        assertEquals(plusOneMinute, cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(Duration.ZERO, cut.getPause());
        assertTrue(cut.isStopped());
        assertFalse(cut.isRunning());
        assertNull(cut.getProject());
        assertTrue(cut.getTags().isEmpty());
        assertBlank(blank); // origin must not be changed
    }


    @Test
    public void isToday()
    {
        Activity blank = td.newActivity();
        assertTrue(blank.isToday());
        assertFalse(blank.plus(org.joda.time.Duration.standardDays(1).getMillis()).isToday());
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
        assertEquals(Duration.ZERO, cut.getPause());
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
        assertEquals(Duration.ZERO, cut.getPause());
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
        assertEquals(Duration.ZERO, cut.getPause());

        cut = td.newActivity();
        Time plusOneMinute = td.newTime(new DateTime().plusMinutes(1));
        cut.setStart(plusOneMinute);
        cut.setEnd(plusOneMinute);
        cut.resume();
        assertFalse(cut.isStopped());
        assertTrue(cut.isRunning());
        assertNotNull(cut.getStart());
        assertNotNull(cut.getEnd());
        assertEquals(Duration.ZERO, cut.getPause());

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
        assertEquals(Duration.ZERO, cut.getPause());

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
        assertEquals(Duration.ZERO, activity.getDuration());
        assertEquals(Duration.ZERO, activity.getPause());
        assertTrue(activity.isStopped());
        assertFalse(activity.isRunning());
        assertFalse(activity.isBillable());
        assertNull(activity.getProject());
        assertTrue(activity.getTags().isEmpty());
    }
}
