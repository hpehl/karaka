package name.pehl.tire.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author $Author$
 * @version $Date$ $Revision: 90
 *          $
 */
public interface Resources extends ClientBundle
{
    @Source("on.png")
    ImageResource on();


    @Source("off.png")
    ImageResource off();


    @Source("record.css")
    Record record();

    public interface Record extends CssResource
    {
        // @formatter:off
        @ClassName("on") String on();
        @ClassName("off") String off();
        // @formatter:on
    }


    @Source("navigation.css")
    Navigation navigation();

    public interface Navigation extends CssResource
    {
        // @formatter:off
        @ClassName("selected") String selected();
        // @formatter:on
    }


    @Source("colors.css")
    Colors colors();

    public interface Colors extends CssResource
    {
        // @formatter:off
        String turquoise0();
        String turquoise1();
        String turquoise2();
        String turquoise3();
        String turquoise4();
        String turquoise5();

        String green0();
        String green1();
        String green2();
        String green3();
        String green4();
        String green5();

        String grey0();
        String grey1();
        String grey2();
        String grey3();

        String yellow0();
        String yellow1();
        String yellow2();

        String pink0();
        // @formatter:on
    }
}
