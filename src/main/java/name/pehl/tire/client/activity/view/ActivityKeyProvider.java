package name.pehl.tire.client.activity.view;

import name.pehl.tire.shared.model.Activity;

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
        return item == null ? null : item.getId();
    }
}
