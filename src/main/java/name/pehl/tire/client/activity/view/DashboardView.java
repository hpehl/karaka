package name.pehl.tire.client.activity.view;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.Date;

import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.activity.presenter.DashboardUiHandlers;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.client.ui.InlineHTMLWithContextMenu;
import name.pehl.tire.shared.model.Activities;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

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
    @UiField SimplePanel lookupActivityHolder;
    @UiField SimplePanel lookupProjectHolder;
    @UiField InlineLabel header;
    @UiField InlineHTMLWithContextMenu previous;
    @UiField InlineHTMLWithContextMenu next;
    @UiField InlineHTMLWithContextMenu month;
    @UiField InlineHTMLWithContextMenu week;
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


    @Override
    public void setInSlot(Object slot, Widget widget)
    {
        if (slot == DashboardPresenter.SLOT_Lookup_Activity)
        {
            lookupActivityHolder.setWidget(widget);
        }
        else if (slot == DashboardPresenter.SLOT_Lookup_Project)
        {
            lookupProjectHolder.setWidget(widget);
        }
        else
        {
            super.setInSlot(slot, widget);
        }
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
                .append(FormatUtils.dateDuration(activities.getStart(), activities.getEnd()));
        header.setText(text.toString());
        header.setTitle(title.toString());

        // Update navigation
        if (activities.getUnit() == WEEK)
        {
            previous.setTitle("Previous week");
            next.setTitle("Next week");
        }
        else if (activities.getUnit() == MONTH)
        {
            previous.setTitle("Previous month");
            next.setTitle("Next month");
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


    @UiHandler("month")
    public void onMonthClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onCurrentMonth();
        }
    }


    @UiHandler("month")
    public void onMonthContextMenu(ContextMenuEvent event)
    {
        if (getUiHandlers() != null)
        {
            event.preventDefault();
            getUiHandlers().onSelectMonth(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
        }
    }


    @UiHandler("week")
    public void onWeekClicked(ClickEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onCurrentWeek();
        }
    }


    @UiHandler("week")
    public void onWeekContextMenu(ContextMenuEvent event)
    {
        if (getUiHandlers() != null)
        {
            event.preventDefault();
            getUiHandlers().onSelectWeek(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
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
    public void onActivityAction(ActivityActionEvent event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onActivityAction(event.getActivity(), event.getAction());
        }
    }
}
