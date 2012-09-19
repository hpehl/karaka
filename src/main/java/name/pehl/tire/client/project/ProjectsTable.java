package name.pehl.tire.client.project;

import static name.pehl.tire.client.project.ProjectAction.Action.DETAILS;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.project.ProjectAction.Action;
import name.pehl.tire.client.project.ProjectActionEvent.HasProjectActionHandlers;
import name.pehl.tire.client.project.ProjectActionEvent.ProjectActionHandler;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.shared.model.Client;
import name.pehl.tire.shared.model.Project;

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
public class ProjectsTable extends ModelsTable<Project> implements HasProjectActionHandlers
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

    public ProjectsTable(final name.pehl.tire.client.resources.Resources resources, final TableResources tableResources)
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
        this.actionCell = new ModelActionCell<Project>(this, new ModelRenderer<Project>()
        {
            @Override
            public SafeHtml render(final Project project)
            {
                SafeHtml deleteHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(resources.delete())
                        .getHTML());
                return TEMPLATES.actions(tableResources.cellTableStyle().hideActions(), deleteHtml);
            }
        });

        // Column #0: Name
        addDataColumn(resources.projectsTableStyle().nameColumn(), 0, new ModelTextRenderer<Project>()
        {
            @Override
            protected String getValue(final Project project)
            {
                return project.getName();
            }
        }, null, null);

        // Column #1: Description
        addDataColumn(resources.projectsTableStyle().descriptionColumn(), 1, new ModelTextRenderer<Project>()
        {
            @Override
            public String getValue(final Project project)
            {
                return project.getDescription();
            }
        }, null, null);

        // Column #2: Client
        addDataColumn(resources.projectsTableStyle().clientColumn(), 2, new ModelTextRenderer<Project>()
        {
            @Override
            public String getValue(final Project project)
            {
                String clientName = "";
                Client client = project.getClient();
                if (client != null)
                {
                    clientName = client.getName();
                }
                return clientName;
            }
        }, null, null);

        // Column #3: Action
        ModelColumn<Project> actionColumn = new ModelColumn<Project>(actionCell);
        addColumnStyleName(3, resources.projectsTableStyle().actionsColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addProjectActionHandler(final ProjectActionHandler handler)
    {
        return addHandler(handler, ProjectActionEvent.getType());
    }


    @Override
    protected void onClick(final Project project)
    {
        ProjectActionEvent.fire(this, DETAILS, project);
    }


    @Override
    protected void onAction(final Project project, final String actionId)
    {
        ProjectActionEvent.fire(this, Action.valueOf(actionId.toUpperCase()), project);
    }
}
