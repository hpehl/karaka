package name.pehl.tire.server.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Transient;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.helper.DAOBase;

public class ObjectifyDao<T> extends DAOBase
{
    static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

    protected Class<T> clazz;


    // ----------------------------------------------------------- constructors

    /**
     * We've got to get the associated domain class somehow
     * 
     * @param clazz
     */
    protected ObjectifyDao(Class<T> clazz)
    {
        this.clazz = clazz;
    }


    // ------------------------------------------------------ get single entity

    public T get(Long id) throws EntityNotFoundException
    {
        return ofy().get(clazz, id);
    }


    public T get(Key<T> key) throws EntityNotFoundException
    {
        return ofy().get(key);
    }


    public T getByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return q.get();
    }


    public T getByExample(T exampleObj)
    {
        Query<T> queryByExample = buildQueryByExample(exampleObj);
        Iterable<T> iterableResults = queryByExample.fetch();
        Iterator<T> i = iterableResults.iterator();
        T obj = i.next();
        if (i.hasNext())
            throw new RuntimeException("Too many results");
        return obj;
    }


    // ------------------------------------------- get multiple entities / keys

    public List<T> list()
    {
        Query<T> q = ofy().query(clazz);
        return asList(q.fetch());
    }


    public List<Key<T>> listKeys()
    {
        Query<T> q = ofy().query(clazz);
        return asKeyList(q.fetchKeys());
    }


    public List<T> listByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return asList(q.fetch());
    }


    public List<Key<T>> listKeysByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        return asKeyList(q.fetchKeys());
    }


    public List<T> listByExample(T exampleObj)
    {
        Query<T> queryByExample = buildQueryByExample(exampleObj);
        return asList(queryByExample.fetch());
    }


    // ----------------------------------------------------------- put / delete

    public Key<T> put(T entity)
    {
        return ofy().put(entity);
    }


    @SuppressWarnings("unchecked")
    public List<Key<T>> putAll(Iterable<T> entities)
    {
        return (List<Key<T>>) ofy().put(entities);
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


    // --------------------------------------------------------- helper methods

    private List<T> asList(Iterable<T> iterable)
    {
        ArrayList<T> list = new ArrayList<T>();
        for (T t : iterable)
        {
            list.add(t);
        }
        return list;
    }


    private List<Key<T>> asKeyList(Iterable<Key<T>> iterableKeys)
    {
        ArrayList<Key<T>> keys = new ArrayList<Key<T>>();
        for (Key<T> key : iterableKeys)
        {
            keys.add(key);
        }
        return keys;
    }


    private Query<T> buildQueryByExample(T exampleObj)
    {
        Query<T> q = ofy().query(clazz);

        // Add all non-null properties to query filter
        for (Field field : clazz.getDeclaredFields())
        {
            // Ignore transient, embedded, array, and collection properties
            if (field.isAnnotationPresent(Transient.class) || (field.isAnnotationPresent(Embedded.class))
                    || (field.getType().isArray()) || (Collection.class.isAssignableFrom(field.getType()))
                    || ((field.getModifiers() & BAD_MODIFIERS) != 0))
                continue;

            field.setAccessible(true);

            Object value;
            try
            {
                value = field.get(exampleObj);
            }
            catch (IllegalArgumentException e)
            {
                throw new RuntimeException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
            if (value != null)
            {
                q.filter(field.getName(), value);
            }
        }

        return q;
    }
}
