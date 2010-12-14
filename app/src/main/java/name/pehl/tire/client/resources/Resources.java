package name.pehl.tire.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author $Author$
 * @version $Date$ $Revision: 157
 *          $
 */
// @formatter:off
public interface Resources extends ClientBundle
{
    // ----------------------------------------------------------------- images
    
    ImageResource recordOn();
    ImageResource recordOff();
    ImageResource selectedNavigation();


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


    public interface Navigation extends CssResource
    {
        @ClassName("selected")
        String selected();
    }

    @Source("navigation.css")
    Navigation navigation();


    public interface NewActivity extends CssResource
    {
        @ClassName("selected")
        String selected();
    }

    @Source("newActivity.css")
    NewActivity newActivity();
}
