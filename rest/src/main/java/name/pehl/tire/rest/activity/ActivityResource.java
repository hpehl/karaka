package name.pehl.tire.rest.activity;

import java.util.List;

import name.pehl.tire.dao.ActivityDao;
import name.pehl.tire.model.Activity;
import name.pehl.tire.rest.EntityIdFinder;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
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
    private final ActivityDao dao;
    private final EntityIdFinder<Activity> eif;


    @Inject
    public ActivityResource(ActivityDao dao, EntityIdFinder<Activity> eif)
    {
        this.dao = dao;
        this.eif = eif;
    }


    @Get("json")
    public Representation getActivity()
    {
        Activity activity = eif.findById(this, dao, (String) getRequestAttributes().get("id"));
        return null;
    }


    @Put
    public void update()
    {
        Activity activity = eif.findById(this, dao, (String) getRequestAttributes().get("id"));
    }


    @Delete
    public void remove()
    {
        List<Activity> activities = eif.findByIds(this, dao, (String) getRequestAttributes().get("id"));
    }
}
