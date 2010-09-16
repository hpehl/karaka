package name.pehl.tire.client.project;

import java.util.List;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Result;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface ProjectClientProxy extends ClientProxy
{
    @Get
    public void list(Result<List<Project>> callback);
}
