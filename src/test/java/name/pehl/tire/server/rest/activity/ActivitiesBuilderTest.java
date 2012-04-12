package name.pehl.tire.server.rest.activity;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import name.pehl.tire.server.model.ActivitiesGenerator;
import name.pehl.tire.server.model.Activity;
import name.pehl.tire.shared.model.Day;
import name.pehl.tire.shared.model.Week;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ActivitiesBuilderTest
{
    private DateMidnight requestedFixture;


    @Before
    public void setUp() throws Exception
    {
        requestedFixture = new DateMidnight();
    }


    @Test
    public void testSortByMonth()
    {
        List<Activity> data = new ActivitiesGenerator().generateMonth(1973, 9);
        Activities activities = new ActivitiesBuilder(requestedFixture, DateTimeZone.getDefault(), MONTH, data)
                .build();
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
        Activities activities = new ActivitiesBuilder(requestedFixture, DateTimeZone.getDefault(), WEEK, data).build();
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
