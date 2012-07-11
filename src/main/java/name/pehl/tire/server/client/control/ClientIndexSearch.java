package name.pehl.tire.server.client.control;

import javax.inject.Inject;

import name.pehl.tire.server.client.entity.Client;
import name.pehl.tire.server.search.DescriptiveEntityIndexSearch;
import name.pehl.tire.server.search.IndexName;
import name.pehl.tire.server.search.IndexSearch;

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
