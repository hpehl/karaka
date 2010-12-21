package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.model.Activity;

import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public abstract class ActivityTextRenderer extends ActivityRenderer
{
    @Override
    public SafeHtml render(Activity activity)
    {
        String value = getValue(activity);
        return toSafeHtml(value);
    }


    protected abstract String getValue(Activity activity);
}
