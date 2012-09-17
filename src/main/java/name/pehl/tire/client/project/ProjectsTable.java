package name.pehl.tire.client.project;

import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.cell.ModelsTableResources;
import name.pehl.tire.client.project.ProjectAction.Action;
import name.pehl.tire.client.project.ProjectActionEvent.HasProjectActionHandlers;
import name.pehl.tire.client.project.ProjectActionEvent.ProjectActionHandler;
import name.pehl.tire.shared.model.Client;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectsTable extends ModelsTable<Project> implements HasProjectActionHandlers
{
    // -------------------------------------------------------- private members

    final ProjectsTemplates projectTemplates;
    final ProjectsTableResources projectTableResources;


    // ----------------------------------------------------------- constructors

    public ProjectsTable(final ProjectsTemplates projectTemplates, final ProjectsTableResources projectTableResources,
            final ModelsTableResources commonTableResources)
    {
        super(commonTableResources);
        this.projectTemplates = projectTemplates;
        this.projectTableResources = projectTableResources;
    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Action is the last column in the UI, but the first one to create!
        this.actionCell = new ProjectActionCell(this, tableResources, new ModelRenderer<Project>()
        {
            @Override
            public SafeHtml render(final Project object)
            {
                SafeHtml deleteHtml = SafeHtmlUtils.fromTrustedString(AbstractImagePrototype.create(
                        tableResources.delete()).getHTML());
                return projectTemplates.actions(tableResources.cellTableStyle().hideActions(), deleteHtml);
            }
        });

        // Column #0: Name
        addDataColumn(projectTableResources.cellTableStyle().nameColumn(), 0, new ModelTextRenderer<Project>()
        {
            @Override
            protected String getValue(final Project project)
            {
                return project.getName();
            }
        });

        // Column #1: Description
        addDataColumn(projectTableResources.cellTableStyle().descriptionColumn(), 1, new ModelTextRenderer<Project>()
        {
            @Override
            public String getValue(final Project project)
            {
                return project.getDescription();
            }
        });

        // Column #2: Client
        addDataColumn(projectTableResources.cellTableStyle().clientColumn(), 2, new ModelTextRenderer<Project>()
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
        });

        // Column #3: Action
        ModelColumn<Project> actionColumn = new ModelColumn<Project>(actionCell);
        addColumnStyleName(3, projectTableResources.cellTableStyle().actionsColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addProjectActionHandler(final ProjectActionHandler handler)
    {
        return addHandler(handler, ProjectActionEvent.getType());
    }


    public void onProjectAction(final Action action, final Project project)
    {
        ProjectActionEvent.fire(this, action, project);
    }
}
