package name.pehl.tire.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public interface Resources extends ClientBundle
{
    @Source("background.png")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource background();


    @Source("tag.png")
    ImageResource tag();


    @Source("backgrounds.css")
    Backgrounds backgrounds();

    public interface Backgrounds extends CssResource
    {
        // @formatter:off
        String top();
        String tag();
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
