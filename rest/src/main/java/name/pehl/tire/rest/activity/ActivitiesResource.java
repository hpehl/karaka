package name.pehl.tire.rest.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.Activity;

import org.joda.time.MutableDateTime;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * Supported methods:
 * <ul>
 * <li>POST: Create a new activity
 * <li>GET /activities/{year}/{month}: Find activities
 * <li>GET /activities/{year}/cw{week}: Find activities
 * <li>GET /activities/{year}/{month}/{day}: Find activities
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision: 110
 *          $
 */
public class ActivitiesResource extends ServerResource
{
    private final Gson gson;
    private final ActivityDao dao;


    @Inject
    public ActivitiesResource(ActivityDao dao, Gson gson)
    {
        this.dao = dao;
        this.gson = gson;
    }


    @Post
    public void create()
    {

    }


    @Get("json")
    public Representation getActivities()
    {
        String json = null;
        List<Activity> activities = null;

        ActivityParameters ap = new ActivityParameters().parse(getRequestAttributes());
        if (ap.hasYear() && ap.hasMonth() && ap.hasDay())
        {
            activities = dao.findByYearMonthDay(ap.getYear(), ap.getMonth(), ap.getDay());
        }
        else if (ap.hasYear() && ap.hasMonth())
        {
            activities = dao.findByYearMonth(ap.getYear(), ap.getMonth());
        }
        else if (ap.hasYear() && ap.hasWeek())
        {
            // activities = dao.findByYearWeek(ap.getYear(), ap.getWeek());
            activities = new ArrayList<Activity>();
            ActivityGenerator activityGenerator = new ActivityGenerator();
            MutableDateTime mdt = new MutableDateTime().year().set(ap.getYear()).weekyear().set(ap.getWeek())
                    .dayOfWeek().set(0);
            for (int i = 0; i < 5; i++)
            {
                mdt.addDays(i);
                activities.add(activityGenerator.generate(mdt.toDate()));
            }
            json = gson.toJson(new Week(ap.getYear(), ap.getWeek(), activities));
        }
        return new JsonRepresentation(json);
    }

    // -------------------------------------- inner classes for json conversion

    static class Week
    {
        final int year;
        final int week;
        final List<Day> days;


        Week(int year, int week, List<Activity> activities)
        {
            this.year = year;
            this.week = week;
            this.days = new ArrayList<Day>();
            // TODO Sort the activities by day
        }
    }

    static class Day
    {
        final Date date;
        final List<Activity> activities;


        Day(Date date, List<Activity> activities)
        {
            super();
            this.date = date;
            this.activities = activities;
        }
    }
}
