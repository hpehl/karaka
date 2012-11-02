package name.pehl.karaka.client.client;

import com.google.gwt.event.shared.HandlerRegistration;
import name.pehl.karaka.client.cell.*;
import name.pehl.karaka.client.client.ClientActionEvent.ClientActionHandler;
import name.pehl.karaka.client.client.ClientActionEvent.HasClientActionHandlers;
import name.pehl.karaka.client.resources.TableResources;
import name.pehl.karaka.shared.model.Client;

import static name.pehl.karaka.client.client.ClientAction.Action.DELETE;
import static name.pehl.karaka.client.client.ClientAction.Action.DETAILS;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ClientsTable extends ModelsTable<Client> implements HasClientActionHandlers
{
    // ---------------------------------------------------------------- members

    final name.pehl.karaka.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public ClientsTable(final name.pehl.karaka.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.clientsTableStyle().ensureInjected();
        addColumns();
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Column #0: Name
        ModelColumn<Client> column = new ModelColumn<Client>(new ModelCell<Client>(this,
                new ModelTextRenderer<Client>()
                {
                    @Override
                    protected String getValue(final Client client)
                    {
                        return client.getName();
                    }
                }));
        addColumn(column);
        addColumnStyleName(0, resources.clientsTableStyle().nameColumn());

        // Column #1: Description
        column = new ModelColumn<Client>(new ModelCell<Client>(this, new ModelTextRenderer<Client>()
        {
            @Override
            public String getValue(final Client client)
            {
                return client.getDescription();
            }
        }));
        addColumn(column);
        addColumnStyleName(1, resources.clientsTableStyle().descriptionColumn());

        // Column #2: Action
        DeleteActionCell<Client> actionCell = new DeleteActionCell<Client>(this, resources);
        column = new ModelColumn<Client>(actionCell);
        addColumn(column);
        addColumnStyleName(3, resources.clientsTableStyle().actionsColumn());
        this.actionCell = actionCell;
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addClientActionHandler(final ClientActionHandler handler)
    {
        return addHandler(handler, ClientActionEvent.getType());
    }


    @Override
    public void onEdit(final Client client)
    {
        ClientActionEvent.fire(this, DETAILS, client);
    }

    @Override
    public void onAction(final Client client, final String id)
    {
        ClientActionEvent.fire(this, DELETE, client);
    }
}
