package name.pehl.karaka.client.project;

import com.google.gwt.event.shared.HandlerRegistration;
import name.pehl.karaka.client.cell.*;
import name.pehl.karaka.client.project.ProjectActionEvent.HasProjectActionHandlers;
import name.pehl.karaka.client.project.ProjectActionEvent.ProjectActionHandler;
import name.pehl.karaka.client.resources.TableResources;
import name.pehl.karaka.shared.model.Client;
import name.pehl.karaka.shared.model.Project;

import static name.pehl.karaka.client.project.ProjectAction.Action.DELETE;
import static name.pehl.karaka.client.project.ProjectAction.Action.DETAILS;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectsTable extends ModelsTable<Project> implements HasProjectActionHandlers
{
    // ---------------------------------------------------------------- members

    final name.pehl.karaka.client.resources.Resources resources;


    // ----------------------------------------------------------- constructors

    public ProjectsTable(final name.pehl.karaka.client.resources.Resources resources, final TableResources tableResources)
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
        DeleteActionCell<Project> actionCell = new DeleteActionCell<Project>(this, resources);
        column = new ModelColumn<Project>(actionCell);
        addColumn(column);
        addColumnStyleName(3, resources.projectsTableStyle().actionsColumn());
        this.actionCell = actionCell;
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

    @Override
    public void onAction(final Project project, final String id)
    {
        ProjectActionEvent.fire(this, DELETE, project);
    }
}
