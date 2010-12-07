package name.pehl.tire.client.activity;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 142
 *          $
 */
public class RecentActivitiesView extends ViewWithUiHandlers<ActivitiesNavigationUiHandlers> implements
        RecentActivitiesPresenter.MyView
{
    // @formatter:off
    interface RecentActivitiesUi extends UiBinder<Widget, RecentActivitiesView> {}
    private static RecentActivitiesUi uiBinder = GWT.create(RecentActivitiesUi.class);

    @UiField(provided = true) CellTable<Activity> activities;
    // @formatter:on

    private final Widget widget;


    public RecentActivitiesView()
    {
        activities = new CellTable<Activity>();
        activities.setRowCount(0);
        addColumns(activities);
        widget = uiBinder.createAndBindUi(this);

    }


    private void addColumns(CellTable<Activity> activities)
    {
        TextColumn<Activity> nameAndTagColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return activity.getName();
            }
        };
        activities.addColumn(nameAndTagColumn);
        TextColumn<Activity> projectColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                if (activity.getProject() != null)
                {
                    return activity.getProject().getName();
                }
                return null;
            }
        };
        activities.addColumn(projectColumn);
        TextColumn<Activity> durationColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return "nyi";
            }
        };
        activities.addColumn(durationColumn);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateActivities(List<Activity> activities)
    {
        this.activities.setRowData(0, activities);
        this.activities.setRowCount(activities.size());
    }
}
