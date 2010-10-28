package name.pehl.tire.dao;

import java.util.List;

import name.pehl.tire.model.HasUser;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Query;

/**
 * DAO for entites which implement {@link HasUser}. The query methods add a
 * filter for the current user, which must be specified as constructor
 * parameter.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class HasUserDao<T extends HasUser> extends AbstractObjectifyDao<T>
{
    protected final User user;


    /**
     * We've got to get the associated domain class somehow
     * 
     * @param clazz
     */
    protected HasUserDao(Class<T> clazz, User user)
    {
        super(clazz);
        this.user = user;
    }


    /**
     * {@inheritDoc}
     * <p>
     * Adds a filter for the current user.
     * 
     * @param propName
     * @param propValue
     * @return T matching Object
     */
    @Override
    public T getByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        addUserFilter(q);
        return q.get();
    }


    /**
     * {@inheritDoc}
     * <p>
     * Adds a filter for the current user.
     * 
     * @param propName
     * @param propValue
     * @return
     * @see name.pehl.tire.dao.AbstractObjectifyDao#listByProperty(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public List<T> listByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        addUserFilter(q);
        return q.list();
    }


    /**
     * {@inheritDoc}
     * <p>
     * Adds a filter for the current user.
     * 
     * @param propName
     * @param propValue
     * @return
     * @see name.pehl.tire.dao.AbstractObjectifyDao#listKeysByProperty(java.lang.String,
     *      java.lang.Object)
     */
    @Override
    public List<Key<T>> listKeysByProperty(String propName, Object propValue)
    {
        Query<T> q = ofy().query(clazz);
        q.filter(propName, propValue);
        addUserFilter(q);
        return q.listKeys();
    }


    /**
     * Adds a filter for the current user.
     * 
     * @param q
     */
    private void addUserFilter(Query<T> q)
    {
        q.filter("user", user);
    }
}
