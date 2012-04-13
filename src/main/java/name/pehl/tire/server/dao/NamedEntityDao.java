package name.pehl.tire.server.dao;

import name.pehl.tire.server.model.NamedEntity;
import name.pehl.tire.server.normalize.Normalizer;
import name.pehl.tire.server.rest.paging.PageResult;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 17:01:54 +0100 (Mi, 01. Dez 2010) $ $Revision: 161
 *          $
 */
public abstract class NamedEntityDao<T extends NamedEntity> extends BaseEntityDao<T>
{
    protected NamedEntityDao(Class<T> clazz, Normalizer normalizer)
    {
        super(clazz, normalizer);
    }


    public PageResult<T> findByName(String name)
    {
        return findByProperty("name >=", name.toLowerCase());
    }
}
