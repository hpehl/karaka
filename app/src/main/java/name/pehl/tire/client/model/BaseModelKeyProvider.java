package name.pehl.tire.client.model;

import com.google.gwt.view.client.ProvidesKey;

/**
 * Key provider which uses {@link BaseModel#getId()} as key.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class BaseModelKeyProvider implements ProvidesKey<BaseModel>
{
    /**
     * Returns {@link BaseModel#getId()} or <code>null</code> in case item or
     * item.getId() is <code>null</code>.
     * 
     * @param item
     *            The model
     * @return {@link BaseModel#getId()} or <code>null</code> in case item or
     *         item.getId() is <code>null</code>.
     * @see com.google.gwt.view.client.ProvidesKey#getKey(java.lang.Object)
     */
    @Override
    public Object getKey(BaseModel item)
    {
        if (item != null && item.getId() != null)
        {
            return item.getId();
        }
        return null;
    }
}
