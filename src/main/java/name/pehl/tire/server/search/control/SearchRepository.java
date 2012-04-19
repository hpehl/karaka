package name.pehl.tire.server.search.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import name.pehl.tire.server.normalizer.Normalizer;
import name.pehl.tire.server.normalizer.TireNormalizer;
import name.pehl.tire.server.search.entity.IndexEntry;
import name.pehl.tire.server.search.entity.Searchable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.util.DAOBase;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class SearchRepository extends DAOBase
{
    private final Normalizer normalizer;


    @Inject
    public SearchRepository(@TireNormalizer Normalizer normalizer)
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
