package name.pehl.karaka.client.activity.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.pehl.karaka.client.model.NamedModelSuggestOracle;
import name.pehl.karaka.client.model.NamedModelSuggestion;
import name.pehl.karaka.client.tag.TagsCache;
import name.pehl.karaka.client.ui.Html5TextBox;
import name.pehl.karaka.shared.model.Tag;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class TagsEditorWidget extends Composite implements HasValue<List<Tag>>, IsEditor<LeafValueEditor<List<Tag>>>,
        ValueChangeHandler<String>, SelectionHandler<Suggestion>, ClickHandler
{
    // -------------------------------------------------------------- templates

    public interface TagTemplate extends SafeHtmlTemplates
    {
        @Template("<mark id=\"{0}\" class=\"existing\" title=\"Click to remove tag\">{1}</mark>")
        SafeHtml tag(String id, String name);
    }

    private static final TagTemplate TAG_TEMPLATE = GWT.create(TagTemplate.class);

    // ------------------------------------------------------- member variables

    private final FlowPanel rootPanel;
    private final FlowPanel tagsPanel;
    private final SuggestBox newTag;
    private final Map<String, Tag> tags;


    // ------------------------------------------------------------ constructor

    public TagsEditorWidget(final TagsCache tagsCache)
    {
        super();
        this.rootPanel = new FlowPanel();
        this.tagsPanel = new FlowPanel();
        this.tags = new HashMap<String, Tag>();

        NamedModelSuggestOracle<Tag> tagOracle = new NamedModelSuggestOracle<Tag>(tagsCache);
        Html5TextBox newTagTextBox = new Html5TextBox();
        newTagTextBox.setPlaceholder("New Tag");
        this.newTag = new SuggestBox(tagOracle, newTagTextBox);
        this.newTag.addValueChangeHandler(this);
        this.newTag.addSelectionHandler(this);

        rootPanel.add(tagsPanel);
        rootPanel.add(newTag);
        initWidget(rootPanel);
        setStyleName("karaka-tagsEditor");
    }


    // ----------------------------------------------------------- editor stuff

    @Override
    public List<Tag> getValue()
    {
        return new ArrayList<Tag>(tags.values());
    }


    @Override
    public void setValue(final List<Tag> value)
    {
        refresh(value);
    }


    @Override
    public void setValue(final List<Tag> value, final boolean fireEvents)
    {
        refresh(value);
    }


    @Override
    public LeafValueEditor<List<Tag>> asEditor()
    {
        return TakesValueEditor.of(this);
    }


    @Override
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<List<Tag>> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }


    // --------------------------------------------------------- event handlers

    @Override
    public void onValueChange(final ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (Strings.emptyToNull(value) != null)
        {
            Tag tag = new Tag(value);
            addTag(tag);
            newTag.setText("");
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public void onSelection(final SelectionEvent<Suggestion> event)
    {
        NamedModelSuggestion<Tag> suggestion = (NamedModelSuggestion<Tag>) event.getSelectedItem();
        Tag tag = suggestion.getModel();
        if (tag != null)
        {
            addTag(tag);
            newTag.setText("");
        }
    }


    @Override
    public void onClick(final ClickEvent event)
    {
        InlineHTML tagWidget = (InlineHTML) event.getSource();
        removeTag(tagWidget);
    }


    // ------------------------------------------------------------ tag methods

    private void refresh(final List<Tag> tags)
    {
        this.tags.clear();
        this.tagsPanel.clear();
        for (Tag tag : tags)
        {
            addTag(tag);
        }
    }


    private void addTag(final Tag tag)
    {
        if (!tags.containsKey(tag.getId()))
        {
            InlineHTML tagWidget = new InlineHTML(TAG_TEMPLATE.tag(tag.getId(), tag.getName()));
            tagWidget.addClickHandler(this);
            tags.put(tag.getId(), tag);
            tagsPanel.add(tagWidget);
        }
    }


    private void removeTag(final InlineHTML tagWidget)
    {
        Element markElement = (Element) tagWidget.getElement().getFirstChildElement();
        if (markElement != null)
        {
            String id = markElement.getAttribute("id");
            tags.remove(id);
            tagsPanel.remove(tagWidget);
        }
    }
}
