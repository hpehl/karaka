package name.pehl.karaka.server.search;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.PutResponse;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import name.pehl.karaka.server.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class BaseEntityIndexSearch<T extends BaseEntity> implements IndexSearch<T>
{
    static final int RESULT_LIMIT = 15;


    @Override
    public PutResponse index(T... entities)
    {
        if (entities != null && entities.length != 0)
        {
            List<Document> documents = new ArrayList<Document>();
            for (T entity : entities)
            {
                documents.add(documentBuilderFor(entity).build());
            }
            return getIndex().put(documents);
        }
        return null;
    }


    @Override
    public PutResponse index(Iterable<T> entities)
    {
        if (entities != null)
        {
            List<Document> documents = new ArrayList<Document>();
            for (T entity : entities)
            {
                documents.add(documentBuilderFor(entity).build());
            }
            return getIndex().put(documents);
        }
        return null;
    }


    @Override
    public void unIndex(T... entities)
    {
        if (entities != null && entities.length != 0)
        {
            List<String> documentIds = new ArrayList<String>();
            for (T entity : entities)
            {
                if (!entity.isTransient())
                {
                    documentIds.add(String.valueOf(entity.getId()));
                }
            }
            getIndex().delete(documentIds);
        }
    }


    @Override
    public void unIndex(Iterable<T> entities)
    {
        if (entities != null)
        {
            List<String> documentIds = new ArrayList<String>();
            for (T entity : entities)
            {
                if (!entity.isTransient())
                {
                    documentIds.add(String.valueOf(entity.getId()));
                }
            }
            getIndex().delete(documentIds);
        }
    }


    @Override
    public Results<ScoredDocument> search(String queryString)
    {
        Query query = Query.newBuilder().setOptions(queryOptionsBuilder()).build(queryString);
        return getIndex().search(query);
    }


    protected Document.Builder documentBuilderFor(T entity)
    {
        return Document.newBuilder().setId(String.valueOf(entity.getId()));
    }


    protected QueryOptions.Builder queryOptionsBuilder()
    {
        QueryOptions.Builder builder = QueryOptions.newBuilder().setLimit(RESULT_LIMIT);
        return builder;
    }


    protected abstract Index getIndex();
}
