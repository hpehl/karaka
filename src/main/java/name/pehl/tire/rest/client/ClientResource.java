package name.pehl.tire.rest.client;

import java.util.List;

import name.pehl.tire.model.Client;
import name.pehl.tire.rest.EntityIdFinder;
import name.pehl.tire.server.dao.ClientDao;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.google.inject.Inject;

/**
 * Supported methods:
 * <ul>
 * <li>GET /client/{id}: Get a client
 * <li>PUT /client/{id}: Update a client
 * <li>DELETE /client/{id[,id2,...]}: Delete client(s)
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-04 12:39:35 +0100 (Do, 04. Nov 2010) $ $Revision: 110
 *          $
 */
public class ClientResource extends ServerResource
{
    private final ClientDao dao;
    private final EntityIdFinder<Client> eif;


    @Inject
    public ClientResource(ClientDao dao, EntityIdFinder<Client> eif)
    {
        this.dao = dao;
        this.eif = eif;
    }


    @Get("json")
    public Representation getClient()
    {
        Client client = eif.findById(this, dao, (String) getRequestAttributes().get("id"));
        return null;
    }


    @Put
    public void update()
    {
        Client client = eif.findById(this, dao, (String) getRequestAttributes().get("id"));
    }


    @Delete
    public void remove()
    {
        List<Client> clients = eif.findByIds(this, dao, (String) getRequestAttributes().get("id"));
    }
}
