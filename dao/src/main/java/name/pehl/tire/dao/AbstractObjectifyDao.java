package name.pehl.tire.dao;

import java.util.List;
import java.util.Map;

import name.pehl.taoki.paging.PageInfo;
import name.pehl.taoki.paging.PageResult;
import name.pehl.tire.model.Activity;
import name.pehl.tire.model.Client;
import name.pehl.tire.model.Project;
import name.pehl.tire.model.Tag;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class AbstractObjectifyDao<T> extends DAOBase
{
    // ------------------------------------------------------ register entities

    static
    {
        // Register all entity classes here
        ObjectifyService.register(Activity.class);
        ObjectifyService.register(Client.class);
        ObjectifyService.register(Project.class);
        ObjectifyService.register(Tag.class);
    }

    // -------------------------------------------------------- private members

    protected final Class<T> clazz;


    // ----------------------------------------------------------- constructors

    /**
     * We've got to get the associated domain class somehow
     * 
     * @param clazz
     */
    protected AbstractObjectifyDao(Class<T> clazz)
    {
        this.clazz = clazz;
    }


    // ------------------------------------------------------------ put methods

    public Key<T> put(T entity)
    {
        return ofy().put(entity);
    }


    public Map<Key<T>, T> putAll(Iterable<T> entities)
    {
        return ofy().put(entities);
    }


    // --------------------------------------------------------- delete methods

    public void delete(T entity)
    {
        ofy().delete(entity);
    }


    public void deleteKey(Key<T> entityKey)
    {
        ofy().delete(entityKey);
    }


    public void deleteAll(Iterable<T> entities)
    {
        ofy().delete(entities);
    }


    public void deleteKeys(Iterable<Key<T>> keys)
    {
        ofy().delete(keys);
    }


    // ------------------------------------------------------------- get entity

    public T get(Long id) throws EntityNotFoundException
    {
        return ofy().get(this.clazz, id);
    }


    public T get(Key<T> key) throws EntityNotFoundException
    {
        return ofy().get(key);
    }


    /**
     * Convenience method to get all objects matching a single property
     * 
     * @param propName
     * @param propValue
     * @return T matching Object
     */
    public T getByProperty(String propName, Object propValue)
    {
        return ofy().query(clazz).filter(propName, propValue).get();
    }


    // ---------------------------------------------------------- find entities

    public PageResult<T> list()
    {
        return list(null);
    }


    public PageResult<T> list(PageInfo pageInfo)
    {
        Query<T> q = ofy().query(clazz);
        return buildPageResult(q, pageInfo);
    }


    public PageResult<T> listByProperty(String propName, Object propValue)
    {
        return listByProperty(propName, propValue, null);
    }


    public PageResult<T> listByProperty(String propName, Object propValue, PageInfo pageInfo)
    {
        Query<T> q = ofy().query(clazz).filter(propName, propValue);
        return buildPageResult(q, pageInfo);
    }


    // --------------------------------------------------------- helper methods

    protected PageResult<T> buildPageResult(Query<T> q, PageInfo pageInfo)
    {
        if (pageInfo != null)
        {
            int total = q.count();
            List<T> page = q.offset(pageInfo.getOffset()).limit(pageInfo.getPageSize()).list();
            return new PageResult<T>(pageInfo, page, total);
        }
        return new PageResult<T>(pageInfo, q.list());
    }
}
