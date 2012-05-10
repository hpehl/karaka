package name.pehl.tire.client.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget to display simple SVG graphics defined by 1 to n paths inside. See <a
 * href="http://thenounproject.com/">http://thenounproject.com/</a> and <a
 * href="http://raphaeljs.com/icons/">http://raphaeljs.com/icons/</a> for sample
 * SVGs. The SVG is placed inside a SPAN element. <h3>CSS Style Rules</h3>
 * <ul>
 * <li>.tire-SvgPath { }</li>
 * </ul>
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class SvgPath extends Widget implements HasClickHandlers
{
    public static final String CSS_STYLE_NAME = "tire-SvgPath";
    private static final String SEPERATOR = "|";

    // -------------------------------------------------------- private members

    private final JavaScriptObject raphael;
    private final List<JavaScriptObject> allPaths;
    private final Map<String, JavaScriptObject> pathsById;


    // ----------------------------------------------------------- constructors

    @UiConstructor
    public SvgPath(int width, int height, String ids, String fills, String strokes, String paths)
    {
        SpanElement element = Document.get().createSpanElement();
        setElement(element);
        setStyleName("tire-SvgPath");
        this.raphael = initializeRaphael(element, width, height);
        this.allPaths = new ArrayList<JavaScriptObject>();
        this.pathsById = new HashMap<String, JavaScriptObject>();

        String[] idArray = split(ids);
        String[] fillArray = split(fills);
        String[] strokeArray = split(strokes);
        String[] pathArray = split(paths);
        Set<Integer> lengths = new HashSet<Integer>(Arrays.asList(idArray.length, fillArray.length, strokeArray.length,
                pathArray.length));
        if (lengths.size() != 1)
        {
            throw new IllegalArgumentException("Attributes must be of same size");
        }
        for (int i = 0; i < idArray.length; i++)
        {
            JavaScriptObject path = internalDraw(fillArray[i], strokeArray[i], pathArray[i]);
            allPaths.add(path);
            pathsById.put(idArray[i], path);
        }
    }


    // ---------------------------------------------------------------- methods

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler)
    {
        return addDomHandler(handler, ClickEvent.getType());
    }


    public void fill(String fill)
    {
        if (fill != null)
        {
            for (JavaScriptObject path : allPaths)
            {
                internalFill(path, fill);
            }
        }
    }


    // --------------------------------------------------------- helper methods

    private String[] split(String value)
    {
        if (value == null)
        {
            return new String[0];
        }
        return value.split("\\|");
    }


    // ----------------------------------------------------- javascript methods

    private native JavaScriptObject initializeRaphael(Element holder, int width, int height) /*-{
                                                                                             return $wnd.Raphael(holder, width, height);
                                                                                             }-*/;


    private native JavaScriptObject internalDraw(String fill, String stroke, String path) /*-{
                                                                                          var raphael = this.@name.pehl.tire.client.ui.SvgPath::raphael;
                                                                                          return raphael.path(path).attr({stroke: stroke, fill: fill});
                                                                                          }-*/;


    private native JavaScriptObject internalFill(JavaScriptObject path, String fill) /*-{
                                                                                     path.attr({fill: fill});
                                                                                     }-*/;
}
