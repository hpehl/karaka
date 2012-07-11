package name.pehl.tire.server.search;

import java.util.List;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface Searcher<T>
{
    List<T> search(String query);
}
