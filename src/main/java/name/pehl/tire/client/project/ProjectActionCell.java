package name.pehl.tire.client.project;

import static name.pehl.tire.client.project.ProjectAction.Action.DELETE;
import name.pehl.tire.client.cell.ModelActionCell;
import name.pehl.tire.client.cell.ModelRenderer;
import name.pehl.tire.client.resources.CommonTableResources;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectActionCell extends ModelActionCell<Project>
{
    final ProjectsTable table;


    public ProjectActionCell(final ProjectsTable table, final CommonTableResources tableResources,
            final ModelRenderer<Project> renderer)
    {
        super(tableResources, renderer);
        this.table = table;
    }


    @Override
    public void onClick(final com.google.gwt.cell.client.Cell.Context context, final Element parent,
            final Project value, final NativeEvent event, final ValueUpdater<Project> valueUpdater)
    {
        table.onProjectAction(DELETE, value);
    }
}
