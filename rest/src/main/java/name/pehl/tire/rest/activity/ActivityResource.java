package name.pehl.tire.rest.activity;

import name.pehl.tire.dao.ActivityDao;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

/**
 * Supported methods:
 * <ul>
 * <li>GET /activities/{id}: Get an activity
 * <li>PUT /activities/{id}: Update an activity
 * <li>DELETE /activities/{id[,id2,...]}: Delete activity / activities
 * <li>PUT /activities/{id}/start: Start an activity
 * <li>PUT /activities/{id}/stop: Stop an activity
 * <li>PUT /activities/{id}/pause: Pause an activity
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision: 110
 *          $
 */
public class ActivityResource extends ServerResource
{
    private final ActivityDao activityDao;


    @Inject
    public ActivityResource(ActivityDao activityDao)
    {
        super();
        this.activityDao = activityDao;
    }


    @Get("json")
    public Representation getActivity()
    {
        ActivityParameters parameters = new ActivityParameters().parse(getRequestAttributes());
        if (parameters.hasId())
        {
            return new StringRepresentation(String.format("{\"id\": %d}", parameters.getId()));
        }
        if (parameters.hasId())
        {
            return new StringRepresentation(String.format("{\"id\": %d, \"action\": \"%s\"}", parameters.getId(),
                    parameters.getAction().name()));
        }
        return null;
    }
}
