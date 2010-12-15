package name.pehl.tire.client.activity;

import static com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED;
import static name.pehl.tire.client.activity.Unit.MONTH;
import static name.pehl.tire.client.activity.Unit.WEEK;

import java.util.List;

import name.pehl.tire.client.resources.CellTableResources;
import name.pehl.tire.client.tag.Tag;
import name.pehl.tire.client.ui.FormatUtils;
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

    @UiField InlineLabel rangeInfo;
    @UiField InlineLabel previous;
    @UiField InlineLabel next;
    @UiField InlineLabel lastMonth;
    @UiField InlineLabel lastWeek;
    @UiField InlineLabel currentMonth;
    @UiField InlineLabel currentWeek;
    @UiField(provided = true) CellTable<Activity> activitiesTable;
    // @formatter:on

    private final Widget widget;
    private final CellTableResources ctr;
    private Activities currentActivities;


    @Inject
    public RecentActivitiesView(final CellTableResources ctr)
    {
        this.ctr = ctr;
        this.ctr.cellTableStyle().ensureInjected();
        activitiesTable = new CellTable<Activity>(Integer.MAX_VALUE, this.ctr);
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
                    return currentActivities.size() + " days";
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
    public void updateActivities(Activities activities, ActivitiesNavigationData and)
    {
        currentActivities = activities;
        StringBuilder builder = new StringBuilder();
        builder.append(FormatUtils.format(currentActivities.getStart().getDate())).append(" - ")
                .append(FormatUtils.format(currentActivities.getEnd().getDate()));
        rangeInfo.setText(builder.toString());
        activitiesTable.setRowData(0, activities.getActivities());
        activitiesTable.setRowCount(activities.getActivities().size());
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
