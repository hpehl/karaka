package name.pehl.tire.dao;

import name.pehl.tire.model.Client;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class ClientDao extends HasUserDao<Client>
{
    @Inject
    public ClientDao(User user)
    {
        super(Client.class, user);
    }
}
