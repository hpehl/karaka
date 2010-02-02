package name.pehl.tire.server.persistence;

import java.util.Iterator;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

public class ObjectifyDao<T> extends DAOBase
{
    private Class<T> clazz;


    /**
     * We've got to get the associated domain class somehow
     * 
     * @param clazz
     */
    protected ObjectifyDao(Class<T> clazz)
    {
        this.clazz = clazz;
    }


    public Key<T> put(T entity)
    {
        Key<T> key = ofy().put(entity);
        return key;
    }


    public T get(Long id) throws EntityNotFoundException
    {
        T obj = ofy().get(this.clazz, id);
        return obj;
    }


    public T getByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return q.get();
    }


    public Iterator<T> list()
    {
        Query<T> q = ofy().query(clazz);
        return q.iterator();
    }


    public Iterator<T> listByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return q.iterator();
    }


    public void delete(T entity)
    {
        ofy().delete(entity);
    }


    public void delete(Key<T> entityKey)
    {
        ofy().delete(entityKey);
    }

}
