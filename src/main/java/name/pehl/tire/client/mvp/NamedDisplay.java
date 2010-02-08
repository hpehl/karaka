package name.pehl.tire.client.mvp;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.user.client.ui.HasValue;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface NamedDisplay extends WidgetDisplay
{
    HasValue<String> getName();
}
