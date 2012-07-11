package name.pehl.tire.server.search;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.server.entity.BaseEntity;

import com.google.appengine.api.search.AddResponse;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class BaseEntityIndexSearch<T extends BaseEntity> implements IndexSearch<T>
{
    static final int RESULT_LIMIT = 15;


    @Override
    public AddResponse index(T... entities)
    {
        if (entities != null && entities.length != 0)
        {
            List<Document> documents = new ArrayList<Document>();
            for (T entity : entities)
            {
                documents.add(documentBuilderFor(entity).build());
            }
            return getIndex().add(documents);
        }
        return null;
    }


    @Override
    public AddResponse index(Iterable<T> entities)
    {
        if (entities != null)
        {
            List<Document> documents = new ArrayList<Document>();
            for (T entity : entities)
            {
                documents.add(documentBuilderFor(entity).build());
            }
            return getIndex().add(documents);
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
            getIndex().remove(documentIds);
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
            getIndex().remove(documentIds);
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
