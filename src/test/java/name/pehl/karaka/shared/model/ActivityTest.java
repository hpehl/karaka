package name.pehl.karaka.shared.model;

import com.google.common.testing.EqualsTester;
import name.pehl.karaka.TestData;
import org.junit.Before;
import org.junit.Test;

import static name.pehl.karaka.shared.model.Status.RUNNING;
import static org.junit.Assert.*;

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
        running.setStatus(RUNNING);
        cut = running.copy();
        assertBlank(cut);
        assertTrue(running.isRunning()); // origin must not be changed
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
