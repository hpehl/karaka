package name.pehl.tire.client.ui;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public final class UiUtils
{
    private UiUtils()
    {
    }


    public static void setContent(Panel container, Widget content)
    {
        if (container != null)
        {
            container.clear();
            if (content != null)
            {
                container.add(content);
            }
        }
    }
}
