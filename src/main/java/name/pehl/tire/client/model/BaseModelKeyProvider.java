package name.pehl.tire.client.model;

import com.google.gwt.view.client.ProvidesKey;

/**
 * Key provider which uses {@link BaseModel#getId()} as key.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-03 15:39:56 +0100 (Mi, 03. Nov 2010) $ $Revision: 133 $
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
