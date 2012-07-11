package name.pehl.tire.server.repository;

import java.util.List;
import java.util.Map;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.client.entity.Client;
import name.pehl.tire.server.entity.BaseEntity;
import name.pehl.tire.server.paging.entity.PageInfo;
import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.search.IndexSearch;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.server.tag.entity.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

/**
 * Base class for all entites used in TiRe.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class BaseEntityRepository<T extends BaseEntity> extends DAOBase implements Repository<T>
{
    // ------------------------------------------------------ register entities

    static
    {
        // Register all entity classes here
        ObjectifyService.register(Activity.class);
        ObjectifyService.register(Client.class);
        ObjectifyService.register(Project.class);
        ObjectifyService.register(Settings.class);
        ObjectifyService.register(Tag.class);
    }

    // -------------------------------------------------------- private members

    protected final Logger logger;
    protected final Class<T> clazz;
    protected final IndexSearch<T> indexSearch;


    // ----------------------------------------------------------- constructors

    protected BaseEntityRepository(Class<T> clazz, IndexSearch<T> indexSearch)
    {
        super();
        this.logger = LoggerFactory.getLogger(getClass());
        this.clazz = clazz;
        this.indexSearch = indexSearch;
    }


    // ------------------------------------------------------------ put methods

    @Override
    @SuppressWarnings("unchecked")
    public Key<T> put(T entity)
    {
        Key<T> key = ofy().put(entity);
        index(entity);
        return key;
    }


    @Override
    public Map<Key<T>, T> putAll(Iterable<T> entities)
    {
        Map<Key<T>, T> keysAndEntities = ofy().put(entities);
        index(entities);
        return keysAndEntities;
    }


    // --------------------------------------------------------- delete methods

    @Override
    @SuppressWarnings("unchecked")
    public void delete(T entity)
    {
        ofy().delete(entity);
        unIndex(entity);
    }


    @Override
    public void deleteAll(Iterable<T> entities)
    {
        ofy().delete(entities);
        unIndex(entities);
    }


    // ------------------------------------------------------------- get entity

    @Override
    public T get(Long id)
    {
        return ofy().get(clazz, id);
    }


    @Override
    public T get(Key<T> key)
    {
        return ofy().get(key);
    }


    // ---------------------------------------------------------- find entities

    @Override
    public PageResult<T> list()
    {
        return list(null);
    }


    @Override
    public PageResult<T> list(PageInfo pageInfo)
    {
        return pageResultFor(query(), pageInfo);
    }


    @Override
    public PageResult<T> findByProperty(String propName, Object propValue)
    {
        return findByProperty(propName, propValue, null);
    }


    @Override
    public PageResult<T> findByProperty(String propName, Object propValue, PageInfo pageInfo)
    {
        return pageResultFor(query().filter(propName, propValue), pageInfo);
    }


    // ---------------------------------------------------------- index methods

    protected void index(T... entities)
    {
        if (indexSearch != null)
        {
            indexSearch.index(entities);
        }
    }


    protected void index(Iterable<T> entities)
    {
        if (indexSearch != null)
        {
            indexSearch.index(entities);
        }
    }


    protected void unIndex(T... entities)
    {
        if (indexSearch != null)
        {
            indexSearch.unIndex(entities);
        }
    }


    protected void unIndex(Iterable<T> entities)
    {
        if (indexSearch != null)
        {
            indexSearch.unIndex(entities);
        }
    }


    // --------------------------------------------------------- helper methods

    protected Query<T> query()
    {
        return ofy().query(clazz);
    }


    protected PageResult<T> pageResultFor(Query<T> q, PageInfo pageInfo)
    {
        if (pageInfo != null)
        {
            int total = q.count();
            List<T> page = q.offset(pageInfo.getOffset()).limit(pageInfo.getPageSize()).list();
            return new PageResult<T>(pageInfo, page, total);
        }
        return new PageResult<T>(new PageInfo(0, PageInfo.MAX_PAGE_SIZE), q.list());
    }
}
