package name.pehl.tire.dao;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.Tag;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public class TagDao extends NamedEntityDao<Tag>
{
    @Inject
    public TagDao(User user, Normalizer normalizer)
    {
        super(Tag.class, user, normalizer);
    }
}
