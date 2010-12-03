package name.pehl.tire.rest.activity;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.Activity;

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
            activities = ensureValidActivities(dao.findByYearMonthDay(ap.getYear(), ap.getMonth(), ap.getDay()));
        }
        else if (ap.hasYear() && ap.hasMonth())
        {
            activities = ensureValidActivities(dao.findByYearMonth(ap.getYear(), ap.getMonth()));
        }
        else if (ap.hasYear() && ap.hasWeek())
        {
            // activities =
            // ensureValidActivities(dao.findByYearWeek(ap.getYear(),
            // ap.getWeek()));
            activities = ensureValidActivities(new ActivitiesGenerator().generate(ap.getYear(), ap.getWeek()));
            JsonWeek week = sortActivitiesByWeek(ap.getYear(), ap.getWeek(), activities);
            json = gson.toJson(week);
        }
        return new JsonRepresentation(json);
    }


    // --------------------------------------------------------- helper methods

    /**
     * Sort the specified activities into {@link JsonDay} instances and the days
     * into a {@link JsonWeek} instance.
     * 
     * @param activities
     * @return
     */
    private JsonWeek sortActivitiesByWeek(int year, int week, List<Activity> activities)
    {
        SortedSet<JsonDay> days = new TreeSet<JsonDay>();
        SortedSetMultimap<JsonDay, Activity> activitiesPerDay = TreeMultimap.create();
        for (Activity activity : activities)
        {
            JsonDay day = new JsonDay(activity.getStart().getDate());
            activitiesPerDay.put(day, activity);
        }
        for (JsonDay day : activitiesPerDay.keySet())
        {
            day.activities = activitiesPerDay.get(day);
            days.add(day);
        }
        return new JsonWeek(year, week, days);
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
