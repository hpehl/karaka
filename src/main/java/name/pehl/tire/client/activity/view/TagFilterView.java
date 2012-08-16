package name.pehl.tire.client.activity.view;

import java.util.HashMap;
import java.util.Map;

import name.pehl.tire.client.activity.presenter.TagFilterPresenter;
import name.pehl.tire.client.activity.presenter.TagFilterUiHandlers;
import name.pehl.tire.shared.model.Tag;

import com.google.common.collect.Multiset;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-07 16:33:54 +0100 (Di, 07. Dez 2010) $ $Revision: 102
 *          $
 */
public class TagFilterView extends ViewWithUiHandlers<TagFilterUiHandlers> implements TagFilterPresenter.MyView,
        EventListener
{
    private static final String ALL = "__all__";

    public interface TagFilterStyle extends CssResource
    {
        String selected();
    }

    public interface Binder extends UiBinder<HTMLPanel, TagFilterView>
    {
    }

    private final HTMLPanel panel;
    private final Map<String, Tag> idToTag;
    @UiField UListElement list;
    @UiField TagFilterStyle style;


    @Inject
    public TagFilterView(final Binder binder)
    {
        this.panel = binder.createAndBindUi(this);
        this.idToTag = new HashMap<String, Tag>();
    }


    @Override
    public Widget asWidget()
    {
        return panel;
    }


    @Override
    public void refresh(Multiset<Tag> tags)
    {
        // clear stuff
        idToTag.clear();
        NodeList<Node> childNodes = list.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++)
        {
            childNodes.getItem(i).removeFromParent();
        }

        if (!tags.isEmpty())
        {
            // fill list - first element is the "all" entry
            createDom(ALL, "all", 0);
            for (Multiset.Entry<Tag> entry : tags.entrySet())
            {
                Tag tag = entry.getElement();
                int count = entry.getCount();
                createDom(tag.getId(), tag.getName(), count);
                idToTag.put(tag.getId(), tag);
            }
        }
    }


    private void createDom(String id, String name, int count)
    {
        LIElement li = Document.get().createLIElement();

        AnchorElement link = Document.get().createAnchorElement();
        link.setAttribute("rel", id);
        link.setAttribute("href", "#");
        link.setInnerText(name);
        Element linkElement = (Element) Element.as(link);
        DOM.sinkEvents(linkElement, Event.ONCLICK);
        DOM.setEventListener(linkElement, this);

        if (count > 0)
        {
            SpanElement countElement = Document.get().createSpanElement();
            countElement.setInnerText(" (" + count + ")");
            link.appendChild(countElement);
        }

        li.appendChild(link);
        list.appendChild(li);
    }


    @Override
    public void onBrowserEvent(Event event)
    {
        EventTarget target = event.getEventTarget();
        AnchorElement link = (AnchorElement) Element.as(target);
        String id = link.getAttribute("rel");
        if (id != null)
        {
            if (getUiHandlers() != null)
            {
                if (ALL.equals(id))
                {
                    getUiHandlers().onAll();
                }
                else
                {
                    Tag tag = idToTag.get(id);
                    if (tag != null)
                    {
                        getUiHandlers().onFilter(tag);
                        link.getParentElement().addClassName(style.selected());
                    }
                }
            }
        }
    }
}
