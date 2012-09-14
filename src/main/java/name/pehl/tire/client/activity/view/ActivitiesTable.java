package name.pehl.tire.client.activity.view;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

import java.util.ArrayList;
import java.util.List;

import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityActionEvent.HasActivityActionHandlers;
import name.pehl.tire.client.model.ModelKeyProvider;
import name.pehl.tire.client.model.ModelRenderer;
import name.pehl.tire.client.model.ModelTextRenderer;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Tag;

import com.google.common.base.Strings;
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
        super(Integer.MAX_VALUE, atr, new ModelKeyProvider<Activity>());
        this.atr = atr;

        setRowCount(0);
        setKeyboardSelectionPolicy(DISABLED);
        setRowStyles(new RowStyles<Activity>()
        {
            @Override
            public String getStyleNames(final Activity row, final int rowIndex)
            {
                if (row.isRunning())
                {
                    return atr.cellTableStyle().activeActivity();
                }
                if (row.getStart().getDay() % 2 != 0)
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
        ActivityColumn startColumn = new ActivityColumn(this, actionCell, new ModelTextRenderer<Activity>()
        {
            @Override
            protected String getValue(final Activity activity)
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
                    return currentActivities.getNumberOfDays() + " days";
                }
                return null;
            }
        });

        // Column #1: Duration from - to
        ActivityColumn durationFromToColumn = new ActivityColumn(this, actionCell, new ModelTextRenderer<Activity>()
        {
            @Override
            public String getValue(final Activity activity)
            {
                return FormatUtils.timeDuration(activity.getStart(), activity.getEnd());
            }
        });
        addColumnStyleName(1, atr.cellTableStyle().durationFromToColumn());
        addColumn(durationFromToColumn);

        // Column #2: Duration in hours
        ActivityColumn durationInHoursColumn = new ActivityColumn(this, actionCell, new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity activity)
            {
                String duration = FormatUtils.duration(activity.getDuration());
                if (activity.getPause().isZero())
                {
                    return toSafeHtml(duration);
                }
                else
                {
                    String pause = FormatUtils.duration(activity.getPause());
                    return ActivityTemplates.INSTANCE.duration(duration, pause);
                }
            }
        });
        durationInHoursColumn.setHorizontalAlignment(ActivityColumn.ALIGN_RIGHT);
        addColumnStyleName(2, atr.cellTableStyle().durationInHoursColumn());
        addColumn(durationInHoursColumn, null, new TextHeader(null)
        {
            @Override
            public String getValue()
            {
                if (currentActivities != null)
                {
                    return FormatUtils.duration(currentActivities.getDuration());
                }
                return null;
            }
        });

        // Column #3: Name, Description & Tags
        ModelRenderer<Activity> nameRenderer = new ModelRenderer<Activity>()
        {
            @Override
            public SafeHtml render(final Activity activity)
            {
                SafeHtml nameDescription = ActivityTemplates.INSTANCE.nameDescription(activity.getName(),
                        Strings.nullToEmpty(activity.getDescription()));
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
                        if (tag.getName() != null)
                        {
                            safeHtmlBuilder.append(' ').append(ActivityTemplates.INSTANCE.tag(tag.getName()));
                        }
                    }
                    return safeHtmlBuilder.toSafeHtml();
                }
            }
        };
        ActivityColumn nameColumn = new ActivityColumn(this, actionCell, nameRenderer)
        {
            @Override
            public Activity getValue(final Activity activity)
            {
                return activity;
            }
        };
        addColumnStyleName(3, atr.cellTableStyle().nameColumn());
        addColumn(nameColumn);

        // Column #4: Project
        ActivityColumn projectColumn = new ActivityColumn(this, actionCell, new ModelTextRenderer<Activity>()
        {
            @Override
            public String getValue(final Activity activity)
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
        addColumnStyleName(5, atr.cellTableStyle().actionsColumn());
        addColumn(actionColumn);
    }


    // --------------------------------------------------------- public methods

    public void update(final Activities activities)
    {
        currentActivities = activities;
        setRowData(0, new ArrayList<Activity>(activities.activities()));
        setRowCount(activities.activities().size());
    }


    // --------------------------------------------------------- event handling

    @Override
    public HandlerRegistration addActivityActionHandler(final ActivityActionHandler handler)
    {
        return addHandler(handler, ActivityActionEvent.getType());
    }


    public void onActivityAction(final Action action, final Activity activity)
    {
        ActivityActionEvent.fire(this, action, activity);
    }
}
