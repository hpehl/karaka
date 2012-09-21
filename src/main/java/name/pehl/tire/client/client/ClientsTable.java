package name.pehl.tire.client.client;

import static name.pehl.tire.client.client.ClientAction.Action.DELETE;
import static name.pehl.tire.client.client.ClientAction.Action.DETAILS;
import name.pehl.tire.client.cell.ModelCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.client.ClientActionEvent.ClientActionHandler;
import name.pehl.tire.client.client.ClientActionEvent.HasClientActionHandlers;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.shared.model.Client;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ClientsTable extends ModelsTable<Client> implements HasClientActionHandlers
{
    // ---------------------------------------------------------------- members

    final name.pehl.tire.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public ClientsTable(final name.pehl.tire.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.clientsTableStyle().ensureInjected();
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
        column = new ModelColumn<Client>(new ClientActionCell()
        {
            @Override
            protected void onDelete(final Client client)
            {
                ClientActionEvent.fire(ClientsTable.this, DELETE, client);
            }
        });
        addColumn(column);
        addColumnStyleName(3, resources.clientsTableStyle().actionsColumn());
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
}
