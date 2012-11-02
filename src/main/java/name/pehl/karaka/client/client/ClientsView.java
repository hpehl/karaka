package name.pehl.karaka.client.client;

import java.util.List;

import name.pehl.karaka.client.resources.I18n;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.resources.TableResources;
import name.pehl.karaka.shared.model.Client;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class ClientsView extends ViewWithUiHandlers<ClientsUiHandlers> implements ClientsPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, ClientsView>
    {
    }

    // ---------------------------------------------------------- private stuff

    final I18n i18n;
    final Widget widget;

    @UiField(provided = true) ClientsTable clientsTable;


    // ------------------------------------------------------------------ setup

    @Inject
    public ClientsView(final Binder binder, final I18n i18n, final Resources resources,
            final TableResources commonTableResources)
    {
        this.i18n = i18n;
        this.clientsTable = new ClientsTable(resources, commonTableResources);
        this.widget = binder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void updateClients(final List<Client> clients)
    {
        clientsTable.update(clients);
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("clientsTable")
    public void onClientAction(final ClientActionEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onClientAction(event.getAction(), event.getClient());
        }
    }
}
