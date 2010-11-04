package name.pehl.tire.dao;

import name.pehl.tire.model.Tag;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class TagDao extends HasUserDao<Tag>
{
    @Inject
    public TagDao(User user)
    {
        super(Tag.class, user);
    }
}
