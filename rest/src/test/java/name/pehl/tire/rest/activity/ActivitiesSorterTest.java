package name.pehl.tire.rest.activity;

import static org.junit.Assert.*;

import java.util.List;

import name.pehl.tire.model.ActivitiesGenerator;
import name.pehl.tire.model.Activity;
import name.pehl.tire.model.TimeUnit;

import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ActivitiesSorterTest
{
    private ActivitiesSorter underTest;
    private DateMidnight requestedFixture;
    private DateMidnight nowFixture;


    @Before
    public void setUp() throws Exception
    {
        underTest = new ActivitiesSorter();
        requestedFixture = new DateMidnight();
        nowFixture = new DateMidnight();
    }


    @Test
    public void testSortByMonth()
    {
        List<Activity> data = new ActivitiesGenerator().generateMonth(1973, 9);
        Activities activities = underTest.sort(requestedFixture, nowFixture, TimeUnit.MONTH, data);
        assertNotNull(activities);
        assertNotNull(activities.weeks);
        assertEquals(4, activities.weeks.size());
        assertNull(activities.days);
        assertNull(activities.activities);

        int cw = 38;
        for (Week week : activities.weeks)
        {
            assertEquals(cw, week.week);
            for (Day day : week.days)
            {
                for (Activity activity : day.activities)
                {
                    assertEquals(cw, activity.getStart().getWeek());
                    assertEquals(day.day, activity.getStart().getDay());
                }
            }
            cw--;
        }
    }


    @Test
    public void testSortByWeek()
    {
        int cw = 35;
        List<Activity> data = new ActivitiesGenerator().generateWeek(1973, cw);
        Activities activities = underTest.sort(requestedFixture, nowFixture, TimeUnit.WEEK, data);
        assertNotNull(activities);
        assertNull(activities.weeks);
        assertNotNull(activities.days);
        assertTrue(activities.days.size() > 6);
        assertNull(activities.activities);

        for (Day day : activities.days)
        {
            for (Activity activity : day.activities)
            {
                assertEquals(cw, activity.getStart().getWeek());
                assertEquals(day.day, activity.getStart().getDay());
            }
        }
    }
}
