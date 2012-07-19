package name.pehl.tire.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-22 16:43:49 +0100 (Mi, 22. Dez 2010) $ $Revision: 157
 *          $
 */
public interface Resources extends ClientBundle
{
    // ----------------------------------------------------------------- images

    // @formatter:off
    ImageResource recordOn();
    ImageResource recordOff();
    ImageResource selectedNavigation();
    ImageResource stopwatch32();
    ImageResource stopwatch64();
    ImageResource stopwatch128();
    // @formatter:on

    // -------------------------------------------------------------------- CSS

    /**
     * Global CSS rules
     * 
     * @return
     */
    @Source("tire.css")
    CssResource tire();


    /**
     * CSS classes to style GWT and custom widgets
     * 
     * @return
     */
    @Source("widgets.css")
    CssResource widgets();

    public interface Message extends CssResource
    {
        @ClassName("show")
        String show();


        @ClassName("hide")
        String hide();
    }


    @Source("message.css")
    Message message();

    public interface Navigation extends CssResource
    {
        @ClassName("selectedNavigationEntry")
        String selectedNavigationEntry();


        @ClassName("selectedDate")
        String selectedDate();
    }


    @Source("navigation.css")
    Navigation navigation();
}
