package name.pehl.tire.client.project;

import static name.pehl.tire.client.project.ProjectAction.Action.DELETE;
import static name.pehl.tire.client.project.ProjectAction.Action.DETAILS;
import name.pehl.tire.client.cell.ModelCell;
import name.pehl.tire.client.cell.ModelColumn;
import name.pehl.tire.client.cell.ModelTextRenderer;
import name.pehl.tire.client.cell.ModelsTable;
import name.pehl.tire.client.project.ProjectActionEvent.HasProjectActionHandlers;
import name.pehl.tire.client.project.ProjectActionEvent.ProjectActionHandler;
import name.pehl.tire.client.resources.TableResources;
import name.pehl.tire.shared.model.Client;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.event.shared.HandlerRegistration;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectsTable extends ModelsTable<Project> implements HasProjectActionHandlers
{
    // ---------------------------------------------------------------- members

    final name.pehl.tire.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public ProjectsTable(final name.pehl.tire.client.resources.Resources resources, final TableResources tableResources)
    {
        super(tableResources);
        this.resources = resources;
        this.resources.projectsTableStyle().ensureInjected();

    }


    // -------------------------------------------------------------- gui setup

    @Override
    protected void addColumns()
    {
        // Column #0: Name
        ModelColumn<Project> column = new ModelColumn<Project>(new ModelCell<Project>(this,
                new ModelTextRenderer<Project>()
                {
                    @Override
                    protected String getValue(final Project project)
                    {
                        return project.getName();
                    }
                }));
        addColumn(column);
        addColumnStyleName(0, resources.projectsTableStyle().nameColumn());

        // Column #1: Description
        column = new ModelColumn<Project>(new ModelCell<Project>(this, new ModelTextRenderer<Project>()
        {
            @Override
            public String getValue(final Project project)
            {
                return project.getDescription();
            }
        }));
        addColumn(column);
        addColumnStyleName(1, resources.projectsTableStyle().descriptionColumn());

        // Column #2: Client
        column = new ModelColumn<Project>(new ModelCell<Project>(this, new ModelTextRenderer<Project>()
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
        }));
        addColumn(column);
        addColumnStyleName(2, resources.projectsTableStyle().clientColumn());

        // Column #3: Action
        column = new ModelColumn<Project>(new ProjectActionCell()
        {
            @Override
            protected void onDelete(final Project project)
            {
                ProjectActionEvent.fire(ProjectsTable.this, DELETE, project);
            }
        });
        addColumn(column);
        addColumnStyleName(3, resources.projectsTableStyle().actionsColumn());
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addProjectActionHandler(final ProjectActionHandler handler)
    {
        return addHandler(handler, ProjectActionEvent.getType());
    }


    @Override
    public void onEdit(final Project project)
    {
        ProjectActionEvent.fire(this, DETAILS, project);
    }
}
