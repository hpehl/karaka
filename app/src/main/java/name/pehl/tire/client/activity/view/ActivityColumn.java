package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.model.Activity;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.user.cellview.client.Column;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ActivityColumn extends Column<Activity, Activity>
{
    public ActivityColumn(Cell<Activity> cell)
    {
        super(cell);
    }


    public ActivityColumn(ActivityActionCell actionCell, SafeHtmlRenderer<Activity> renderer)
    {
        super(new ActivityCell(actionCell, renderer));
    }


    @Override
    public Activity getValue(Activity object)
    {
        return object;
    }
}
