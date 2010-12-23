package name.pehl.tire.rest.activity;

import static name.pehl.tire.model.TimeUnit.*;
import static org.joda.time.Months.months;
import static org.joda.time.Weeks.weeks;

import java.util.List;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.ActivitiesGenerator;
import name.pehl.tire.model.Activity;
import name.pehl.tire.model.TimeUnit;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * Supported methods:
 * <ul>
 * <li>POST: Create a new activity
 * <li>GET /activities/{year}/{month}: Find activities
 * <li>GET /activities/relative/{month}: Find activities
 * <li>GET /activities/currentMonth: Find activities
 * <li>GET /activities/{year}/cw{week}: Find activities
 * <li>GET /activities/relative/cw{week}: Find activities
 * <li>GET /activities/currentWeek: Find activities
 * <li>GET /activities/{year}/{month}/{day}: Find activities
 * <li>GET /activities/today: Find activities *
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
        ActivityParameters ap = new ActivityParameters().parse(getRequestAttributes());
        DateTimeZone timeZone = parseTimeZone(getRequest().getResourceRef().getQueryAsForm());
        Activities activities = retrieveActivities(ap, timeZone);

        String json = gson.toJson(activities);
        return new JsonRepresentation(json);
    }


    protected Activities retrieveActivities(ActivityParameters ap, DateTimeZone timeZone)
    {
        TimeUnit unit = null;
        DateMidnight now = null;
        DateMidnight requested = null;
        List<Activity> activities = null;

        now = new DateMidnight(timeZone);
        if (ap.isCurrentMonth() || ap.isCurrentWeek() || ap.isToday())
        {
            requested = new DateMidnight(timeZone);
            if (ap.isCurrentMonth())
            {
                // activities =
                // dao.findByYearMonth(requested.year().get(),
                // requested.monthOfYear()
                // .get());
                unit = MONTH;
                activities = new ActivitiesGenerator().generateMonth(requested.year().get(), requested.monthOfYear()
                        .get());
            }
            else if (ap.isCurrentWeek())
            {
                // activities =
                // dao.findByYearWeek(requested.year().get(),
                // requested
                // .weekOfWeekyear().get());
                unit = WEEK;
                activities = new ActivitiesGenerator().generateWeek(requested.year().get(), requested.weekOfWeekyear()
                        .get());
            }
            else if (ap.isToday())
            {
                unit = DAY;
                activities = dao.findByYearMonthDay(requested.year().get(), requested.monthOfYear().get(), requested
                        .dayOfMonth().get());
            }
        }
        else if (ap.hasYear() && ap.hasMonth() && ap.hasDay())
        {
            unit = DAY;
            requested = new DateMidnight(ap.getYear(), ap.getMonth(), ap.getDay(), timeZone);
            activities = dao.findByYearMonthDay(requested.year().get(), requested.monthOfYear().get(), requested
                    .dayOfMonth().get());
        }
        else if ((ap.hasYear() || ap.isRelative()) && ap.hasMonth())
        {
            unit = MONTH;
            int year = ap.getYear();
            int requestedMonth = ap.getMonth();
            if (ap.isRelative())
            {
                DateMidnight relative = now.plus(months(requestedMonth));
                year = relative.year().get();
                requestedMonth = relative.monthOfYear().get();
            }
            requested = new DateMidnight(year, requestedMonth, 1, timeZone);
            // activities =
            // dao.findByYearMonth(requested.year().get(),
            // requested.monthOfYear()
            // .get());
            activities = new ActivitiesGenerator().generateMonth(requested.year().get(), requested.monthOfYear().get());
        }
        else if ((ap.hasYear() || ap.isRelative()) && ap.hasWeek())
        {
            unit = WEEK;
            int year = ap.getYear();
            int requestedWeek = ap.getWeek();
            if (ap.isRelative())
            {
                DateMidnight relative = now.plus(weeks(requestedWeek));
                year = relative.year().get();
                requestedWeek = relative.weekOfWeekyear().get();
            }
            MutableDateTime mdt = new MutableDateTime(timeZone).year().set(year).weekOfWeekyear().set(requestedWeek);
            requested = new DateMidnight(mdt);
            // activities =
            // dao.findByYearWeek(requested.year().get(),
            // requested.weekOfWeekyear()
            // .get());
            activities = new ActivitiesGenerator().generateWeek(requested.year().get(), requested.weekOfWeekyear()
                    .get());
        }
        return new ActivitiesSorter().sort(requested, now, unit, ensureValidActivities(activities));
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
