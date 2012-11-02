package name.pehl.karaka.server.search;

import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface IndexSearch<T>
{
    PutResponse index(T... entities);


    PutResponse index(Iterable<T> entities);


    void unIndex(T... entities);


    void unIndex(Iterable<T> entities);


    Results<ScoredDocument> search(String query);
}
