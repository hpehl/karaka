package name.pehl.tire.client.ui;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget to display simple SVG graphics defined by a path. See <a
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
    private final String p;
    private String color = "#000";
    private final int width;
    private final int height;
    private JavaScriptObject path;


    public @UiConstructor
    SvgPath(final int width, final int height, final String path)
    {
        this.width = width;
        this.height = height;
        this.p = path;
        setElement(Document.get().createSpanElement());
        setStyleName("tire-SvgPath");
    }


    @Override
    protected void onLoad()
    {
        this.path = draw(getElement(), width, height, p, color);
    }


    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler)
    {
        return addDomHandler(handler, ClickEvent.getType());
    }


    public void setColor(String color)
    {
        this.color = color;
        if (path != null)
        {
            setPathColor(path, color);
        }
    }


    // -------------------------------------------------------- private members

    private native JavaScriptObject draw(Element holder, int width, int height, String path, String color) /*-{
        var r = $wnd.Raphael(holder, width, height);
        return r.path(path).attr({stroke: "none", fill: color});
    }-*/;


    private native JavaScriptObject setPathColor(JavaScriptObject path, String color) /*-{
        path.attr({fill: color});
    }-*/;
}
