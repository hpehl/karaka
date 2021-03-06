package name.pehl.karaka.client.cell;

import name.pehl.karaka.shared.model.BaseModel;

import com.google.gwt.view.client.ProvidesKey;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ModelKeyProvider<T extends BaseModel> implements ProvidesKey<T>
{
    @Override
    public Object getKey(final T item)
    {
        return item == null ? null : item.getId();
    }
}
