package name.pehl.tire.rest.activity;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivityResource extends ServerResource
{
    @Get("json")
    public Representation getActivity()
    {
        return null;
    }
}
