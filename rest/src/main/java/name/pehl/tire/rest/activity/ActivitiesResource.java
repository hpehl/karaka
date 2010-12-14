package name.pehl.tire.rest.activity;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.Activity;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;
import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * Supported methods:
 * <ul>
 * <li>POST: Create a new activity
 * <li>GET /activities/currentMonth: Find activities
 * <li>GET /activities/{year}/{month}: Find activities
 * <li>GET /activities/currentWeek: Find activities
 * <li>GET /activities/{year}/cw{week}: Find activities
 * <li>GET /activities/today: Find activities
 * <li>GET /activities/{year}/{month}/{day}: Find activities
 * </ul>
 * TODO Need a timezone offset for current* resources
 * 
 * @author $Author$
 * @version $Date$ $Revision: 110
 *          $
 */
public class ActivitiesResource extends ServerResource
{
    private static final String TIME_ZONE_ID = "tz";

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
        int year = 0;
        int month = 1;
        int week = 1;
        int day = 1;
        String json = null;
        List<Activity> activities = null;

        Form form = getRequest().getResourceRef().getQueryAsForm();
        ActivityParameters ap = new ActivityParameters().parse(getRequestAttributes());
        if (ap.isCurrentMonth() || ap.isCurrentWeek() || ap.isToday())
        {
            DateTime dateTime = new DateTime(parseTimeZone(form));
            year = dateTime.year().get();
            month = dateTime.monthOfYear().get();
            week = dateTime.weekOfWeekyear().get();
            day = dateTime.dayOfMonth().get();
            if (ap.isCurrentMonth())
            {
                activities = ensureValidActivities(dao.findByYearMonth(year, month));
            }
            else if (ap.isCurrentWeek())
            {
                // activities = ensureValidActivities(dao.findByYearWeek(year,
                // week));
                activities = ensureValidActivities(new ActivitiesGenerator().generate(year, week));
            }
            else if (ap.isToday())
            {
                activities = ensureValidActivities(dao.findByYearMonthDay(year, month, day));
            }
        }
        else if (ap.hasYear() && ap.hasMonth() && ap.hasDay())
        {
            year = ap.getYear();
            month = ap.getMonth();
            day = ap.getDay();
            activities = ensureValidActivities(dao.findByYearMonthDay(year, month, day));
        }
        else if (ap.hasYear() && ap.hasMonth())
        {
            year = ap.getYear();
            month = ap.getMonth();
            activities = ensureValidActivities(dao.findByYearMonth(year, month));
        }
        else if (ap.hasYear() && ap.hasWeek())
        {
            year = ap.getYear();
            week = ap.getWeek();
            // activities = ensureValidActivities(dao.findByYearWeek(year,
            // week));
            activities = ensureValidActivities(new ActivitiesGenerator().generate(ap.getYear(), ap.getWeek()));
        }

        Activities sortedActivities = sortActivities(year, month, week, activities);
        json = gson.toJson(sortedActivities);
        return new JsonRepresentation(json);
    }


    // --------------------------------------------------------- helper methods

    private DateTimeZone parseTimeZone(Form form)
    {
        String timeZoneId = form.getFirstValue(TIME_ZONE_ID);
        if (timeZoneId != null)
        {
            return DateTimeZone.forID(timeZoneId);
        }
        return DateTimeZone.getDefault();
    }


    /**
     * Sort the specified activities into {@link Day} instances and the days
     * into a {@link Activities} instance.
     * 
     * @param activities
     * @return
     */
    private Activities sortActivities(int year, int month, int week, List<Activity> activities)
    {
        SortedSet<Day> days = new TreeSet<Day>();
        SortedSetMultimap<Day, Activity> activitiesPerDay = TreeMultimap.create();
        for (Activity activity : activities)
        {
            Day day = new Day(activity.getStart().getDate());
            activitiesPerDay.put(day, activity);
        }
        for (Day day : activitiesPerDay.keySet())
        {
            day.activities = activitiesPerDay.get(day);
            days.add(day);
        }
        return new Activities(year, month, week, days);
    }


    /**
     * Throws a {@link ResourceException} with
     * {@link Status#CLIENT_ERROR_NOT_FOUND} in case the activites are null or
     * empty.
     * 
     * @param activities
     *            the activities to check
     * @return the specified activities
     * @throws ResourceException
     *             in case the activites are null or empty.
     */
    private List<Activity> ensureValidActivities(List<Activity> activities)
    {
        if (activities == null || activities.isEmpty())
        {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return activities;
    }
}
