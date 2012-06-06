package name.pehl.tire.shared.model;

import name.pehl.tire.TestData;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActivityComparatorTest
{
    // ------------------------------------------------------------------ setup

    TestData td;
    ActivityComparator cut;


    @Before
    public void setUp()
    {
        td = new TestData();
        cut = new ActivityComparator();
    }


    // ------------------------------------------------------------------ tests

    @Test
    public void compare() throws InterruptedException
    {
        Activity a1 = td.newActivity();
        Thread.sleep(100);
        Activity a2 = td.newActivity();
        assertEquals(0, cut.compare(a1, a1));
        assertEquals(0, cut.compare(a2, a2));
        assertEquals(1, cut.compare(a1, a2));
        assertEquals(-1, cut.compare(a2, a1));
    }
}
