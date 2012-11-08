package name.pehl.karaka.server.activity.control;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.googlecode.objectify.Key;
import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.search.DescriptiveEntityIndexSearch;
import name.pehl.karaka.server.search.IndexName;
import name.pehl.karaka.server.search.IndexSearch;
import name.pehl.karaka.server.tag.control.TagRepository;
import name.pehl.karaka.server.tag.entity.Tag;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivityIndexSearch extends DescriptiveEntityIndexSearch<Activity> implements IndexSearch<Activity>
{
    @Inject @IndexName("activity") Index index;
    @Inject TagRepository tagRepository;


    @Override
    protected Index getIndex()
    {
        return index;
    }


    @Override
    protected Document.Builder documentBuilderFor(Activity entity)
    {
        Document.Builder builder = super.documentBuilderFor(entity);
        List<Key<Tag>> tags = entity.getTags();
        if (!tags.isEmpty())
        {
            StringBuilder tagsAsString = new StringBuilder();
            for (Iterator<Key<Tag>> iterator = tags.iterator(); iterator.hasNext(); )
            {
                Key<Tag> key = iterator.next();
                Tag tag = tagRepository.get(key);
                tagsAsString.append(tag.getName());
                if (iterator.hasNext())
                {
                    tagsAsString.append(" ");
                }
            }
            builder = builder.addField(Field.newBuilder().setName("tags").setText(tagsAsString.toString()));
        }
        return builder;
    }
}
