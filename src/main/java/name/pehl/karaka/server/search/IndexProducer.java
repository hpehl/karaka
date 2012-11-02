package name.pehl.karaka.server.search;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.google.appengine.api.search.Consistency;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;

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
        IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName.value())
                .setConsistency(Consistency.PER_DOCUMENT).build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }
}
