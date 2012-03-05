package name.pehl.tire.rest.activity;

import name.pehl.tire.model.Activity;
import name.pehl.tire.rest.EntityIdFinder;
import name.pehl.tire.server.dao.ActivityDao;

import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import com.google.gson.Gson;
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
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 00:35:23 +0100 (Fr, 03. Dez 2010) $ $Revision: 110
 *          $
 */
public class ActivityResource extends ServerResource
{
    private final Gson gson;
    private final ActivityDao dao;
    private final EntityIdFinder<Activity> eif;


    @Inject
    public ActivityResource(ActivityDao dao, EntityIdFinder<Activity> eif, Gson gson)
    {
        this.dao = dao;
        this.eif = eif;
        this.gson = gson;
    }


    @Get("json")
    public Representation getActivity()
    {
        Activity activity = eif.findById(this, dao, (String) getRequestAttributes().get("id"));
        if (activity == null)
        {
            throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND);
        }
        String json = gson.toJson(activity);
        return new JsonRepresentation(json);
    }


    @Put
    public void update()
    {
        // TODO Implement me
        // Activity activity = eif.findById(this, dao, (String)
        // getRequestAttributes().get("id"));
    }


    @Delete
    public void remove()
    {
        // TODO Implement me
        // List<Activity> activities = eif.findByIds(this, dao, (String)
        // getRequestAttributes().get("id"));
    }
}
