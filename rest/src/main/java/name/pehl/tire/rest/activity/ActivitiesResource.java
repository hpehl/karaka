package name.pehl.tire.rest.activity;

import java.util.List;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.Activity;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

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
    private final ActivityDao dao;


    @Inject
    public ActivitiesResource(ActivityDao dao)
    {
        this.dao = dao;
    }


    @Post
    public void create()
    {

    }


    @Get("json")
    public Representation getActivities()
    {
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
            activities = dao.findByYearWeek(ap.getYear(), ap.getWeek());
        }
        return null;
    }
}
