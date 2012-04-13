package name.pehl.tire.server.client.control;

import com.google.inject.Inject;

import name.pehl.tire.server.client.entity.Client;
import name.pehl.tire.server.normalize.Normalizer;
import name.pehl.tire.server.normalize.TireNormalizer;
import name.pehl.tire.server.repository.NamedEntityRepository;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class ClientRepository extends NamedEntityRepository<Client>
{
    @Inject
    public ClientRepository(@TireNormalizer Normalizer normalizer)
    {
        super(Client.class, normalizer);
    }
}
