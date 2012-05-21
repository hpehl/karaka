package name.pehl.tire.client.activity.view;

import java.util.Date;

import name.pehl.tire.client.activity.event.ProcessActivityEvent;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.activity.presenter.DashboardUiHandlers;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.client.ui.SvgPath;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

public class DashboardView extends ViewWithUiHandlers<DashboardUiHandlers> implements DashboardPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, DashboardView>
    {
    }

    // ---------------------------------------------------------- private stuff

    private static final long ONE_DAY = 86400000l;
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd. MMM");

    private final I18n i18n;
    private final Widget widget;
    private final Resources resources;

    @UiField Anchor theDayBeforeYesterday;
    @UiField Anchor yesterday;
    @UiField Anchor today;
    @UiField CalendarLink calendar;
    @UiField InlineLabel header;
    @UiField SvgPath previous;
    @UiField SvgPath next;
    @UiField SvgPath selectMonth;
    @UiField SvgPath selectWeek;
    @UiField SvgPath currentMonth;
    @UiField SvgPath currentWeek;
    @UiField(provided = true) ActivitiesTable activitiesTable;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardView(final Binder binder, final I18n i18n, final Resources resources,
            final ActivitiesTableResources atr)
    {
        this.i18n = i18n;
        this.resources = resources;
        this.resources.navigation().ensureInjected();
        this.activitiesTable = new ActivitiesTable(atr);
        this.widget = binder.createAndBindUi(this);
        this.today.addStyleName(resources.navigation().selectedDate());
        this.yesterday.setText(DATE_FORMAT.format(new Date(System.currentTimeMillis() - ONE_DAY)));
        this.theDayBeforeYesterday.setText(DATE_FORMAT.format(new Date(System.currentTimeMillis() - 2 * ONE_DAY)));
    }


    @Override
    public Widget asWidget()
    {
        return widget;
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void updateActivities(Activities activities)
    {
        // Update header
        StringBuilder text = new StringBuilder();
        StringBuilder title = new StringBuilder();
        if (activities.getUnit() == WEEK)
        {
            String cw = "CW " + activities.getWeek() + " / " + activities.getYear();
            text.append(cw);
            title.append(cw);
        }
        else if (activities.getUnit() == MONTH)
        {
            String monthKey = "month_" + activities.getMonth();
            String month = i18n.enums().getString(monthKey) + " " + activities.getYear();
            text.append(month);
            title.append(month);
        }
        title.append(" - ").append(FormatUtils.hours(activities.getMinutes())).append(" - ")
                .append(FormatUtils.date(activities.getStart())).append(" - ")
                .append(FormatUtils.date(activities.getEnd()));
        header.setText(text.toString());
        header.setTitle(title.toString());

        // Update navigation
        // TODO Define colors as resources / constants
        selectMonth.fill("#3d3d3d");
        selectWeek.fill("#3d3d3d");
        currentMonth.fill("#3d3d3d");
        currentWeek.fill("#3d3d3d");
        if (activities.getUnit() == WEEK)
        {
            previous.setTitle("Previous week");
            next.setTitle("Next week");
            if (activities.getWeekDiff() == 0)
            {
                currentWeek.fill("#1b92a8");
            }
        }
        else if (activities.getUnit() == MONTH)
        {
            previous.setTitle("Previous month");
            next.setTitle("Next month");
            if (activities.getMonthDiff() == 0)
            {
                currentMonth.fill("#1b92a8");
            }
        }

        // Update table
        activitiesTable.update(activities);
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("theDayBeforeYesterday")
    void onTheDayBeforeYesterdayClicked(ClickEvent event)
    {
        theDayBeforeYesterday.addStyleName(resources.navigation().selectedDate());
        yesterday.removeStyleName(resources.navigation().selectedDate());
        today.removeStyleName(resources.navigation().selectedDate());
        calendar.removeStyleName(resources.navigation().selectedDate());
        calendar.reset();
        if (getUiHandlers() != null)
        {
            Date date = new Date(System.currentTimeMillis() - 2 * ONE_DAY);
            getUiHandlers().onSelectDate(date);
        }
    }


    @UiHandler("yesterday")
    void onYesterdayClicked(ClickEvent event)
    {
        theDayBeforeYesterday.removeStyleName(resources.navigation().selectedDate());
        yesterday.addStyleName(resources.navigation().selectedDate());
        today.removeStyleName(resources.navigation().selectedDate());
        calendar.removeStyleName(resources.navigation().selectedDate());
        calendar.reset();
        if (getUiHandlers() != null)
        {
            Date date = new Date(System.currentTimeMillis() - ONE_DAY);
            getUiHandlers().onSelectDate(date);
        }
    }


    @UiHandler("today")
    void onTodayClicked(ClickEvent event)
    {
        theDayBeforeYesterday.removeStyleName(resources.navigation().selectedDate());
        yesterday.removeStyleName(resources.navigation().selectedDate());
        today.addStyleName(resources.navigation().selectedDate());
        calendar.removeStyleName(resources.navigation().selectedDate());
        calendar.reset();
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectDate(new Date());
        }
    }


    @UiHandler("calendar")
    void onCalendarClicked(ClickEvent event)
    {
        theDayBeforeYesterday.removeStyleName(resources.navigation().selectedDate());
        yesterday.removeStyleName(resources.navigation().selectedDate());
        today.removeStyleName(resources.navigation().selectedDate());
        calendar.addStyleName(resources.navigation().selectedDate());
    }


    @UiHandler("calendar")
    void onCalendarChanged(ValueChangeEvent<Date> event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectDate(event.getValue());
        }
    }


    @UiHandler("currentMonth")
    public void onCurrentMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onCurrentMonth();
        }
    }


    @UiHandler("currentWeek")
    public void onCurrentWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onCurrentWeek();
        }
    }


    @UiHandler("selectMonth")
    public void onSelectMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectMonth(event.getClientX(), event.getClientY());
        }
    }


    @UiHandler("selectWeek")
    public void onSelectWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectWeek(event.getClientX(), event.getClientY());
        }
    }


    @UiHandler("previous")
    public void onPreviousClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onPrevious();
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
    public void onActivityAction(ProcessActivityEvent event)
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
