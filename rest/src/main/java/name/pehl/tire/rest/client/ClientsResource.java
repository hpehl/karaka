package name.pehl.tire.rest.client;

import name.pehl.taoki.paging.PagingHeaderResource;
import name.pehl.tire.dao.ClientDao;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.google.inject.Inject;

/**
 * Supported methods:
 * <ul>
 * <li>POST: Create a new client
 * <li>GET /projects[?name=&lt;case insensitive string&gt;]: List clients
 * (Paging is supported. See {@link PagingHeaderResource})
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision: 110
 *          $
 */
public class ClientsResource extends PagingHeaderResource
{
    private final ClientDao dao;


    @Inject
    public ClientsResource(ClientDao dao)
    {
        this.dao = dao;
    }


    @Post
    public void create()
    {

    }


    @Get("json")
    public Representation getClients()
    {
        return null;
    }
}
