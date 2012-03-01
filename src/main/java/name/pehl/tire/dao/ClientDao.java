package name.pehl.tire.dao;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.Client;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-05 00:40:48 +0100 (Fr, 05. Nov 2010) $ $Revision: 121
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
