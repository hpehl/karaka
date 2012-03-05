package name.pehl.tire.rest.client;

import name.pehl.taoki.paging.PageResult;
import name.pehl.taoki.paging.PagingHeaderResource;
import name.pehl.tire.model.Client;
import name.pehl.tire.server.dao.ClientDao;

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
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 110
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
        // TODO findByName
        // TODO orderBy
        PageResult<Client> pageResult = dao.list(getPageInfo());
        return null;
    }
}
