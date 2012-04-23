package name.pehl.tire.server.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.client.entity.Client;
import name.pehl.tire.server.entity.BaseEntity;
import name.pehl.tire.server.normalizer.Normalizer;
import name.pehl.tire.server.paging.entity.PageInfo;
import name.pehl.tire.server.paging.entity.PageResult;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.search.entity.IndexEntry;
import name.pehl.tire.server.search.entity.Searchable;
import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.server.tag.entity.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.util.DAOBase;

/**
 * Base class for all entites used in TiRe. There's also support for the
 * following interfaces
 * <ul>
 * <li> {@link Searchable}
 * </ul>
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class BaseEntityRepository<T extends BaseEntity> extends DAOBase
{

    // ------------------------------------------------------ register entities

    static
    {
        // Register all entity classes here
        ObjectifyService.register(Activity.class);
        ObjectifyService.register(Client.class);
        ObjectifyService.register(IndexEntry.class);
        ObjectifyService.register(Project.class);
        ObjectifyService.register(Settings.class);
        ObjectifyService.register(Tag.class);
    }

    // -------------------------------------------------------- private members

    protected final Logger logger;
    protected final Class<T> clazz;
    protected final Normalizer normalizer;


    // ----------------------------------------------------------- constructors

    protected BaseEntityRepository(Class<T> clazz, Normalizer normalizer)
    {
        super();
        this.logger = LoggerFactory.getLogger(getClass());
        this.clazz = clazz;
        this.normalizer = normalizer;
    }


    // ------------------------------------------------------------ put methods

    public Key<T> put(T entity)
    {
        Key<T> key = ofy().put(entity);
        index(key, entity);
        return key;
    }


    public Map<Key<T>, T> putAll(Iterable<T> entities)
    {
        Map<Key<T>, T> keysAndEntities = ofy().put(entities);
        index(keysAndEntities);
        return keysAndEntities;
    }


    // --------------------------------------------------------- delete methods

    public void delete(T entity)
    {
        ofy().delete(entity);
        unIndex(new Key<T>(clazz, entity.getId()));
    }


    public void deleteKey(Key<T> key)
    {
        ofy().delete(key);
        unIndex(key);
    }


    public void deleteAll(Iterable<T> entities)
    {
        ofy().delete(entities);
        Set<Key<T>> keys = new HashSet<Key<T>>();
        for (T entity : entities)
        {
            keys.add(new Key<T>(clazz, entity.getId()));
        }
        unIndex(keys);
    }


    public void deleteKeys(Iterable<Key<T>> keys)
    {
        ofy().delete(keys);
        unIndex(keys);
    }


    // ------------------------------------------------------------- get entity

    public T get(Long id)
    {
        return ofy().get(clazz, id);
    }


    public T get(Key<T> key)
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
        return query().filter(propName, propValue).get();
    }


    // ---------------------------------------------------------- find entities

    public PageResult<T> list()
    {
        return list(null);
    }


    public PageResult<T> list(PageInfo pageInfo)
    {
        return pageResultFor(query(), pageInfo);
    }


    public PageResult<T> findByProperty(String propName, Object propValue)
    {
        return findByProperty(propName, propValue, null);
    }


    public PageResult<T> findByProperty(String propName, Object propValue, PageInfo pageInfo)
    {
        return pageResultFor(query().filter(propName, propValue), pageInfo);
    }


    // ---------------------------------------------------------- index methods

    private IndexEntry createIndexEntry(Key<T> key, T entity)
    {
        IndexEntry indexEntry = null;
        if (entity instanceof Searchable)
        {
            String normalized = normalizer.normalize(((Searchable) entity).getSearchData());
            if (normalized != null)
            {
                indexEntry = new IndexEntry(key, normalized);
            }
        }
        return indexEntry;
    }


    private List<IndexEntry> createIndexEntries(Map<Key<T>, T> keysAndEntities)
    {
        List<IndexEntry> indexEntries = new ArrayList<IndexEntry>();
        for (Map.Entry<Key<T>, T> entry : keysAndEntities.entrySet())
        {
            IndexEntry indexEntry = createIndexEntry(entry.getKey(), entry.getValue());
            if (indexEntry != null)
            {
                indexEntries.add(indexEntry);
            }
        }
        return indexEntries;
    }


    private void index(Key<T> key, T entity)
    {
        unIndex(key);
        IndexEntry indexEntry = createIndexEntry(key, entity);
        if (indexEntry != null)
        {
            ofy().put(indexEntry);
        }
    }


    private void index(Map<Key<T>, T> keysAndEntities)
    {
        unIndex(keysAndEntities.keySet());
        List<IndexEntry> indexEntries = createIndexEntries(keysAndEntities);
        if (!indexEntries.isEmpty())
        {
            ofy().put(indexEntries);
        }
    }


    private void unIndex(Key<T> key)
    {
        List<Key<IndexEntry>> indexEntryKeys = ofy().query(IndexEntry.class).filter("key", key).listKeys();
        ofy().delete(indexEntryKeys);
    }


    private void unIndex(Iterable<Key<T>> keys)
    {
        List<Key<IndexEntry>> indexEntryKeys = ofy().query(IndexEntry.class).filter("key IN", keys).listKeys();
        ofy().delete(indexEntryKeys);
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
