package name.pehl.tire.server.repository;

import java.util.Map;

import name.pehl.tire.server.paging.entity.PageInfo;
import name.pehl.tire.server.paging.entity.PageResult;

import com.googlecode.objectify.Key;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public interface Repository<T>
{
    Key<T> put(T entity);


    Map<Key<T>, T> putAll(Iterable<T> entities);


    void delete(T entity);


    void deleteAll(Iterable<T> entities);


    T get(Long id);


    T get(Key<T> key);


    PageResult<T> list();


    PageResult<T> list(PageInfo pageInfo);


    PageResult<T> findByProperty(String propName, Object propValue);


    PageResult<T> findByProperty(String propName, Object propValue, PageInfo pageInfo);
}
