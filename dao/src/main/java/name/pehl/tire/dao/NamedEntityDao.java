package name.pehl.tire.dao;

import name.pehl.taoki.paging.PageResult;
import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.NamedEntity;

import com.google.appengine.api.users.User;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
public abstract class NamedEntityDao<T extends NamedEntity> extends BaseEntityDao<T>
{
    protected NamedEntityDao(Class<T> clazz, User user, Normalizer normalizer)
    {
        super(clazz, user, normalizer);
    }


    public PageResult<T> listByName(String name)
    {
        return listByProperty("name >=", name.toLowerCase());
    }
}
