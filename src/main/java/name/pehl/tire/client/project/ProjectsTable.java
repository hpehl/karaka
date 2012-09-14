package name.pehl.tire.client.project;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.model.ModelKeyProvider;
import name.pehl.tire.client.model.ModelTextRenderer;
import name.pehl.tire.client.project.ProjectAction.Action;
import name.pehl.tire.client.project.ProjectActionEvent.HasProjectActionHandlers;
import name.pehl.tire.client.project.ProjectActionEvent.ProjectActionHandler;
import name.pehl.tire.shared.model.Client;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.RowStyles;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectsTable extends CellTable<Project> implements HasProjectActionHandlers
{
    // -------------------------------------------------------- private members

    private final ProjectsTableResources ptr;


    // ----------------------------------------------------------- constructors

    public ProjectsTable(final ProjectsTableResources atr)
    {
        super(Integer.MAX_VALUE, atr, new ModelKeyProvider<Project>());
        this.ptr = atr;

        setRowCount(0);
        setKeyboardSelectionPolicy(DISABLED);
        setRowStyles(new RowStyles<Project>()
        {
            @Override
            public String getStyleNames(final Project row, final int rowIndex)
            {
                if (rowIndex % 2 != 0)
                {
                    return ptr.cellTableStyle().odd();
                }
                return null;
            }
        });
        addColumns();
    }


    // -------------------------------------------------------------- gui setup

    private void addColumns()
    {
        // Action cell is used in all other cells to show / hide the actions
        ProjectActionCell actionCell = new ProjectActionCell(this, ptr);

        // Column #0: Name
        ProjectColumn nameColumn = new ProjectColumn(this, actionCell, new ModelTextRenderer<Project>()
        {
            @Override
            protected String getValue(final Project project)
            {
                return project.getName();
            }
        });
        addColumnStyleName(0, ptr.cellTableStyle().nameColumn());
        addColumn(nameColumn);

        // Column #1: Description
        ProjectColumn descriptionColumn = new ProjectColumn(this, actionCell, new ModelTextRenderer<Project>()
        {
            @Override
            public String getValue(final Project project)
            {
                return project.getDescription();
            }
        });
        addColumnStyleName(1, ptr.cellTableStyle().descriptionColumn());
        addColumn(descriptionColumn);

        // Column #2: Client
        ProjectColumn clientColumn = new ProjectColumn(this, actionCell, new ModelTextRenderer<Project>()
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
        addColumnStyleName(2, ptr.cellTableStyle().clientColumn());
        addColumn(clientColumn);

        // Column #3: Actions
        ProjectColumn actionColumn = new ProjectColumn(actionCell);
        addColumnStyleName(3, ptr.cellTableStyle().actionsColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- public methods

    public void update(final List<Project> projects)
    {
        List<Project> local = projects;
        if (local == null)
        {
            local = new ArrayList<Project>();
        }
        setRowData(0, local);
        setRowCount(local.size());
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
