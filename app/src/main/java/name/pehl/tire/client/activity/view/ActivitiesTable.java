package name.pehl.tire.client.activity.view;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;

import java.util.List;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.client.tag.Tag;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.model.Status;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ActivitiesTable extends CellTable<Activity>
{
    interface ActivityTemplates extends SafeHtmlTemplates
    {
        @Template("<span title=\"{1}\">{0}</span>")
        SafeHtml nameDescription(String name, String description);


        @Template("<mark>{0}</mark>")
        SafeHtml tag(String name);
    }

    private static final ActivityTemplates ACTIVITY_TEMPLATES = GWT.create(ActivityTemplates.class);

    private final ActivitiesTableResources atr;
    private Activities currentActivities;


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


    private void addColumns()
    {
        // Column #0: Start date
        TextColumn<Activity> startColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.date(activity.getStart());
            }
        };
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
        TextColumn<Activity> durationFromToColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.time(activity.getStart()) + " - " + FormatUtils.time(activity.getEnd());
            }
        };
        addColumnStyleName(1, atr.cellTableStyle().durationFromToColumn());
        addColumn(durationFromToColumn);

        // Column #2: Duration in hours
        // TODO Right align as soon as
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=5623 is
        // released
        TextColumn<Activity> durationInHoursColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.hours(activity.getMinutes());
            }
        };
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
        SafeHtmlRenderer<Activity> nameRenderer = new AbstractSafeHtmlRenderer<Activity>()
        {
            @Override
            public SafeHtml render(Activity activity)
            {
                SafeHtml nameDescription = ACTIVITY_TEMPLATES.nameDescription(activity.getName(),
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
                        safeHtmlBuilder.append(' ').append(ACTIVITY_TEMPLATES.tag(tag.getName()));
                    }
                    return safeHtmlBuilder.toSafeHtml();
                }
            }
        };
        Cell<Activity> nameCell = new AbstractSafeHtmlCell<Activity>(nameRenderer)
        {
            @Override
            protected void render(SafeHtml data, Object key, SafeHtmlBuilder sb)
            {
                if (data != null)
                {
                    sb.append(data);
                }
            }
        };
        Column<Activity, Activity> nameColumn = new Column<Activity, Activity>(nameCell)
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
        addColumnStyleName(4, atr.cellTableStyle().projectColumn());
        addColumn(projectColumn);

        // TODO Column #4: Actions
    }


    public void update(Activities activities)
    {
        currentActivities = activities;
        setRowData(0, activities.getActivities());
        setRowCount(activities.getActivities().size());
    }
}
