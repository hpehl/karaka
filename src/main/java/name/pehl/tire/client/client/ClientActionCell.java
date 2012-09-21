package name.pehl.tire.client.client;

import name.pehl.tire.shared.model.Client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

public abstract class ClientActionCell extends AbstractCell<Client>
{
    interface ActionRenderer extends UiRenderer
    {
        void render(SafeHtmlBuilder sb);


        void onBrowserEvent(ClientActionCell cell, NativeEvent event, Element element, Client client);
    }

    static ActionRenderer renderer = GWT.create(ActionRenderer.class);


    public ClientActionCell()
    {
        super("click");
    }


    @Override
    public void render(final com.google.gwt.cell.client.Cell.Context context, final Client value,
            final SafeHtmlBuilder sb)
    {
        renderer.render(sb);
    }


    @Override
    public void onBrowserEvent(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Client value, final NativeEvent event, final ValueUpdater<Client> valueUpdater)
    {
        renderer.onBrowserEvent(this, event, parent, value);
    }


    @UiHandler("deleteSpan")
    void onDeleteClicked(final ClickEvent event, final Element parent, final Client client)
    {
        onDelete(client);
    }


    protected abstract void onDelete(Client client);
}
