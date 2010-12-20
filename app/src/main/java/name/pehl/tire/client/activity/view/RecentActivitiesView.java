package name.pehl.tire.client.activity.view;

import static name.pehl.tire.model.TimeUnit.MONTH;
import static name.pehl.tire.model.TimeUnit.WEEK;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.presenter.ActivitiesNavigationUiHandlers;
import name.pehl.tire.client.activity.presenter.RecentActivitiesPresenter;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.client.ui.SvgPath;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
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
    @UiField(provided = true) ActivitiesTable activitiesTable;
    // @formatter:on

    private final I18n i18n;
    private final Widget widget;


    @Inject
    public RecentActivitiesView(final I18n i18n, final ActivitiesTableResources atr)
    {
        this.i18n = i18n;
        this.activitiesTable = new ActivitiesTable(atr);
        this.widget = uiBinder.createAndBindUi(this);
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    @Override
    public void updateActivities(Activities activities)
    {
        // Update header
        StringBuilder tooltip = new StringBuilder();
        if (activities.getUnit() == WEEK)
        {
            tooltip.append("CW ").append(activities.getWeek()).append(" / ").append(activities.getYear());
        }
        else if (activities.getUnit() == MONTH)
        {
            String monthKey = "month_" + activities.getMonth();
            tooltip.append(i18n.enums().getString(monthKey)).append(" ").append(activities.getYear());
        }
        tooltip.append(" - ").append(FormatUtils.hours(activities.getMinutes())).append(" - ")
                .append(FormatUtils.date(activities.getStart())).append(" - ")
                .append(FormatUtils.date(activities.getEnd()));
        header.setTitle(tooltip.toString());

        // Update navigation
        // TODO Define colors as resources / constants
        lastMonth.setColor("#3d3d3d");
        lastWeek.setColor("#3d3d3d");
        currentMonth.setColor("#3d3d3d");
        currentWeek.setColor("#3d3d3d");
        if (activities.getUnit() == WEEK)
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
        else if (activities.getUnit() == MONTH)
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

        // Update table
        activitiesTable.update(activities);
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
    public void onRecentMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(MONTH);
            getUiHandlers().onRelative(-1);
        }
    }


    @UiHandler("lastWeek")
    public void onRecentWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().changeUnit(WEEK);
            getUiHandlers().onRelative(-1);
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
