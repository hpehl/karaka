package name.pehl.karaka.server.repository;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;
import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.entity.BaseEntity;
import name.pehl.karaka.server.project.entity.Project;
import name.pehl.karaka.server.search.IndexSearch;
import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.tag.entity.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Base class for all entites used in Karaka.
 *
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class Repository<T extends BaseEntity>
{
    static
    {
        // Register all entity classes here
        ObjectifyService.register(Activity.class);
        ObjectifyService.register(Client.class);
        ObjectifyService.register(Project.class);
        ObjectifyService.register(Settings.class);
        ObjectifyService.register(Tag.class);
    }

    protected final Logger logger;
    protected final Class<T> clazz;
    protected final IndexSearch<T> indexSearch;


    protected Repository(Class<T> clazz, IndexSearch<T> indexSearch)
    {
        super();
        this.logger = LoggerFactory.getLogger(getClass());
        this.clazz = clazz;
        this.indexSearch = indexSearch;
    }

    public T get(Key<T> key)
    {
        return ofy().load().key(key).safeGet();
    }

    public T get(Long id)
    {
        return ofy().load().type(clazz).id(id).safeGet();
    }

    public List<T> list()
    {
        return ofy().load().type(clazz).list();
    }

    public Query<T> query()
    {
        return ofy().load().type(clazz);
    }

    @SuppressWarnings("unchecked")
    public T save(T entity)
    {
        Key<T> key = ofy().save().entity(entity).now();
        T saved = get(key);
        index(saved);
        return saved;
    }

    @SuppressWarnings("unchecked")
    public Collection<T> saveAll(Iterable<T> entities)
    {
        Map<Key<T>, T> keysAndEntities = ofy().save().<T>entities().now();
        Collection<T> savedEntities = keysAndEntities.values();
        index(savedEntities);
        return savedEntities;
    }

    @SuppressWarnings("unchecked")
    public void delete(T entity)
    {
        ofy().delete().entity(entity);
        unIndex(entity);
    }

    public void deleteAll(Iterable<T> entities)
    {
        ofy().delete().entities(entities);
        unIndex(entities);
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
}
