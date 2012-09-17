package name.pehl.tire.client.project;

import static name.pehl.tire.client.project.ProjectAction.Action.DELETE;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.cell.ModelsTableResources;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectActionCell extends ModelActionCell<Project>
{
    final ProjectsTable table;


    public ProjectActionCell(final ProjectsTable table, final ModelRenderer<Project> renderer,
            final ModelsTableResources tableResources)
    {
        super(renderer, tableResources);
        this.table = table;
    }


    @Override
    public void onClick(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Project value, final ValueUpdater<Project> valueUpdater)
    {
        table.onProjectAction(DELETE, value);
    }
}
