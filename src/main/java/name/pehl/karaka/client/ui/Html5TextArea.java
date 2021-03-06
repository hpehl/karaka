package name.pehl.karaka.client.ui;

import com.google.gwt.user.client.ui.TextArea;

/**
 * A TextArea with support for some of the new HTML5 attributes.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-11-11 10:53:09 +0100 (Do, 11. Nov 2010) $ $Revision: 102
 *          $
 */
public class Html5TextArea extends TextArea
{
    private final Html5ElementAdapter adapter;


    public Html5TextArea()
    {
        super();
        this.adapter = new Html5ElementAdapter(getElement());
    }


    public void setPlaceholder(String placeholder)
    {
        adapter.setPlaceholder(placeholder);
    }


    public void setAutofocus(String autofocus)
    {
        adapter.setAutofocus(autofocus);
    }
}
