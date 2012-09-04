package name.pehl.tire.client.activity.view;

import java.util.List;

import name.pehl.tire.shared.model.Tag;

import com.google.common.base.Strings;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagsEditorWidget extends Widget
{
    private final DivElement root;
    private final InputElement newTag;
    private final NewTagListener newTagListener;
    private final SpanElement existingTags;
    private final ExistingTagListener existingTagListener;


    public TagsEditorWidget()
    {
        super();
        root = Document.get().createDivElement();
        newTag = Document.get().createTextInputElement();
        newTagListener = new NewTagListener();
        existingTags = Document.get().createSpanElement();
        existingTagListener = new ExistingTagListener();

        root.appendChild(existingTags);
        root.appendChild(newTag);
        setElement(root);
        setStyleName("tire-tagsEditor");

        Element element = (Element) Element.as(newTag);
        DOM.sinkEvents(element, Event.ONKEYDOWN);
        DOM.setEventListener(element, newTagListener);
    }


    public void setTags(List<Tag> tags)
    {
        NodeList<Node> childNodes = existingTags.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++)
        {
            childNodes.getItem(i).removeFromParent();
        }
        for (Tag tag : tags)
        {
            addTag(tag.getName());
        }
    }


    private void addTag(String tag)
    {
        com.google.gwt.dom.client.Element markElement = Document.get().createElement("mark");
        markElement.setInnerText(tag);
        markElement.setClassName("existing");
        markElement.setTitle("Click to remove tag");

        Element element = (Element) Element.as(markElement);
        DOM.sinkEvents(element, Event.ONCLICK);
        DOM.setEventListener(element, existingTagListener);
    }

    class ExistingTagListener implements EventListener
    {
        @Override
        public void onBrowserEvent(Event event)
        {
            EventTarget target = event.getEventTarget();
            Element.as(target).removeFromParent();
        }
    }

    class NewTagListener implements EventListener
    {
        @Override
        public void onBrowserEvent(Event event)
        {
            if (event.getKeyCode() == 13)
            {
                String tag = newTag.getValue();
                if (Strings.emptyToNull(tag) != null)
                {
                    addTag(tag);
                    newTag.setValue("");
                }
            }
        }
    }
}
