package name.pehl.tire.client.mvp;

import com.google.gwt.user.client.ui.HasValue;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface NamedDisplay extends WidgetDisplay
{
    HasValue<String> getName();
}
