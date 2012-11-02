package name.pehl.karaka.server.client.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import name.pehl.karaka.server.client.control.ClientConverter;
import name.pehl.karaka.server.client.control.ClientRepository;
import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.paging.entity.PageResult;

import org.jboss.resteasy.spi.NotFoundException;

/**
 * Supported methods:
 * <ul>
 * <li>GET /clients/: List all clients
 * <li>POST: Create a new client
 * <li>PUT /clients/{id}: Update an existing client
 * <li>DELETE /clients/{id}: Delete an existing client
 * </ul>
 * 
 * @todo implement ETag
 * @author $Author: harald.pehl $
 * @version $Date: 2011-05-16 12:54:26 +0200 (Mo, 16. Mai 2011) $ $Revision: 110
 *          $
 */
@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
public class ClientsResource
{
    @Inject ClientRepository repository;
    @Inject ClientConverter converter;


    @GET
    public List<name.pehl.karaka.shared.model.Client> list()
    {
        PageResult<Client> clients = repository.list();
        if (clients.isEmpty())
        {
            throw new NotFoundException("No clients found");
        }
        List<name.pehl.karaka.shared.model.Client> result = new ArrayList<name.pehl.karaka.shared.model.Client>();
        for (Client client : clients)
        {
            result.add(converter.toModel(client));
        }
        return result;
    }
}
