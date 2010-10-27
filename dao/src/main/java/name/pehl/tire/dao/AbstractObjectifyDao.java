package name.pehl.tire.dao;

import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class AbstractObjectifyDao<T> extends DAOBase
{
    static
    {
        // Register all your entity classes here
        // ObjectifyService.register(MyDomain.class);
        // ...
    }

    protected Class<T> clazz;


    /**
     * We've got to get the associated domain class somehow
     * 
     * @param clazz
     */
    protected AbstractObjectifyDao(Class<T> clazz)
    {
        this.clazz = clazz;
    }


    public Key<T> put(T entity)

    {
        return ofy().put(entity);
    }


    public Map<Key<T>, T> putAll(Iterable<T> entities)
    {
        return ofy().put(entities);
    }


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
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return q.get();
    }


    public List<T> listByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return q.list();
    }


    public List<Key<T>> listKeysByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return q.listKeys();
    }
}
