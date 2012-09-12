package name.pehl.tire.server.activity.boundary;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static name.pehl.tire.shared.model.TimeUnit.DAY;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import static org.joda.time.Months.months;
import static org.joda.time.Weeks.weeks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import name.pehl.tire.server.activity.control.ActivitiesConverter;
import name.pehl.tire.server.activity.control.ActivityConverter;
import name.pehl.tire.server.activity.control.ActivityIndexSearch;
import name.pehl.tire.server.activity.control.ActivityRepository;
import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.settings.control.CurrentSettings;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Duration;
import name.pehl.tire.shared.model.Durations;
import name.pehl.tire.shared.model.HasLinks;
import name.pehl.tire.shared.model.Year;
import name.pehl.tire.shared.model.Years;

import org.jboss.resteasy.spi.NotFoundException;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;

import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.googlecode.objectify.Key;

/**
 * Supported methods:
 * <ul>
 * <li>GET /activities/{year}/{month}: Find activities
 * <li>GET /activities/{year}/{month}/duration: Get the duration in minutes of
 * the specified activities
 * <li>GET /activities/relative/{month}: Find activities
 * <li>GET /activities/relative/{month}/duration: Get the duration in minutes of
 * the specified activities
 * <li>GET /activities/currentMonth: Find activities
 * <li>GET /activities/currentMonth/duration: Get the duration in minutes of the
 * specified activities
 * <li>GET /activities/{year}/cw{week}: Find activities
 * <li>GET /activities/{year}/cw{week}/duration: Get the duration in minutes of
 * the specified activities
 * <li>GET /activities/relative/cw{week}: Find activities
 * <li>GET /activities/relative/cw{week}/duration: Get the duration in minutes
 * of the specified activities
 * <li>GET /activities/currentWeek: Find activities
 * <li>GET /activities/currentWeek/duration: Get the duration in minutes of the
 * specified activities
 * <li>GET /activities/{year}/{month}/{day}: Find activities
 * <li>GET /activities/{year}/{month}/{day}/duration: Get the duration in
 * minutes of the specified activities
 * <li>GET /activities/today: Find activities
 * <li>GET /activities/today/duration: Get the duration in minutes of the
 * specified activities
 * <li>GET /activities/?q=&lt;name&gt;: Fid the activities with the specified
 * name
 * <li>GET /activities/current/durations: Get the durations of the current
 * month, week and day
 * <li>GET /activities/running: Find the running activity
 * <li>GET /activities/years: Returns the years, months and weeks in which
 * activities are stored
 * <li>POST: Create a new activity
 * <li>PUT /activities/{id}: Update an existing activity
 * <li>DELETE /activities/{id}: Delete an existing activity
 * </ul>
 * <p>
 * Normally for POST and PUT the start and end time is taken from the JSON input
 * and the minutes are calculated. There's one exception to this behaviour: If
 * </p>
 * <ul>
 * <li>the activity is stopped
 * <li>the start time is present
 * <li>the end time is not present and
 * <li>the minutes are specified
 * </ul>
 * <p>
 * then the end time is calculated.
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
    @Context UriInfo uriInfo;
    @Inject @CurrentSettings Settings settings;
    @Inject ActivityRepository repository;
    @Inject ActivityIndexSearch indexSearch;
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
        Years years = new Years(new TreeSet<Year>(lookup.values()));
        years.addLink(HasLinks.SELF, uriInfo.getAbsolutePath().toASCIIString());
        return years;
    }


    // --------------------------------------------------------------- by month

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}")
    public Activities activitiesForYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        DateMidnight yearMonth = new DateMidnight(year, month, 1, settings.getTimeZone());
        Activities activities = activitiesConverter.toModel(yearMonth, MONTH, forYearMonth(yearMonth));
        addLinksForYearMonth(activities, yearMonth);
        return activities;
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/duration")
    public Duration minutesForYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        return minutes(forYearMonth(new DateMidnight(year, month, 1, settings.getTimeZone())));
    }


    @GET
    @Path("/relative/{month:[+-]?\\d+}")
    public Activities activitiesForRelativeMonth(@PathParam("month") int month)
    {
        DateMidnight absolute = absoluteMonth(month);
        return activitiesConverter.toModel(absolute, MONTH, forYearMonth(absolute));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/relative/{month:[+-]?\\d+}/duration")
    public Duration minutesForRelativeMonth(@PathParam("month") int month)
    {
        return minutes(forYearMonth(absoluteMonth(month)));
    }


    @GET
    @Path("/currentMonth")
    public Activities activitiesForCurrentMonth()
    {
        DateMidnight now = now(settings.getTimeZone());
        return activitiesConverter.toModel(now, MONTH, forYearMonth(now));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/currentMonth/duration")
    public Duration minutesForCurrentMonth()
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


    private void addLinksForYearMonth(Activities activities, DateMidnight yearMonth)
    {
        activities.addLink(HasLinks.SELF, uriInfo.getAbsolutePath().toASCIIString());

        DateMidnight prevMonth = yearMonth.minusMonths(1);
        DateMidnight nextMonth = yearMonth.plusMonths(1);
        boolean hasPrev = repository.hasActivitiesByYearMonth(prevMonth.year().get(), prevMonth.monthOfYear().get());
        boolean hasNext = repository.hasActivitiesByYearMonth(nextMonth.year().get(), nextMonth.monthOfYear().get());
        if (hasPrev)
        {
            String prev = uriInfo.getBaseUriBuilder()
                    .segment(String.valueOf(prevMonth.year().get()), String.valueOf(prevMonth.monthOfYear().get()))
                    .build().toASCIIString();
            activities.addLink(HasLinks.PREV, prev);
        }
        if (hasNext)
        {
            String next = uriInfo.getBaseUriBuilder()
                    .segment(String.valueOf(nextMonth.year().get()), String.valueOf(nextMonth.monthOfYear().get()))
                    .build().toASCIIString();
            activities.addLink(HasLinks.NEXT, next);
        }
    }


    // ------------------------------------------------------- by calendar week

    @GET
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}")
    public Activities activitiesForYearWeek(@PathParam("year") int year, @PathParam("week") int week)
    {
        DateMidnight yearWeek = new MutableDateTime(settings.getTimeZone()).year().set(year).weekOfWeekyear().set(week)
                .toDateTime().toDateMidnight();
        return activitiesConverter.toModel(yearWeek, WEEK, forYearWeek(yearWeek));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}/duration")
    public Duration minutesForYearWeek(@PathParam("year") int year, @PathParam("week") int week)
    {
        DateMidnight yearWeek = new MutableDateTime(settings.getTimeZone()).year().set(year).weekOfWeekyear().set(week)
                .toDateTime().toDateMidnight();
        return minutes(forYearWeek(yearWeek));
    }


    @GET
    @Path("/relative/cw{week:[+-]?\\d+}")
    public Activities activitiesForRelativeWeek(@PathParam("week") int week)
    {
        DateMidnight absolute = absoluteWeek(week);
        return activitiesConverter.toModel(absolute, WEEK, forYearWeek(absolute));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/relative/cw{week:[+-]?\\d+}/duration")
    public Duration minutesForRelativeWeek(@PathParam("week") int week)
    {
        return minutes(forYearWeek(absoluteWeek(week)));
    }


    @GET
    @Path("/currentWeek")
    public Activities activitiesForCurrentWeek()
    {
        DateMidnight now = now(settings.getTimeZone());
        return activitiesConverter.toModel(now, WEEK, forYearWeek(now));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/currentWeek/duration")
    public Duration minutesForCurrentWeek()
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
        DateMidnight yearMonthDay = new DateMidnight(year, month, day, settings.getTimeZone());
        return activitiesConverter.toModel(yearMonthDay, DAY, forYearMonthDay(yearMonthDay));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}/duration")
    public Duration minutesForYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day)
    {
        return minutes(forYearMonthDay(new DateMidnight(year, month, day, settings.getTimeZone())));
    }


    @GET
    @Path("/today")
    public Activities activitiesForToday()
    {
        DateMidnight now = now(settings.getTimeZone());
        return activitiesConverter.toModel(now, DAY, forYearMonthDay(now));
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/today/duration")
    public Duration minutesForToday()
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


    // ------------------------------------------------ find activities by name

    @GET
    public List<name.pehl.tire.shared.model.Activity> findByName(@QueryParam("q") String query)
    {
        List<name.pehl.tire.shared.model.Activity> result = new ArrayList<name.pehl.tire.shared.model.Activity>();
        Results<ScoredDocument> results = indexSearch.search(query);
        for (ScoredDocument scoredDocument : results)
        {
            Long id = Long.valueOf(scoredDocument.getId());
            Activity activity = repository.get(id);
            if (activity != null)
            {
                result.add(activityConverter.toModel(activity));
            }
        }
        return result;
    }


    // --------------------------------- minutes of current month, week and day

    @GET
    @Path("/current/durations")
    public Durations minutesForCurrentMonthWeekAndDay()
    {
        DateMidnight now = now(settings.getTimeZone());
        Duration currentMonth = minutes(forYearMonth(now));
        Duration currentWeek = minutes(forYearWeek(now));
        Duration today = minutes(forYearMonthDay(now));
        return new Durations(currentMonth, currentWeek, today);
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


    // ------------------------------------------------------------ CUD methods

    @POST
    public Response saveNewActivity(name.pehl.tire.shared.model.Activity newClientActivity)
    {
        Activity newServerActivity = activityConverter.fromModel(newClientActivity);
        repository.put(newServerActivity);
        name.pehl.tire.shared.model.Activity createdClientActivity = activityConverter.toModel(newServerActivity);
        return Response.status(CREATED).entity(createdClientActivity).build();
    }


    @PUT
    @Path("{id}")
    public Response updateExistingActivity(@PathParam("id") String id,
            name.pehl.tire.shared.model.Activity modifiedClientActivity)
    {
        Activity existingServerActivity = repository.get(Key.<Activity> create(id));
        activityConverter.merge(modifiedClientActivity, existingServerActivity);
        repository.put(existingServerActivity);
        name.pehl.tire.shared.model.Activity updatedClientActivity = activityConverter.toModel(existingServerActivity);
        return Response.ok(updatedClientActivity).build();
    }


    @DELETE
    @Path("{id}")
    public Response deleteExistingActivity(@PathParam("id") String id)
    {
        Key<Activity> key = Key.<Activity> create(id);
        Activity activity = repository.get(key);
        if (activity != null)
        {
            repository.delete(activity);
            return Response.noContent().build();
        }
        return Response.status(NOT_FOUND).build();
    }


    // --------------------------------------------------------- helper methods

    private DateMidnight now(DateTimeZone timeZone)
    {
        return new DateMidnight(timeZone);
    }


    private Duration minutes(List<Activity> activities)
    {
        long minutes = 0;
        for (Activity activity : activities)
        {
            minutes += activity.getMinutes();
        }
        return new Duration(minutes);
    }
}
