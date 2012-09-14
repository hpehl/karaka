package name.pehl.tire.client.project;

import static name.pehl.tire.client.project.ProjectAction.Action.DETAILS;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ProjectCell extends AbstractSafeHtmlCell<Project>
{
    private final ProjectsTable projectsTable;
    private final ProjectActionCell actionCell;


    public ProjectCell(final ProjectsTable projectsTable, final ProjectActionCell actionCell,
            final SafeHtmlRenderer<Project> renderer)
    {
        super(renderer, "click", "mouseover", "mouseout");
        this.projectsTable = projectsTable;
        this.actionCell = actionCell;
    }


    @Override
    public void onBrowserEvent(final Cell.Context context, final Element parent, final Project value,
            final NativeEvent event, final ValueUpdater<Project> valueUpdater)
    {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        if ("click".equals(event.getType()))
        {
            actionCell.hideActions(parent);
            projectsTable.onProjectAction(DETAILS, value);
        }
        else if ("mouseover".equals(event.getType()))
        {
            actionCell.showActions(parent);
        }
        else if ("mouseout".equals(event.getType()))
        {
            actionCell.hideActions(parent);
        }
    }


    @Override
    protected void render(final Cell.Context context, final SafeHtml data, final SafeHtmlBuilder sb)
    {
        if (data != null)
        {
            sb.append(data);
        }
    }
}
