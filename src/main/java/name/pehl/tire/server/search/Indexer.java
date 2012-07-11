package name.pehl.tire.server.search;

import com.google.appengine.api.search.AddResponse;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface Indexer<T>
{
    AddResponse index(T entity);


    void unIndex(T entity);
}
