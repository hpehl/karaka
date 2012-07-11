package name.pehl.tire.server.tag.control;

import javax.inject.Inject;

import name.pehl.tire.server.search.IndexName;
import name.pehl.tire.server.search.IndexSearch;
import name.pehl.tire.server.search.NamedEntityIndexSearch;
import name.pehl.tire.server.tag.entity.Tag;

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
