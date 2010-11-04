package name.pehl.tire.dao;

import name.pehl.taoki.paging.PageInfo;
import name.pehl.taoki.paging.PageResult;
import name.pehl.tire.model.HasUser;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Query;

/**
 * DAO for entites which implement {@link HasUser}. The query methods add a
 * filter for the current user, which must be specified as constructor
 * parameter.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 121
 *          $
 */
public abstract class HasUserDao<T extends HasUser> extends AbstractObjectifyDao<T>
{
    // -------------------------------------------------------- private members

    protected final User user;


    // ----------------------------------------------------------- constructors

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


    // ------------------------------------------------------------- get entity

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
        return addUserFilter(q.filter(propName, propValue)).get();
    }


    // ---------------------------------------------------------- find entities

    /**
     * @param pageInfo
     * @return
     * @see name.pehl.tire.dao.AbstractObjectifyDao#list(name.pehl.taoki.paging.PageInfo)
     */
    @Override
    public PageResult<T> list(PageInfo pageInfo)
    {
        Query<T> q = addUserFilter(ofy().query(clazz));
        return buildPageResult(q, pageInfo);
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
    public PageResult<T> listByProperty(String propName, Object propValue, PageInfo pageInfo)
    {
        Query<T> q = addUserFilter(ofy().query(clazz).filter(propName, propValue));
        return buildPageResult(q, pageInfo);
    }


    // --------------------------------------------------------- helper methods

    /**
     * Adds a filter for the current user.
     * 
     * @param q
     */
    protected Query<T> addUserFilter(Query<T> q)
    {
        return q.filter("user", user);
    }
}
