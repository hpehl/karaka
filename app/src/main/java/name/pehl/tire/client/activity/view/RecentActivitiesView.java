package name.pehl.tire.client.activity.view;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;
import static name.pehl.tire.model.TimeUnit.MONTH;
import static name.pehl.tire.model.TimeUnit.WEEK;

import java.util.List;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.client.activity.presenter.ActivitiesNavigationUiHandlers;
import name.pehl.tire.client.activity.presenter.RecentActivitiesPresenter;
import name.pehl.tire.client.resources.CellTableResources;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.tag.Tag;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.client.ui.SvgPath;
import name.pehl.tire.model.Status;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.AbstractSafeHtmlRenderer;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

/**
 * @author $Author$
 * @version $Date$ $Revision: 142
 *          $
 */
public class RecentActivitiesView extends ViewWithUiHandlers<ActivitiesNavigationUiHandlers> implements
        RecentActivitiesPresenter.MyView
{
    interface ActivityTemplates extends SafeHtmlTemplates
    {
        @Template("<span title=\"{1}\">{0}</span>")
        SafeHtml nameDescription(String name, String description);


        @Template("<mark>{0}</mark>")
        SafeHtml tag(String name);
    }

    private static final ActivityTemplates ACTIVITY_TEMPLATES = GWT.create(ActivityTemplates.class);

    // @formatter:off
    interface RecentActivitiesUi extends UiBinder<Widget, RecentActivitiesView> {}
    private static RecentActivitiesUi uiBinder = GWT.create(RecentActivitiesUi.class);

    @UiField InlineLabel header;
    @UiField SvgPath previous;
    @UiField SvgPath next;
    @UiField SvgPath lastMonth;
    @UiField SvgPath lastWeek;
    @UiField SvgPath currentMonth;
    @UiField SvgPath currentWeek;
    @UiField(provided = true) CellTable<Activity> activitiesTable;
    // @formatter:on

    private final I18n i18n;
    private final Widget widget;
    private final Resources resources;
    private final CellTableResources ctr;
    private Activities currentActivities;


    @Inject
    public RecentActivitiesView(final I18n i18n, final Resources resources, final CellTableResources ctr)
    {
        this.i18n = i18n;
        this.resources = resources;
        this.resources.navigation().ensureInjected();
        this.ctr = ctr;
        this.ctr.cellTableStyle().ensureInjected();
        activitiesTable = new CellTable<Activity>(Integer.MAX_VALUE, this.ctr, new ActivityKeyProvider());
        activitiesTable.setRowCount(0);
        activitiesTable.setKeyboardSelectionPolicy(DISABLED);
        activitiesTable.setRowStyles(new RowStyles<Activity>()
        {
            @Override
            public String getStyleNames(Activity row, int rowIndex)
            {
                if (row.getStatus() == Status.RUNNING)
                {
                    return ctr.cellTableStyle().activeActivity();
                }
                return null;
            }
        });
        addColumns(activitiesTable);
        widget = uiBinder.createAndBindUi(this);
    }


    private void addColumns(CellTable<Activity> activities)
    {
        // Column #0: Start date
        TextColumn<Activity> startColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.format(activity.getStart());
            }
        };
        activities.addColumnStyleName(0, ctr.cellTableStyle().startColumn());
        activities.addColumn(startColumn, null, new TextHeader(null)
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

        // Column #1: Duration
        // TODO Right align as soon as
        // http://code.google.com/p/google-web-toolkit/issues/detail?id=5623 is
        // released
        TextColumn<Activity> durationColumn = new TextColumn<Activity>()
        {
            @Override
            public String getValue(Activity activity)
            {
                return FormatUtils.inHours(activity.getMinutes());
            }
        };
        activities.addColumnStyleName(1, ctr.cellTableStyle().durationColumn());
        activities.addColumn(durationColumn, null, new TextHeader(null)
        {
            @Override
            public String getValue()
            {
                if (currentActivities != null)
                {
                    return FormatUtils.inHours(currentActivities.getMinutes());
                }
                return null;
            }
        });

        // Column #2: Name, Description & Tags
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
        activities.addColumnStyleName(2, ctr.cellTableStyle().nameColumn());
        activities.addColumn(nameColumn);

        // Column #3: Project
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
        activities.addColumnStyleName(3, ctr.cellTableStyle().projectColumn());
        activities.addColumn(projectColumn);

        // TODO Column #4: Actions
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateActivities(Activities activities)
    {
        currentActivities = activities;

        StringBuilder tooltip = new StringBuilder();
        if (activities.getUnit() == WEEK)
        {
            tooltip.append("CW ").append(currentActivities.getWeek()).append(" / ").append(currentActivities.getYear());
        }
        else if (activities.getUnit() == MONTH)
        {
            String monthKey = "month_" + currentActivities.getMonth();
            tooltip.append(i18n.enums().getString(monthKey)).append(" ").append(currentActivities.getYear());
        }
        tooltip.append(" - ").append(FormatUtils.inHours(currentActivities.getMinutes())).append(" - ")
                .append(FormatUtils.format(currentActivities.getStart())).append(" - ")
                .append(FormatUtils.format(currentActivities.getEnd()));
        header.setTitle(tooltip.toString());

        activitiesTable.setRowData(0, activities.getActivities());
        activitiesTable.setRowCount(activities.getActivities().size());

        lastMonth.setColor("#3d3d3d");
        lastWeek.setColor("#3d3d3d");
        currentMonth.setColor("#3d3d3d");
        currentWeek.setColor("#3d3d3d");
        if (currentActivities.getUnit() == WEEK)
        {
            previous.setTitle("Previous week");
            next.setTitle("Next week");
            if (activities.getWeekDiff() == -1)
            {
                lastWeek.setColor("#1b92a8");
            }
            else if (activities.getWeekDiff() == 0)
            {
                currentWeek.setColor("#1b92a8");
            }
        }
        else if (currentActivities.getUnit() == MONTH)
        {
            previous.setTitle("Previous month");
            next.setTitle("Next month");
            if (activities.getMonthDiff() == -1)
            {
                lastMonth.setColor("#1b92a8");
            }
            else if (activities.getMonthDiff() == 0)
            {
                currentMonth.setColor("#1b92a8");
            }
        }
    }


    @UiHandler("previous")
    public void onPreviousClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onPrev();
        }
    }


    @UiHandler("lastMonth")
    public void onLastMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(MONTH);
            getUiHandlers().onPrev();
        }
    }


    @UiHandler("lastWeek")
    public void onLastWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(WEEK);
            getUiHandlers().onPrev();
        }
    }


    @UiHandler("currentMonth")
    public void onCurrentMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(MONTH);
            getUiHandlers().onCurrent();
        }
    }


    @UiHandler("currentWeek")
    public void onCurrentWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(WEEK);
            getUiHandlers().onCurrent();
        }
    }


    @UiHandler("next")
    public void onNextClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onNext();
        }
    }
}
