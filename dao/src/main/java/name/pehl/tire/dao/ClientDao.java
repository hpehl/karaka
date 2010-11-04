package name.pehl.tire.dao;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.Client;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class ClientDao extends NamedEntityDao<Client>
{
    @Inject
    public ClientDao(User user, Normalizer normalizer)
    {
        super(Client.class, user, normalizer);
    }
}
