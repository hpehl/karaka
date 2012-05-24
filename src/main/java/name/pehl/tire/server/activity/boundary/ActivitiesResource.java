package name.pehl.tire.server.activity.boundary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import name.pehl.tire.server.activity.control.ActivitiesConverter;
import name.pehl.tire.server.activity.control.ActivityConverter;
import name.pehl.tire.server.activity.control.ActivityRepository;
import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.settings.control.CurrentSettings;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Year;
import name.pehl.tire.shared.model.Years;

import org.jboss.resteasy.spi.NotFoundException;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;

import static name.pehl.tire.shared.model.TimeUnit.DAY;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import static org.joda.time.Months.months;
import static org.joda.time.Weeks.weeks;

/**
 * Supported methods:
 * <ul>
 * <li>POST: Create a new activity
 * <li>GET /activities/{year}/{month}: Find activities
 * <li>GET /activities/{year}/{month}/minutes: Get the minutes of the specified
 * activities
 * <li>GET /activities/relative/{month}: Find activities
 * <li>GET /activities/relative/{month}/minutes: Get the minutes of the
 * specified activities
 * <li>GET /activities/currentMonth: Find activities
 * <li>GET /activities/currentMonth/minutes: Get the minutes of the specified
 * activities
 * <li>GET /activities/{year}/cw{week}: Find activities
 * <li>GET /activities/{year}/cw{week}/minutes: Get the minutes of the specified
 * activities
 * <li>GET /activities/relative/cw{week}: Find activities
 * <li>GET /activities/relative/cw{week}/minutes: Get the minutes of the
 * specified activities
 * <li>GET /activities/currentWeek: Find activities
 * <li>GET /activities/currentWeek/minutes: Get the minutes of the specified
 * activities
 * <li>GET /activities/{year}/{month}/{day}: Find activities
 * <li>GET /activities/{year}/{month}/{day}/minutes: Get the minutes of the
 * specified activities
 * <li>GET /activities/today: Find activities
 * <li>GET /activities/today/minutes: Get the minutes of the specified
 * activities
 * <li>GET /activities/running: Find the running activity
 * <li>GET /activities/years: Returns the years, months and weeks in which
 * activities are stored
 * </ul>
 * 
 * @todo Add hyperlinks to current, previous and next activities. If there are
 *       no previous / next activities omit the links
 * @todo implement ETag
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 */
@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
public class ActivitiesResource
{
    @Inject @CurrentSettings Settings settings;
    @Inject ActivityRepository repository;
    @Inject ActivitiesConverter activitiesConverter;
    @Inject ActivityConverter activityConverter;


    // ------------------------------------------------- years / months / weeks

    @GET
    @Path("/years")
    public Years years()
    {
        PageResult<Activity> activities = repository.list();
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found");
        }
        Map<Integer, Year> lookup = new HashMap<Integer, Year>();
        for (Activity activity : activities)
        {
            int y = activity.getStart().getYear();
            int m = activity.getStart().getMonth();
            int w = activity.getStart().getWeek();
            Year year = lookup.get(y);
            if (year == null)
            {
                year = new Year(y);
                lookup.put(y, year);
            }
            year.addMonth(m);
            year.addWeek(w);
        }
        return new Years(new TreeSet<Year>(lookup.values()));
    }


    // --------------------------------------------------------------- by month

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}")
    public Activities activitiesForYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight yearMonth = new DateMidnight(year, month, 1, timeZone);
        return activitiesConverter.toModel(yearMonth, now(timeZone), MONTH, forYearMonth(yearMonth));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/minutes")
    public int minutesForYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        return minutes(forYearMonth(new DateMidnight(year, month, 1, settings.getTimeZone())));
    }


    @GET
    @Path("/relative/{month:[+-]?\\d+}")
    public Activities activitiesForRelativeMonth(@PathParam("month") int month)
    {
        DateMidnight absolute = absoluteMonth(month);
        return activitiesConverter.toModel(absolute, now(settings.getTimeZone()), MONTH, forYearMonth(absolute));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/relative/{month:[+-]?\\d+}/minutes")
    public int minutesForRelativeMonth(@PathParam("month") int month)
    {
        return minutes(forYearMonth(absoluteMonth(month)));
    }


    @GET
    @Path("/currentMonth")
    public Activities activitiesForCurrentMonth()
    {
        DateMidnight now = now(settings.getTimeZone());
        return activitiesConverter.toModel(now, now, MONTH, forYearMonth(now));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/currentMonth/minutes")
    public int minutesForCurrentMonth()
    {
        return minutes(forYearMonth(now(settings.getTimeZone())));
    }


    private List<Activity> forYearMonth(DateMidnight date)
    {
        List<Activity> activities = repository.findByYearMonth(date.year().get(), date.monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for %d/%d", date.monthOfYear().get(), date
                    .year().get()));
        }
        return activities;
    }


    private DateMidnight absoluteMonth(int month)
    {
        DateMidnight now = now(settings.getTimeZone());
        return now.plus(months(month));
    }


    // ------------------------------------------------------- by calendar week

    @GET
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}")
    public Activities activitiesForYearWeek(@PathParam("year") int year, @PathParam("week") int week)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        MutableDateTime mdt = new MutableDateTime(timeZone).year().set(year).weekOfWeekyear().set(week);
        DateMidnight yearWeek = new DateMidnight(mdt);
        return activitiesConverter.toModel(yearWeek, now(timeZone), WEEK, forYearWeek(yearWeek));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}/minutes")
    public int minutesForYearWeek(@PathParam("year") int year, @PathParam("week") int week)
    {
        MutableDateTime mdt = new MutableDateTime(settings.getTimeZone()).year().set(year).weekOfWeekyear().set(week);
        DateMidnight yearWeek = new DateMidnight(mdt);
        return minutes(forYearWeek(yearWeek));
    }


    @GET
    @Path("/relative/cw{week:[+-]?\\d+}")
    public Activities activitiesForRelativeWeek(@PathParam("week") int week)
    {
        DateMidnight absolute = absoluteWeek(week);
        return activitiesConverter.toModel(absolute, now(settings.getTimeZone()), WEEK, forYearWeek(absolute));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/relative/cw{week:[+-]?\\d+}/minutes")
    public int minutesForRelativeWeek(@PathParam("week") int week)
    {
        return minutes(forYearWeek(absoluteWeek(week)));
    }


    @GET
    @Path("/currentWeek")
    public Activities activitiesForCurrentWeek()
    {
        DateMidnight now = now(settings.getTimeZone());
        return activitiesConverter.toModel(now, now, WEEK, forYearWeek(now));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/currentWeek/minutes")
    public int minutesForCurrentWeek()
    {
        return minutes(forYearWeek(now(settings.getTimeZone())));
    }


    private List<Activity> forYearWeek(DateMidnight date)
    {
        List<Activity> activities = repository.findByYearWeek(date.year().get(), date.weekOfWeekyear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for CW %d/%d", date.weekOfWeekyear().get(),
                    date.year().get()));
        }
        return activities;
    }


    private DateMidnight absoluteWeek(int week)
    {
        DateMidnight now = now(settings.getTimeZone());
        return now.plus(weeks(week));
    }


    // ----------------------------------------------------------------- by day

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}")
    public Activities activitiesForYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight yearMonthDay = new DateMidnight(year, month, day, timeZone);
        return activitiesConverter.toModel(yearMonthDay, now(timeZone), DAY, forYearMonthDay(yearMonthDay));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}/minutes")
    public int minutesForYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day)
    {
        return minutes(forYearMonthDay(new DateMidnight(year, month, day, settings.getTimeZone())));
    }


    @GET
    @Path("/today")
    public Activities activitiesForToday()
    {
        DateMidnight now = now(settings.getTimeZone());
        return activitiesConverter.toModel(now, now, DAY, forYearMonthDay(now));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/today/minutes")
    public int minutesForToday()
    {
        return minutes(forYearMonthDay(now(settings.getTimeZone())));
    }


    private List<Activity> forYearMonthDay(DateMidnight date)
    {
        List<Activity> activities = repository.findByYearMonthDay(date.year().get(), date.monthOfYear().get(), date
                .dayOfMonth().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for %d/%d/%d", date.dayOfMonth().get(), date
                    .monthOfYear().get(), date.year().get()));
        }
        return activities;
    }


    // -------------------------------------------------------------- by status

    @GET
    @Path("/running")
    public name.pehl.tire.shared.model.Activity runningActivity()
    {
        Activity activity = repository.findRunningActivity();
        if (activity == null)
        {
            throw new NotFoundException("No running activity");
        }
        return activityConverter.toModel(activity);
    }


    // --------------------------------------------------------- helper methods

    private DateMidnight now(DateTimeZone timeZone)
    {
        return new DateMidnight(timeZone);
    }


    private int minutes(List<Activity> activities)
    {
        int minutes = 0;
        for (Activity activity : activities)
        {
            minutes += activity.getMinutes();
        }
        return minutes;
    }
}
