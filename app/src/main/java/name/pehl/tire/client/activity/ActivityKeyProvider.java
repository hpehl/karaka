package name.pehl.tire.client.activity;

import com.google.gwt.view.client.ProvidesKey;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

class ActivityKeyProvider implements ProvidesKey<Activity>
{
    @Override
    public Object getKey(Activity item)
    {
        return item.getId();
    }
}