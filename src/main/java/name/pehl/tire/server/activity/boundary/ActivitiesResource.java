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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import name.pehl.tire.server.activity.control.ActivitiesConverter;
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
 * <li>GET /activities/relative/{month}: Find activities
 * <li>GET /activities/currentMonth: Find activities
 * <li>GET /activities/{year}/cw{week}: Find activities
 * <li>GET /activities/relative/cw{week}: Find activities
 * <li>GET /activities/currentWeek: Find activities
 * <li>GET /activities/{year}/{month}/{day}: Find activities
 * <li>GET /activities/today: Find activities
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
    public Activities activitiesByYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight requested = new DateMidnight(year, month, 1, settings.getTimeZone());
        List<Activity> activities = repository.findByYearMonth(requested.year().get(), requested.monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for year %d and month %d", year, month));
        }
        return activitiesConverter.toModel(requested, now(timeZone), MONTH, activities);
    }


    @GET
    @Path("/relative/{month:[+-]?\\d+}")
    public Activities activitiesByRelativeMonth(@PathParam("month") int month)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight now = now(timeZone);
        DateMidnight relative = now.plus(months(month));
        int year = relative.year().get();
        int requestedMonth = relative.monthOfYear().get();
        DateMidnight requested = new DateMidnight(year, requestedMonth, 1, timeZone);
        List<Activity> activities = repository.findByYearMonth(requested.year().get(), requested.monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for relative month %d", month));
        }
        return activitiesConverter.toModel(requested, now, MONTH, activities);
    }


    @GET
    @Path("/currentMonth")
    public Activities activitiesByCurrentMonth(@QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(timeZone);
        List<Activity> activities = repository.findByYearMonth(requested.year().get(), requested.monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found for current month");
        }
        return activitiesConverter.toModel(requested, now(timeZone), MONTH, activities);
    }


    // ------------------------------------------------------- by calendar week

    @GET
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}")
    public Activities activitiesByYearWeek(@PathParam("year") int year, @PathParam("week") int week)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        MutableDateTime mdt = new MutableDateTime(timeZone).year().set(year).weekOfWeekyear().set(week);
        DateMidnight requested = new DateMidnight(mdt);
        List<Activity> activities = repository.findByYearWeek(requested.year().get(), requested.weekOfWeekyear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for year %d and calendar week %d", year,
                    week));
        }
        return activitiesConverter.toModel(requested, now(timeZone), WEEK, activities);
    }


    @GET
    @Path("/relative/cw{week:[+-]?\\d+}")
    public Activities activitiesByRelativeWeek(@PathParam("week") int week)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight now = now(timeZone);
        DateMidnight relative = now.plus(weeks(week));
        int year = relative.year().get();
        int requestedWeek = relative.weekOfWeekyear().get();
        MutableDateTime mdt = new MutableDateTime(timeZone).year().set(year).weekOfWeekyear().set(requestedWeek);
        DateMidnight requested = new DateMidnight(mdt);
        List<Activity> activities = repository.findByYearWeek(requested.year().get(), requested.weekOfWeekyear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for relative calendar week %d", week));
        }
        return activitiesConverter.toModel(requested, now, WEEK, activities);
    }


    @GET
    @Path("/currentWeek")
    public Activities activitiesByCurrentWeek(@QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(timeZone);
        List<Activity> activities = repository.findByYearWeek(requested.year().get(), requested.weekOfWeekyear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found for current calendar week");
        }
        return activitiesConverter.toModel(requested, now(timeZone), WEEK, activities);
    }


    // ----------------------------------------------------------------- by day

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}")
    public Activities activitiesByYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day)
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight requested = new DateMidnight(year, month, day, timeZone);
        List<Activity> activities = repository.findByYearMonthDay(requested.year().get(),
                requested.monthOfYear().get(), requested.dayOfMonth().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for year %d, month %d and day %d", year,
                    month, day));
        }
        return activitiesConverter.toModel(requested, now(timeZone), DAY, activities);
    }


    @GET
    @Path("/today")
    public Activities activitiesByYearMonthDay()
    {
        DateTimeZone timeZone = settings.getTimeZone();
        DateMidnight requested = new DateMidnight(timeZone);
        List<Activity> activities = repository.findByYearMonthDay(requested.year().get(),
                requested.monthOfYear().get(), requested.dayOfMonth().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found for today");
        }
        return activitiesConverter.toModel(requested, now(timeZone), DAY, activities);
    }


    // --------------------------------------------------------- helper methods

    private DateTimeZone parseTimeZone(String timeZoneId)
    {
        if (timeZoneId != null)
        {
            return DateTimeZone.forID(timeZoneId);
        }
        return DateTimeZone.getDefault();
    }


    private DateMidnight now(DateTimeZone timeZone)
    {
        return new DateMidnight(timeZone);
    }
}
