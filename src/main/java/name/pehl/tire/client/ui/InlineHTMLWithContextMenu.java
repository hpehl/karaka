package name.pehl.tire.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.HasContextMenuHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.shared.DirectionEstimator;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.InlineHTML;

public class InlineHTMLWithContextMenu extends InlineHTML implements HasContextMenuHandlers
{
    public InlineHTMLWithContextMenu()
    {
        super();
    }


    public InlineHTMLWithContextMenu(Element element)
    {
        super(element);
    }


    public InlineHTMLWithContextMenu(SafeHtml html, Direction dir)
    {
        super(html, dir);
    }


    public InlineHTMLWithContextMenu(SafeHtml html, DirectionEstimator directionEstimator)
    {
        super(html, directionEstimator);
    }


    public InlineHTMLWithContextMenu(SafeHtml html)
    {
        super(html);
    }


    public InlineHTMLWithContextMenu(String html, Direction dir)
    {
        super(html, dir);
    }


    public InlineHTMLWithContextMenu(String html)
    {
        super(html);
    }


    @Override
    public HandlerRegistration addContextMenuHandler(ContextMenuHandler handler)
    {
        return addDomHandler(handler, ContextMenuEvent.getType());
    }
}
