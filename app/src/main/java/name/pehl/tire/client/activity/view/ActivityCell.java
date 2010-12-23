package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.model.Activity;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
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
public class ActivityCell extends AbstractSafeHtmlCell<Activity>
{
    private final ActivitiesTable activitiesTable;
    private final ActivityActionCell actionCell;


    public ActivityCell(ActivitiesTable activitiesTable, ActivityActionCell actionCell,
            SafeHtmlRenderer<Activity> renderer)
    {
        super(renderer, "click", "mouseover", "mouseout");
        this.activitiesTable = activitiesTable;
        this.actionCell = actionCell;
    }


    @Override
    public void onBrowserEvent(Element parent, Activity value, Object key, NativeEvent event,
            ValueUpdater<Activity> valueUpdater)
    {
        super.onBrowserEvent(parent, value, key, event, valueUpdater);
        if ("click".equals(event.getType()))
        {
            activitiesTable.onEdit(value);
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
    protected void render(SafeHtml data, Object key, SafeHtmlBuilder sb)
    {
        if (data != null)
        {
            sb.append(data);
        }
    }
}
