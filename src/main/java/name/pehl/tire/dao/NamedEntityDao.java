package name.pehl.tire.dao;

import name.pehl.tire.dao.normalize.Normalizer;
import name.pehl.tire.model.NamedEntity;
import name.pehl.tire.rest.paging.PageResult;

import com.google.appengine.api.users.User;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 17:01:54 +0100 (Mi, 01. Dez 2010) $ $Revision: 161
 *          $
 */
public abstract class NamedEntityDao<T extends NamedEntity> extends BaseEntityDao<T>
{
    protected NamedEntityDao(Class<T> clazz, User user, Normalizer normalizer)
    {
        super(clazz, user, normalizer);
    }


    public PageResult<T> findByName(String name)
    {
        return findByProperty("name >=", name.toLowerCase());
    }
}
