package name.pehl.tire.server.activity.control;

import javax.inject.Inject;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.server.searching.IndexName;
import name.pehl.tire.server.searching.Indexer;

import com.google.appengine.api.search.AddResponse;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityIndexer implements Indexer<Activity>
{
    @Inject @IndexName("activity") Index index;


    @Override
    public AddResponse index(Activity activity)
    {
        Builder builder = Document.newBuilder().setId(String.valueOf(activity.getId()))
                .addField(Field.newBuilder().setName("name").setText(activity.getName()))
                .addField(Field.newBuilder().setName("description").setText(activity.getDescription()));
        return null;
    }


    @Override
    public void unIndex(Activity entity)
    {
    }

}
