package name.pehl.karaka.server.activity.boundary;

import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.googlecode.objectify.Key;
import name.pehl.karaka.server.activity.control.ActivitiesConverter;
import name.pehl.karaka.server.activity.control.ActivityConverter;
import name.pehl.karaka.server.activity.control.ActivityIndexSearch;
import name.pehl.karaka.server.activity.control.ActivityRepository;
import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.paging.entity.PageResult;
import name.pehl.karaka.server.settings.control.CurrentSettings;
import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Duration;
import name.pehl.karaka.shared.model.Durations;
import name.pehl.karaka.shared.model.HasLinks;
import name.pehl.karaka.shared.model.Year;
import name.pehl.karaka.shared.model.Years;
import org.jboss.resteasy.annotations.cache.Cache;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jboss.resteasy.spi.NotFoundException;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.*;
import static name.pehl.karaka.shared.model.HasLinks.SELF;
import static name.pehl.karaka.shared.model.Status.STOPPED;
import static name.pehl.karaka.shared.model.TimeUnit.*;
import static org.joda.time.Months.months;
import static org.joda.time.Weeks.weeks;

/**
 * Supported methods:
 * <p/>
 * <p>Read methods:</p>
 * <ul>
 * <li>GET /activities/{year}/{month}: Find activities by year and month</li>
 * <li>GET /activities/{year}/{month}/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/relative/{month}: Find activities by month relative to the current month</li>
 * <li>GET /activities/relative/{month}/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/currentMonth: Find activities of the current month</li>
 * <li>GET /activities/currentMonth/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/{year}/cw{week}: Find activities by year and week</li>
 * <li>GET /activities/{year}/cw{week}/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/relative/cw{week}: Find activities by  week relative to the current week</li>
 * <li>GET /activities/relative/cw{week}/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/currentWeek: Find activities</li>
 * <li>GET /activities/currentWeek/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/{year}/{month}/{day}: Find activities</li>
 * <li>GET /activities/{year}/{month}/{day}/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/today: Find activities</li>
 * <li>GET /activities/today/duration: Get the duration in minutes of the specified activities</li>
 * <li>GET /activities/?q=&lt;name&gt;: Fid the activities with the specified name</li>
 * <li>GET /activities/current/durations: Get the durations of the current month, week and day</li>
 * <li>GET /activities/running: Find the running activity</li>
 * <li>GET /activities/years: Returns the years, months and weeks in which activities are stored</li>
 * </ul>
 * <p>CUD methods:</p>
 * <ul>
 * <li>PUT /activities/{id}: Update an existing activity. The data for the activity must be provided in the request
 * body. The end time of the activity is taken from the JSON input and the duration in minutes is calculated. There's
 * one exception to this rule: If
 * <ul>
 * <li>the activity is stopped</li>
 * <li>the init time is present</li>
 * <li>the end time is not present and</li>
 * <li>the duration in minutes is specified</li>
 * </ul>
 * then the end time is calculated. Please note that changes to the status of an activity are ignored by this method!
 * The only way to change the status of an activity is to call the relvant method / url pair.<br/>
 * The method returns 200 together with the updated activity.</li>
 * <li>PUT /activities/{id}/copy/{period}: Copy an existing activity as a new activity and adds the specified period.
 * The period must follow the format described at <a href="http://en.wikipedia.org/wiki/ISO_8601#Durations">ISO8601</a>.
 * The status of the original activity is not touched.<br/>
 * The method returns 201 together with the copied activity.</li>
 * <li>PUT /activities/init: Start a new activity. The data for the new activity must be provided in the request body.
 * The new activity will be stored as the running activity. If there's another running activity that activity will be
 * stopped first.<br/>
 * The method returns 201 together with the created / modified activities in a collection (even if there was only one
 * activity created).</li>
 * <li>PUT /activities/{id}/init: Start an existing activity. Depending on the activities init date the activity is
 * resumed (init date == today) or started as a new activity (init date != today). If there's another running
 * activity that activity will be stopped first.<br/>
 * The method returns 200 together with all modified activities in a collection (even if there was only one activity
 * modified). If the activity was already started nothing will happen and 304 is returned.</li>
 * <li>PUT /activities/{id}/tick: Tick an existing activity i.e. sets the end time to the current time and saves the
 * activity. If the activity is not yet started, it will be started first. If there's another running activity that
 * activity will be stopped first.<br/>
 * The method returns 200 together with all modified activities in a collection (even if there was only one activity
 * modified).</li>
 * <li>PUT /activities/{id}/stop: Stop an existing activity.<br/>
 * The method returns 200 together with the updated activity. If the activity is already stopped nothing will happen
 * and 304 is returned.</li>
 * <li>DELETE /activities/{id}: Delete an existing activity and return 204.</li>
 * </ul>
 *
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 * @todo Add hyperlinks to current, previous and next activities. If there are
 * no previous / next activities omit the links
 * @todo implement ETag
 */
@Cache
@Path("/activities")
@Produces(APPLICATION_JSON)
public class ActivitiesResource
{
    @Context UriInfo uriInfo;
    @Inject @CurrentSettings Instance<Settings> settings;
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
        years.addLink(SELF, uriInfo.getAbsolutePath().toASCIIString());
        return years;
    }


    // --------------------------------------------------------------- by month

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}")
    public Activities activitiesForYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        DateMidnight yearMonth = new DateMidnight(year, month, 1, settings.get().getTimeZone());
        Activities activities = activitiesConverter.toModel(yearMonth, MONTH, forYearMonth(yearMonth));
        addLinksForYearMonth(activities, yearMonth);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/duration")
    public Duration minutesForYearMonth(@PathParam("year") int year, @PathParam("month") int month)
    {
        return minutes(forYearMonth(new DateMidnight(year, month, 1, settings.get().getTimeZone())));
    }

    @GET
    @Path("/relative/{month:[+-]?\\d+}")
    public Activities activitiesForRelativeMonth(@PathParam("month") int month)
    {
        DateMidnight absolute = absoluteMonth(month);
        Activities activities = activitiesConverter.toModel(absolute, MONTH, forYearMonth(absolute));
        addLinksForYearMonth(activities, absolute);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/relative/{month:[+-]?\\d+}/duration")
    public Duration minutesForRelativeMonth(@PathParam("month") int month)
    {
        return minutes(forYearMonth(absoluteMonth(month)));
    }

    @GET
    @Path("/currentMonth")
    public Activities activitiesForCurrentMonth()
    {
        DateMidnight now = now(settings.get().getTimeZone());
        Activities activities = activitiesConverter.toModel(now, MONTH, forYearMonth(now));
        addLinksForYearMonth(activities, now);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/currentMonth/duration")
    public Duration minutesForCurrentMonth()
    {
        return minutes(forYearMonth(now(settings.get().getTimeZone())));
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
        DateMidnight now = now(settings.get().getTimeZone());
        return now.plus(months(month));
    }

    private void addLinksForYearMonth(Activities activities, DateMidnight yearMonth)
    {
        activities.addLink(SELF, uriInfo.getAbsolutePath().toASCIIString());

        DateMidnight prevMonth = yearMonth.minusMonths(1);
        DateMidnight nextMonth = yearMonth.plusMonths(1);
        boolean hasPrev = repository.hasActivitiesByYearMonth(prevMonth.year().get(), prevMonth.monthOfYear().get());
        boolean hasNext = repository.hasActivitiesByYearMonth(nextMonth.year().get(), nextMonth.monthOfYear().get());
        if (hasPrev)
        {
            String prev = uriInfo.getBaseUriBuilder().path("activities")
                    .segment(String.valueOf(prevMonth.year().get()), String.valueOf(prevMonth.monthOfYear().get()))
                    .build().toASCIIString();
            activities.addLink(HasLinks.PREV, prev);
        }
        if (hasNext)
        {
            String next = uriInfo.getBaseUriBuilder().path("activities")
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
        DateMidnight yearWeek = new MutableDateTime(settings.get().getTimeZone()).year().set(year).weekOfWeekyear()
                .set(week)
                .toDateTime().toDateMidnight();
        Activities activities = activitiesConverter.toModel(yearWeek, WEEK, forYearWeek(yearWeek));
        addLinksForYearWeek(activities, yearWeek);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/{year:\\d{4}}/cw{week:\\d{1,2}}/duration")
    public Duration minutesForYearWeek(@PathParam("year") int year, @PathParam("week") int week)
    {
        DateMidnight yearWeek = new MutableDateTime(settings.get().getTimeZone()).year().set(year).weekOfWeekyear()
                .set(week)
                .toDateTime().toDateMidnight();
        return minutes(forYearWeek(yearWeek));
    }

    @GET
    @Path("/relative/cw{week:[+-]?\\d+}")
    public Activities activitiesForRelativeWeek(@PathParam("week") int week)
    {
        DateMidnight absolute = absoluteWeek(week);
        Activities activities = activitiesConverter.toModel(absolute, WEEK, forYearWeek(absolute));
        addLinksForYearWeek(activities, absolute);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/relative/cw{week:[+-]?\\d+}/duration")
    public Duration minutesForRelativeWeek(@PathParam("week") int week)
    {
        return minutes(forYearWeek(absoluteWeek(week)));
    }

    @GET
    @Path("/currentWeek")
    public Activities activitiesForCurrentWeek()
    {
        DateMidnight now = now(settings.get().getTimeZone());
        Activities activities = activitiesConverter.toModel(now, WEEK, forYearWeek(now));
        addLinksForYearWeek(activities, now);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/currentWeek/duration")
    public Duration minutesForCurrentWeek()
    {
        return minutes(forYearWeek(now(settings.get().getTimeZone())));
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
        DateMidnight now = now(settings.get().getTimeZone());
        return now.plus(weeks(week));
    }

    private void addLinksForYearWeek(Activities activities, DateMidnight yearWeek)
    {
        activities.addLink(SELF, uriInfo.getAbsolutePath().toASCIIString());

        DateMidnight prevWeek = yearWeek.minusWeeks(1);
        DateMidnight nextWeek = yearWeek.plusWeeks(1);
        boolean hasPrev = repository.hasActivitiesByYearWeek(prevWeek.year().get(), prevWeek.weekOfWeekyear().get());
        boolean hasNext = repository.hasActivitiesByYearWeek(nextWeek.year().get(), nextWeek.weekOfWeekyear().get());
        if (hasPrev)
        {
            String prev = uriInfo
                    .getBaseUriBuilder()
                    .path("activities")
                    .segment(String.valueOf(prevWeek.year().get()),
                            "cw" + String.valueOf(prevWeek.weekOfWeekyear().get())).build().toASCIIString();
            activities.addLink(HasLinks.PREV, prev);
        }
        if (hasNext)
        {
            String next = uriInfo
                    .getBaseUriBuilder()
                    .path("activities")
                    .segment(String.valueOf(nextWeek.year().get()),
                            "cw" + String.valueOf(nextWeek.weekOfWeekyear().get())).build().toASCIIString();
            activities.addLink(HasLinks.NEXT, next);
        }
    }


    // ----------------------------------------------------------------- by day

    @GET
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}")
    public Activities activitiesForYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day)
    {
        DateMidnight yearMonthDay = new DateMidnight(year, month, day, settings.get().getTimeZone());
        Activities activities = activitiesConverter.toModel(yearMonthDay, DAY, forYearMonthDay(yearMonthDay));
        addLinksForYearMonthDay(activities, yearMonthDay);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/{year:\\d{4}}/{month:\\d{1,2}}/{day:\\d{1,2}}/duration")
    public Duration minutesForYearMonthDay(@PathParam("year") int year, @PathParam("month") int month,
            @PathParam("day") int day)
    {
        return minutes(forYearMonthDay(new DateMidnight(year, month, day, settings.get().getTimeZone())));
    }

    @GET
    @Path("/today")
    public Activities activitiesForToday()
    {
        DateMidnight now = now(settings.get().getTimeZone());
        Activities activities = activitiesConverter.toModel(now, DAY, forYearMonthDay(now));
        addLinksForYearMonthDay(activities, now);
        return activities;
    }

    @GET
    @Produces(TEXT_PLAIN)
    @Path("/today/duration")
    public Duration minutesForToday()
    {
        return minutes(forYearMonthDay(now(settings.get().getTimeZone())));
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

    private void addLinksForYearMonthDay(Activities activities, DateMidnight yearMonthDay)
    {
        activities.addLink(SELF, uriInfo.getAbsolutePath().toASCIIString());

        DateMidnight prevDay = yearMonthDay.minusDays(1);
        DateMidnight nextDay = yearMonthDay.plusDays(1);
        boolean hasPrev = repository.hasActivitiesByYearMonthDay(prevDay.year().get(), prevDay.monthOfYear().get(),
                prevDay.dayOfMonth().get());
        boolean hasNext = repository.hasActivitiesByYearMonthDay(nextDay.year().get(), nextDay.monthOfYear().get(),
                nextDay.dayOfMonth().get());
        if (hasPrev)
        {
            String prev = uriInfo
                    .getBaseUriBuilder()
                    .path("activities")
                    .segment(String.valueOf(prevDay.year().get()), String.valueOf(prevDay.monthOfYear().get()),
                            String.valueOf(prevDay.dayOfMonth().get())).build().toASCIIString();
            activities.addLink(HasLinks.PREV, prev);
        }
        if (hasNext)
        {
            String next = uriInfo
                    .getBaseUriBuilder()
                    .path("activities")
                    .segment(String.valueOf(nextDay.year().get()), String.valueOf(nextDay.monthOfYear().get()),
                            String.valueOf(nextDay.dayOfMonth().get())).build().toASCIIString();
            activities.addLink(HasLinks.NEXT, next);
        }
    }


    // ------------------------------------------------ find activities by name

    @GET
    @NoCache
    public List<name.pehl.karaka.shared.model.Activity> findByName(@QueryParam("q") String query)
    {
        List<name.pehl.karaka.shared.model.Activity> result = new ArrayList<name.pehl.karaka.shared.model.Activity>();
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
        DateMidnight now = now(settings.get().getTimeZone());
        Duration currentMonth = minutes(forYearMonth(now));
        Duration currentWeek = minutes(forYearWeek(now));
        Duration today = minutes(forYearMonthDay(now));
        Durations durations = new Durations(currentMonth, currentWeek, today);
        durations.addLink(SELF, uriInfo.getAbsolutePath().toASCIIString());
        return durations;
    }


    // -------------------------------------------------------------- by status

    @GET
    @Path("/running")
    public name.pehl.karaka.shared.model.Activity runningActivity()
    {
        Activity activity = repository.findRunningActivity();
        if (activity == null)
        {
            throw new NotFoundException("No running activity");
        }
        return activityConverter.toModel(activity);
    }


    // ------------------------------------------------------------ CUD methods

    @PUT
    @Path("/{id}")
    public Response updateActivity(@PathParam("id") String id,
            name.pehl.karaka.shared.model.Activity clientActivity)
    {
        try
        {
            Activity serverActivity = repository.get(Key.<Activity>create(id));
            activityConverter.merge(clientActivity, serverActivity);
            repository.put(serverActivity);
            name.pehl.karaka.shared.model.Activity updatedClientActivity = activityConverter.toModel(serverActivity);
            return Response.ok(updatedClientActivity).build();
        }
        catch (com.googlecode.objectify.NotFoundException e)
        {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/copy/{period}")
    public Response copyActivity(@PathParam("id") String id, @PathParam("period") String period)
    {
        try
        {
            Activity activity = repository.get(Key.<Activity>create(id));
            Activity copy = activity.copy(period);
            repository.put(copy);
            name.pehl.karaka.shared.model.Activity clientCopy = activityConverter.toModel(copy);
            return Response.status(CREATED).entity(clientCopy).build();
        }
        catch (com.googlecode.objectify.NotFoundException e)
        {
            return Response.status(NOT_FOUND).build();
        }
        catch (IllegalArgumentException e)
        {
            return Response.status(BAD_REQUEST).entity(
                    "Illegal format for period \"" + period + "\". See http://en.wikipedia.org/wiki/ISO_8601#Durations for a valid format")
                    .build();
        }
    }

    @PUT
    @Path("/start")
    public Response startNewActivity(name.pehl.karaka.shared.model.Activity clientActivity)
    {
        // TODO Is it an error if the client activity already exists on the server?
        Activity newServerActivity = activityConverter.fromModel(clientActivity);
        repository.put(newServerActivity);
        name.pehl.karaka.shared.model.Activity createdClientActivity = activityConverter.toModel(newServerActivity);
        return Response.status(CREATED).entity(createdClientActivity).build();
    }

    @PUT
    @Path("/{id}/start")
    public Response startActivity(@PathParam("id") String id)
    {
        try
        {
            Activity activity = repository.get(Key.<Activity>create(id));
            Iterable<Activity> modifiedActivities = repository.start(activity);
            Set<name.pehl.karaka.shared.model.Activity> clientActivities = new HashSet<name.pehl.karaka.shared.model.Activity>();
            for (Activity modifiedActivity : modifiedActivities)
            {
                clientActivities.add(activityConverter.toModel(modifiedActivity));
            }
            return Response.ok(clientActivities).build();
        }
        catch (com.googlecode.objectify.NotFoundException e)
        {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/tick")
    public Response tickActivity(@PathParam("id") String id)
    {
        try
        {
            Activity activity = repository.get(Key.<Activity>create(id));
            Iterable<Activity> modifiedActivities = repository.tick(activity);
            Set<name.pehl.karaka.shared.model.Activity> clientActivities = new HashSet<name.pehl.karaka.shared.model.Activity>();
            for (Activity modifiedActivity : modifiedActivities)
            {
                clientActivities.add(activityConverter.toModel(modifiedActivity));
            }
            return Response.ok(clientActivities).build();
        }
        catch (com.googlecode.objectify.NotFoundException e)
        {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}/stop")
    public Response stopActivity(@PathParam("id") String id)
    {
        try
        {
            Activity activity = repository.get(Key.<Activity>create(id));
            if (activity.getStatus() == STOPPED)
            {
                return Response.status(NOT_MODIFIED).build();
            }
            else
            {
                activity.stop();
                repository.put(activity);
                name.pehl.karaka.shared.model.Activity clientActivity = activityConverter.toModel(activity);
                return Response.ok(clientActivity).build();
            }
        }
        catch (com.googlecode.objectify.NotFoundException e)
        {
            return Response.status(NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActivity(@PathParam("id") String id)
    {
        try
        {
            Activity activity = repository.get(Key.<Activity>create(id));
            repository.delete(activity);
            return Response.noContent().build();
        }
        catch (com.googlecode.objectify.NotFoundException e)
        {
            return Response.status(NOT_FOUND).build();
        }
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
            minutes += activity.getDuration();
        }
        return new Duration(minutes);
    }
}
