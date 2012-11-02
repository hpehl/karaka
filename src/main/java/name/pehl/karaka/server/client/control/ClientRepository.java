package name.pehl.karaka.server.client.control;

import javax.inject.Inject;

import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.repository.NamedEntityRepository;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class ClientRepository extends NamedEntityRepository<Client>
{
    @Inject
    public ClientRepository(ClientIndexSearch indexer)
    {
        super(Client.class, indexer);
    }
}
