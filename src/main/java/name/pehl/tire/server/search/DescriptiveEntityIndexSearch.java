package name.pehl.tire.server.search;

import name.pehl.tire.server.entity.DescriptiveEntity;

import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class DescriptiveEntityIndexSearch<T extends DescriptiveEntity> extends NamedEntityIndexSearch<T>
        implements IndexSearch<T>
{
    @Override
    protected Builder documentBuilderFor(T entity)
    {
        Builder builder = super.documentBuilderFor(entity);
        if (entity.getDescription() != null)
        {
            builder = builder.addField(Field.newBuilder().setName("description").setText(entity.getDescription()));
        }
        return builder;
    }
}
