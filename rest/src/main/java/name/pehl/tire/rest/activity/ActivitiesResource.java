package name.pehl.tire.rest.activity;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.ActivitiesGenerator;
import name.pehl.tire.model.Activity;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;
import org.joda.time.Years;
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
 * 
 * @todo Need a timezone offset for current* resources
 * @todo Add hyperlinks to current, previous and next activities. If there are
 *       no previous / next activities omit the links
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
        String json = null;
        DateMidnight now = null;
        DateMidnight requested = null;
        ActivityParameters ap = null;
        List<Activity> activities = null;

        Form form = getRequest().getResourceRef().getQueryAsForm();
        DateTimeZone timeZone = parseTimeZone(form);
        now = new DateMidnight(timeZone);
        ap = new ActivityParameters().parse(getRequestAttributes());
        if (ap.isCurrentMonth() || ap.isCurrentWeek() || ap.isToday())
        {
            requested = new DateMidnight(timeZone);
            if (ap.isCurrentMonth())
            {
                activities = ensureValidActivities(dao.findByYearMonth(requested.year().get(), requested.monthOfYear()
                        .get()));
            }
            else if (ap.isCurrentWeek())
            {
                // activities =
                // ensureValidActivities(dao.findByYearWeek(requested.year().get(),
                // requested
                // .weekOfWeekyear().get()));
                activities = ensureValidActivities(new ActivitiesGenerator().generate(requested.year().get(), requested
                        .weekOfWeekyear().get()));
            }
            else if (ap.isToday())
            {
                activities = ensureValidActivities(dao.findByYearMonthDay(requested.year().get(), requested
                        .monthOfYear().get(), requested.dayOfMonth().get()));
            }
        }
        else if (ap.hasYear() && ap.hasMonth() && ap.hasDay())
        {
            requested = new DateMidnight(ap.getYear(), ap.getMonth(), ap.getDay(), timeZone);
            activities = ensureValidActivities(dao.findByYearMonthDay(requested.year().get(), requested.monthOfYear()
                    .get(), requested.dayOfMonth().get()));
        }
        else if (ap.hasYear() && ap.hasMonth())
        {
            requested = new DateMidnight(ap.getYear(), ap.getMonth(), 1, timeZone);
            activities = ensureValidActivities(dao.findByYearMonth(requested.year().get(), requested.monthOfYear()
                    .get()));
        }
        else if (ap.hasYear() && ap.hasWeek())
        {
            MutableDateTime mdt = new MutableDateTime(timeZone);
            mdt.year().set(ap.getYear());
            mdt.weekOfWeekyear().set(ap.getWeek());
            requested = new DateMidnight(mdt);
            // activities =
            // ensureValidActivities(dao.findByYearWeek(requested.year().get(),
            // requested.weekOfWeekyear()
            // .get()));
            activities = ensureValidActivities(new ActivitiesGenerator().generate(requested.year().get(), requested
                    .weekOfWeekyear().get()));
        }

        Activities activitiesforJson = createActivities(requested, now, activities);
        json = gson.toJson(activitiesforJson);
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
     * @param requested
     * @param now
     * @param activities
     * @return
     */
    private Activities createActivities(DateMidnight requested, DateMidnight now, List<Activity> activities)
    {
        int year = requested.year().get();
        int yearDiff = Years.yearsBetween(now, requested).getYears();
        int month = requested.monthOfYear().get();
        int monthDiff = Months.monthsBetween(now, requested).getMonths();
        int week = requested.weekOfWeekyear().get();
        int weekDiff = Weeks.weeksBetween(now, requested).getWeeks();

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
        return new Activities(year, yearDiff, month, monthDiff, week, weekDiff, days);
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
