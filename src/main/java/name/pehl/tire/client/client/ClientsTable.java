package name.pehl.tire.client.client;

import static name.pehl.tire.client.client.ClientAction.Action.DETAILS;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.client.ClientAction.Action;
import name.pehl.tire.client.client.ClientActionEvent.ClientActionHandler;
import name.pehl.tire.client.client.ClientActionEvent.HasClientActionHandlers;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.shared.model.Client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ClientsTable extends ModelsTable<Client> implements HasClientActionHandlers
{
    // -------------------------------------------------------------- templates

    interface ActionsTemplates extends SafeHtmlTemplates
    {
        @Template("<div class=\"{0}\" style=\"width: 16px;\"><span id=\"delete\" title=\"Delete\">{1}</span></div>")
        SafeHtml actions(String hideActionsClassname, SafeHtml delete);
    }

    static final ActionsTemplates TEMPLATES = GWT.create(ActionsTemplates.class);

    // ---------------------------------------------------------------- members

    final name.pehl.tire.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public ClientsTable(final name.pehl.tire.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.projectsTableStyle().ensureInjected();
        addColumns();
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Action is the last column in the UI, but the first one to create!
        this.actionCell = new ModelActionCell<Client>(this, new ModelRenderer<Client>()
        {
            @Override
            public SafeHtml render(final Client client)
            {
                SafeHtml deleteHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(resources.delete())
                        .getHTML());
                return TEMPLATES.actions(tableResources.cellTableStyle().hideActions(), deleteHtml);
            }
        });

        // Column #0: Name
        addDataColumn(resources.projectsTableStyle().nameColumn(), 0, new ModelTextRenderer<Client>()
        {
            @Override
            protected String getValue(final Client client)
            {
                return client.getName();
            }
        }, null, null);

        // Column #1: Description
        addDataColumn(resources.projectsTableStyle().descriptionColumn(), 1, new ModelTextRenderer<Client>()
        {
            @Override
            public String getValue(final Client client)
            {
                return client.getDescription();
            }
        }, null, null);

        // Column #2: Action
        ModelColumn<Client> actionColumn = new ModelColumn<Client>(actionCell);
        addColumnStyleName(2, resources.projectsTableStyle().actionsColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addClientActionHandler(final ClientActionHandler handler)
    {
        return addHandler(handler, ClientActionEvent.getType());
    }


    @Override
    protected void onClick(final Client client)
    {
        ClientActionEvent.fire(this, DETAILS, client);
    }


    @Override
    protected void onAction(final Client client, final String actionId)
    {
        ClientActionEvent.fire(this, Action.valueOf(actionId.toUpperCase()), client);
    }
}
