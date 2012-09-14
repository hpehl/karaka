package name.pehl.tire.client.project;

import name.pehl.tire.shared.model.Project;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.user.cellview.client.Column;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ProjectColumn extends Column<Project, Project>
{
    public ProjectColumn(final Cell<Project> cell)
    {
        super(cell);
    }


    public ProjectColumn(final ProjectsTable projectsTable, final ProjectActionCell actionCell,
            final SafeHtmlRenderer<Project> renderer)
    {
        super(new ProjectCell(projectsTable, actionCell, renderer));
    }


    @Override
    public Project getValue(final Project object)
    {
        return object;
    }
}
