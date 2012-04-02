package name.pehl.tire.server.rest.activity;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import name.pehl.tire.server.dao.ActivityDao;
import name.pehl.tire.server.model.ActivitiesGenerator;
import name.pehl.tire.server.model.Activity;

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
 * @todo Need a timezone offset for current* resources
 * @todo Add hyperlinks to current, previous and next activities. If there are
 *       no previous / next activities omit the links
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 */
@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
public class ActivitiesResource
{
    @Inject
    private ActivityDao dao;


    // --------------------------------------------------------------- by month

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}")
    public Activities activitiesByYearMonth(@PathParam("year") int year, @PathParam("month") int month,
            @QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(year, month, 1, timeZone);
        // activities = dao.findByYearMonth(requested.year().get(),
        // requested.monthOfYear().get());
        List<Activity> activities = new ActivitiesGenerator().generateMonth(requested.year().get(), requested
                .monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for year %d and month %d", year, month));
        }
        return new Activities.Builder(requested, timeZone, MONTH, activities).build();
    }


    @GET
    @Path("/relative/{month:[+-]?\\d+}")
    public Activities activitiesByRelativeMonth(@PathParam("month") int month, @QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight now = new DateMidnight(timeZone);
        DateMidnight relative = now.plus(months(month));
        int year = relative.year().get();
        int requestedMonth = relative.monthOfYear().get();
        DateMidnight requested = new DateMidnight(year, requestedMonth, 1, timeZone);
        // activities = dao.findByYearMonth(requested.year().get(),
        // requested.monthOfYear().get());
        List<Activity> activities = new ActivitiesGenerator().generateMonth(requested.year().get(), requested
                .monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for relative month %d", month));
        }
        return new Activities.Builder(requested, timeZone, MONTH, activities).now(now).build();
    }


    @GET
    @Path("/currentMonth")
    public Activities activitiesByCurrentMonth(@QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(timeZone);
        // activities = dao.findByYearMonth(requested.year().get(),
        // requested.monthOfYear().get());
        List<Activity> activities = new ActivitiesGenerator().generateMonth(requested.year().get(), requested
                .monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found for current month");
        }
        return new Activities.Builder(requested, timeZone, MONTH, activities).build();
    }


    // ------------------------------------------------------- by calendar week

    @GET
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}")
    public Activities activitiesByYearWeek(@PathParam("year") int year, @PathParam("week") int week,
            @QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        MutableDateTime mdt = new MutableDateTime(timeZone).year().set(year).weekOfWeekyear().set(week);
        DateMidnight requested = new DateMidnight(mdt);
        // activities = dao.findByYearWeek(requested.year().get(),
        // requested.weekOfWeekyear().get());
        List<Activity> activities = new ActivitiesGenerator().generateWeek(requested.year().get(), requested
                .weekOfWeekyear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for year %d and calendar week %d", year,
                    week));
        }
        return new Activities.Builder(requested, timeZone, WEEK, activities).build();
    }


    @GET
    @Path("/relative/cw{week:\\d{1,2}}")
    public Activities activitiesByRelativeWeek(@PathParam("week") int week, @QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight now = new DateMidnight(timeZone);
        DateMidnight relative = now.plus(weeks(week));
        int year = relative.year().get();
        int requestedWeek = relative.weekOfWeekyear().get();
        MutableDateTime mdt = new MutableDateTime(timeZone).year().set(year).weekOfWeekyear().set(requestedWeek);
        DateMidnight requested = new DateMidnight(mdt);
        // activities = dao.findByYearWeek(requested.year().get(),
        // requested.weekOfWeekyear().get());
        List<Activity> activities = new ActivitiesGenerator().generateWeek(requested.year().get(), requested
                .weekOfWeekyear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for relative calendar week %d", week));
        }
        return new Activities.Builder(requested, timeZone, WEEK, activities).now(now).build();
    }


    @GET
    @Path("/currentWeek")
    public Activities activitiesByCurrentWeek(@QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(timeZone);
        // activities = dao.findByYearWeek(requested.year().get(),
        // requested.weekOfWeekyear().get());
        List<Activity> activities = new ActivitiesGenerator().generateMonth(requested.year().get(), requested
                .monthOfYear().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found for current calendar week");
        }
        return new Activities.Builder(requested, timeZone, WEEK, activities).build();
    }


    // ----------------------------------------------------------------- by day

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}")
    public Activities activitiesByYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day, @QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(year, month, day, timeZone);
        List<Activity> activities = dao.findByYearMonthDay(requested.year().get(), requested.monthOfYear().get(),
                requested.dayOfMonth().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException(String.format("No activities found for year %d, month %d and day %d", year,
                    month, day));
        }
        return new Activities.Builder(requested, timeZone, DAY, activities).build();
    }


    @GET
    @Path("/today")
    public Activities activitiesByYearMonthDay(@QueryParam("tz") String timeZoneId)
    {
        DateTimeZone timeZone = parseTimeZone(timeZoneId);
        DateMidnight requested = new DateMidnight(timeZone);
        List<Activity> activities = dao.findByYearMonthDay(requested.year().get(), requested.monthOfYear().get(),
                requested.dayOfMonth().get());
        if (activities.isEmpty())
        {
            throw new NotFoundException("No activities found for today");
        }
        return new Activities.Builder(requested, timeZone, DAY, activities).build();
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
}
