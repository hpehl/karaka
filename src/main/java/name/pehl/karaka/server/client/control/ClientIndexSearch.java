package name.pehl.karaka.server.client.control;

import javax.inject.Inject;

import name.pehl.karaka.server.client.entity.Client;
import name.pehl.karaka.server.search.DescriptiveEntityIndexSearch;
import name.pehl.karaka.server.search.IndexName;
import name.pehl.karaka.server.search.IndexSearch;

import com.google.appengine.api.search.Index;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ClientIndexSearch extends DescriptiveEntityIndexSearch<Client> implements IndexSearch<Client>
{
    @Inject @IndexName("client") Index index;


    @Override
    protected Index getIndex()
    {
        return index;
    }
}
