package name.pehl.tire.server.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;

import name.pehl.tire.server.normalize.Normalizer;
import name.pehl.tire.server.normalize.TireNormalizer;
import name.pehl.tire.server.search.entity.IndexEntry;
import name.pehl.tire.server.search.entity.Searchable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class SearchDao extends DAOBase
{
    private final Normalizer normalizer;


    @Inject
    public SearchDao(@TireNormalizer Normalizer normalizer)
    {
        this.normalizer = normalizer;
    }


    @SuppressWarnings("unchecked")
    public <T extends Searchable> List<T> search(String query)
    {
        List<T> result = new ArrayList<T>();
        String normalized = normalizer.normalize(query);
        if (normalized != null)
        {
            Set<Key<?>> keys = new HashSet<Key<?>>();
            List<IndexEntry> indexEntries = ofy().query(IndexEntry.class).filter("data >=", normalized).list();
            for (IndexEntry indexEntry : indexEntries)
            {
                keys.add(indexEntry.getKey());
            }
            Map<Key<Object>, Object> resultMap = ofy().get(keys);
            for (Object entity : resultMap.values())
            {
                if (entity instanceof Searchable)
                {
                    result.add((T) entity);
                }
            }
        }
        return result;
    }
}
