package name.pehl.tire.client.ui;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-14 17:40:17 +0100 (Di, 14. Dez 2010) $ $Revision: 102
 *          $
 */
public final class UiUtils
{
    private UiUtils()
    {
    }


    /**
     * Removes any content in the container and adds the specified widget if it
     * is not <code>null</code>.
     * 
     * @param container
     * @param content
     */
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


    /**
     * Returns <code>true</code> if <code>textWidget</code> is <code>null</code>
     * or empty.
     * 
     * @param textWidget
     * @return
     */
    public static boolean isEmpty(HasText textWidget)
    {
        if (textWidget != null)
        {
            String text = textWidget.getText();
            return text == null || text.length() == 0;
        }
        return true;
    }
}
