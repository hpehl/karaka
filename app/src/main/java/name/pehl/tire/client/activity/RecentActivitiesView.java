package name.pehl.tire.client.activity;

import name.pehl.tire.client.activity.ActivitiesNavigation.Unit;
import name.pehl.tire.client.ui.UiUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.InlineLabel;
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

    @UiField InlineLabel header;
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
    public void updateActivities(Activities activities, Unit unit)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Recent activities by ").append(unit.name().toLowerCase()).append(": ")
                .append(UiUtils.DATE_FORMAT.format(activities.getStart().getDate())).append(" - ")
                .append(UiUtils.DATE_FORMAT.format(activities.getEnd().getDate()));
        this.header.setText(builder.toString());
        this.activities.setRowData(0, activities.getActivities());
        this.activities.setRowCount(activities.getActivities().size());
    }
}
