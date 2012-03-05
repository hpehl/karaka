package name.pehl.tire.client.activity.view;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.Activity;
import name.pehl.tire.client.activity.presenter.RecentActivitiesPresenter;
import name.pehl.tire.client.activity.presenter.RecentActivitiesUiHandlers;
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
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 14:58:34 +0100 (Do, 23. Dez 2010) $ $Revision: 142
 *          $
 */
public class RecentActivitiesView extends ViewWithUiHandlers<RecentActivitiesUiHandlers> implements
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
    public void mask()
    {
    }


    @Override
    public void unmask()
    {
    }


    @Override
    public void updateActivities(Activities activities)
    {
        // Update header
        StringBuilder text = new StringBuilder("Recent Activities");
        StringBuilder tooltip = new StringBuilder();
        if (activities.getUnit() == WEEK)
        {
            String cw = "CW " + activities.getWeek() + " / " + activities.getYear();
            text.append(": ").append(cw);
            tooltip.append(cw);
        }
        else if (activities.getUnit() == MONTH)
        {
            String monthKey = "month_" + activities.getMonth();
            String month = i18n.enums().getString(monthKey) + " " + activities.getYear();
            text.append(": ").append(month);
            tooltip.append(month);
        }
        tooltip.append(" - ").append(FormatUtils.hours(activities.getMinutes())).append(" - ")
                .append(FormatUtils.date(activities.getStart())).append(" - ")
                .append(FormatUtils.date(activities.getEnd()));
        header.setText(text.toString());
        header.setTitle(tooltip.toString());

        // Update navigation
        // TODO Define colors as resources / constants
        lastMonth.fill("#3d3d3d");
        lastWeek.fill("#3d3d3d");
        currentMonth.fill("#3d3d3d");
        currentWeek.fill("#3d3d3d");
        if (activities.getUnit() == WEEK)
        {
            previous.setTitle("Previous week");
            next.setTitle("Next week");
            if (activities.getWeekDiff() == -1)
            {
                lastWeek.fill("#1b92a8");
            }
            else if (activities.getWeekDiff() == 0)
            {
                currentWeek.fill("#1b92a8");
            }
        }
        else if (activities.getUnit() == MONTH)
        {
            previous.setTitle("Previous month");
            next.setTitle("Next month");
            if (activities.getMonthDiff() == -1)
            {
                lastMonth.fill("#1b92a8");
            }
            else if (activities.getMonthDiff() == 0)
            {
                currentMonth.fill("#1b92a8");
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


    @UiHandler("activitiesTable")
    public void onActivityAction(ActivityActionEvent event)
    {
        if (getUiHandlers() != null)
        {
            int rowIndex = event.getRowIndex();
            Activity activity = event.getActivity();
            switch (event.getAction())
            {
                case EDIT:
                    getUiHandlers().onEdit(rowIndex, activity);
                    break;
                case COPY:
                    getUiHandlers().onCopy(rowIndex, activity);
                    break;
                case GOON:
                    getUiHandlers().onGoon(rowIndex, activity);
                    break;
                case DELETE:
                    getUiHandlers().onDelete(rowIndex, activity);
                    break;
                default:
                    break;
            }
        }
    }
}
