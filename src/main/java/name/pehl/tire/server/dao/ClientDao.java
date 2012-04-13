package name.pehl.tire.server.dao;

import com.google.inject.Inject;

import name.pehl.tire.server.model.Client;
import name.pehl.tire.server.normalize.Normalizer;
import name.pehl.tire.server.normalize.TireNormalizer;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
 *          $
 */
public class ClientDao extends NamedEntityDao<Client>
{
    @Inject
    public ClientDao(@TireNormalizer Normalizer normalizer)
    {
        super(Client.class, normalizer);
    }
}
