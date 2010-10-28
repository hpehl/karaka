package name.pehl.tire.rest.activity;

import name.pehl.tire.dao.ActivityDao;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
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
    private final ActivityDao activityDao;


    @Inject
    public ActivitiesResource(ActivityDao activityDao)
    {
        this.activityDao = activityDao;
    }


    @Post
    public void create()
    {

    }


    @Get("json")
    public Representation getActivities()
    {
        ActivityParameters parameters = new ActivityParameters().parse(getRequestAttributes());
        if (parameters.hasYear() && parameters.hasMonth() && parameters.hasDay())
        {
            return new StringRepresentation(String.format("{\"year\": %d, \"month\": %d, \"day\": %d}",
                    parameters.getYear(), parameters.getMonth(), parameters.getDay()));
        }
        else if (parameters.hasYear() && parameters.hasMonth())
        {
            return new StringRepresentation(String.format("{\"year\": %d, \"month\": %d}", parameters.getYear(),
                    parameters.getMonth()));
        }
        else if (parameters.hasYear() && parameters.hasWeek())
        {
            return new StringRepresentation(String.format("{\"year\": %d, \"week\": %d}", parameters.getYear(),
                    parameters.getWeek()));
        }
        return null;
    }
}
