package name.pehl.tire.client.activity.view;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

import java.util.List;

import name.pehl.tire.client.activity.event.ActivityAction;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityActionEvent.HasActivityActionHandlers;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.client.tag.Tag;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.model.Status;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextHeader;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivitiesTable extends CellTable<Activity> implements HasActivityActionHandlers
{
    // -------------------------------------------------------- private members

    private final ActivitiesTableResources atr;
    private Activities currentActivities;


    // ----------------------------------------------------------- constructors

    public ActivitiesTable(final ActivitiesTableResources atr)
    {
        super(Integer.MAX_VALUE, atr, new ActivityKeyProvider());
        this.atr = atr;

        setRowCount(0);
        setKeyboardSelectionPolicy(DISABLED);
        setRowStyles(new RowStyles<Activity>()
        {
            @Override
            @SuppressWarnings("deprecation")
            public String getStyleNames(Activity row, int rowIndex)
            {
                if (row.getStatus() == Status.RUNNING)
                {
                    return atr.cellTableStyle().activeActivity();
                }
                if (row.getStart().getDate() % 2 != 0)
                {
                    return atr.cellTableStyle().oddDays();
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
        ActivityActionCell actionCell = new ActivityActionCell(this, atr);

        // Column #0: Start date
        ActivityColumn startColumn = new ActivityColumn(this, actionCell, new ActivityTextRenderer()
        {
            @Override
            protected String getValue(Activity activity)
            {
                return FormatUtils.date(activity.getStart());
            }
        });
        addColumnStyleName(0, atr.cellTableStyle().startColumn());
        addColumn(startColumn, null, new TextHeader(null)
        {
            @Override
            public String getValue()
            {
                if (currentActivities != null)
                {
                    return currentActivities.days() + " days";
                }
                return null;
            }
        });

        // Column #1: Duration from - to
        ActivityColumn durationFromToColumn = new ActivityColumn(this, actionCell, new ActivityTextRenderer()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.time(activity.getStart()) + " - " + FormatUtils.time(activity.getEnd());
            }
        });
        addColumnStyleName(1, atr.cellTableStyle().durationFromToColumn());
        addColumn(durationFromToColumn);

        // Column #2: Duration in hours
        // TODO Right align as soon as
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=5623 is
        // released
        ActivityColumn durationInHoursColumn = new ActivityColumn(this, actionCell, new ActivityTextRenderer()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.hours(activity.getMinutes());
            }
        });
        addColumnStyleName(2, atr.cellTableStyle().durationInHoursColumn());
        addColumn(durationInHoursColumn, null, new TextHeader(null)
        {
            @Override
            public String getValue()
            {
                if (currentActivities != null)
                {
                    return FormatUtils.hours(currentActivities.getMinutes());
                }
                return null;
            }
        });

        // Column #3: Name, Description & Tags
        ActivityRenderer nameRenderer = new ActivityRenderer()
        {
            @Override
            public SafeHtml render(Activity activity)
            {
                SafeHtml nameDescription = ActivityTemplates.INSTANCE.nameDescription(activity.getName(),
                        activity.getDescription());
                List<Tag> tags = activity.getTags();
                if (tags.isEmpty())
                {
                    return nameDescription;
                }
                else
                {
                    SafeHtmlBuilder safeHtmlBuilder = new SafeHtmlBuilder();
                    safeHtmlBuilder.append(nameDescription);
                    for (Tag tag : tags)
                    {
                        safeHtmlBuilder.append(' ').append(ActivityTemplates.INSTANCE.tag(tag.getName()));
                    }
                    return safeHtmlBuilder.toSafeHtml();
                }
            }
        };
        ActivityColumn nameColumn = new ActivityColumn(this, actionCell, nameRenderer)
        {
            @Override
            public Activity getValue(Activity activity)
            {
                return activity;
            }
        };
        addColumnStyleName(3, atr.cellTableStyle().nameColumn());
        addColumn(nameColumn);

        // Column #4: Project
        ActivityColumn projectColumn = new ActivityColumn(this, actionCell, new ActivityTextRenderer()
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
        });
        addColumnStyleName(4, atr.cellTableStyle().projectColumn());
        addColumn(projectColumn);

        // Column #5: Actions
        ActivityColumn actionColumn = new ActivityColumn(actionCell);
        addColumnStyleName(5, atr.cellTableStyle().actionColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- public methods

    public void update(Activities activities)
    {
        currentActivities = activities;
        setRowData(0, activities.getActivities());
        setRowCount(activities.getActivities().size());
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addActivityActionHandler(ActivityActionHandler handler)
    {
        return addHandler(handler, ActivityActionEvent.getType());
    }


    public void onEdit(Activity activity)
    {
        ActivityActionEvent.fire(this, activity, ActivityAction.Action.EDIT);
    }


    public void onCopy(Activity activity)
    {
        ActivityActionEvent.fire(this, activity, ActivityAction.Action.COPY);
    }


    public void onGoon(Activity activity)
    {
        ActivityActionEvent.fire(this, activity, ActivityAction.Action.GOON);
    }


    public void onDelete(Activity activity)
    {
        ActivityActionEvent.fire(this, activity, ActivityAction.Action.DELETE);
    }
}
