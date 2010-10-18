package name.pehl.tire.rest.activity;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivitiesResource extends ServerResource
{
    @Post
    public void create()
    {

    }


    @Get("json")
    public Representation getActivities()
    {
        return null;
    }
}
