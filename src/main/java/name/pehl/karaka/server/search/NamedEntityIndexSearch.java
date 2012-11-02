package name.pehl.karaka.server.search;

import name.pehl.karaka.server.entity.NamedEntity;

import com.google.appengine.api.search.Document.Builder;
import com.google.appengine.api.search.Field;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class NamedEntityIndexSearch<T extends NamedEntity> extends BaseEntityIndexSearch<T> implements IndexSearch<T>
{
    @Override
    protected Builder documentBuilderFor(T entity)
    {
        return super.documentBuilderFor(entity).addField(Field.newBuilder().setName("name").setText(entity.getName()));
    }
}
