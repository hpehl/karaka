package name.pehl.karaka.server.search;

import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class IndexProducer
{
    @Produces
    @IndexName("")
    public Index produceIndex(InjectionPoint ip)
    {
        IndexName indexName = ip.getAnnotated().getAnnotation(IndexName.class);
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName.value() + indexName.version()).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }
}
