package name.pehl.tire.server.repository;

import name.pehl.tire.server.entity.NamedEntity;
import name.pehl.tire.server.paging.entity.PageResult;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 17:01:54 +0100 (Mi, 01. Dez 2010) $ $Revision: 161
 *          $
 */
public abstract class NamedEntityRepository<T extends NamedEntity> extends BaseEntityRepository<T>
{
    protected NamedEntityRepository(Class<T> clazz)
    {
        super(clazz);
    }


    public PageResult<T> findByName(String name)
    {
        return findByProperty("name >=", name.toLowerCase());
    }
}
