package name.pehl.tire.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author $Author$
 * @version $Date$ $Revision: 95
 *          $
 */
public interface Resources extends ClientBundle
{
    ImageResource recordOn();


    ImageResource recordOff();


    ImageResource selectedNavigation();


    @Source("common.css")
    CssResource common();


    @Source("navigation.css")
    Navigation navigation();

    public interface Navigation extends CssResource
    {
        @ClassName("selected")
        String selected();
    }


    @Source("newActivity.css")
    NewActivity newActivity();

    public interface NewActivity extends CssResource
    {
        @ClassName("selected")
        String selected();
    }
}
