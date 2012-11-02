package name.pehl.karaka.server.tag.control;

import javax.inject.Inject;

import name.pehl.karaka.server.search.IndexName;
import name.pehl.karaka.server.search.IndexSearch;
import name.pehl.karaka.server.search.NamedEntityIndexSearch;
import name.pehl.karaka.server.tag.entity.Tag;

import com.google.appengine.api.search.Index;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagIndexSearch extends NamedEntityIndexSearch<Tag> implements IndexSearch<Tag>
{
    @Inject @IndexName("tag") Index index;


    @Override
    protected Index getIndex()
    {
        return index;
    }
}
