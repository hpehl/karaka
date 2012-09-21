package name.pehl.tire.client.tag;

import name.pehl.tire.shared.model.Tag;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

public abstract class TagActionCell extends AbstractCell<Tag>
{
    interface ActionRenderer extends UiRenderer
    {
        void render(SafeHtmlBuilder sb);


        void onBrowserEvent(TagActionCell cell, NativeEvent event, Element element, Tag tag);
    }

    static ActionRenderer renderer = GWT.create(ActionRenderer.class);


    public TagActionCell()
    {
        super("click");
    }


    @Override
    public void render(final com.google.gwt.cell.client.Cell.Context context, final Tag value, final SafeHtmlBuilder sb)
    {
        renderer.render(sb);
    }


    @Override
    public void onBrowserEvent(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Tag value, final NativeEvent event, final ValueUpdater<Tag> valueUpdater)
    {
        renderer.onBrowserEvent(this, event, parent, value);
    }


    @UiHandler("deleteSpan")
    void onDeleteClicked(final ClickEvent event, final Element parent, final Tag tag)
    {
        onDelete(tag);
    }


    protected abstract void onDelete(Tag tag);
}
