package name.pehl.tire.client.activity.view;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.Date;

import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.presenter.DashboardPresenter;
import name.pehl.tire.client.activity.presenter.DashboardUiHandlers;
import name.pehl.tire.client.model.NamedModelSuggestOracle;
import name.pehl.tire.client.model.NamedModelSuggestion;
import name.pehl.tire.client.project.ProjectsCache;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.client.resources.Resources;
import name.pehl.tire.client.ui.FormatUtils;
import name.pehl.tire.client.ui.Html5TextBox;
import name.pehl.tire.client.ui.InlineHTMLWithContextMenu;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Project;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
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

    final I18n i18n;
    final Widget widget;
    final Resources resources;
    final ActivitySuggestOracle activityOracle;

    @UiField Anchor theDayBeforeYesterday;
    @UiField Anchor yesterday;
    @UiField Anchor today;
    @UiField CalendarLink calendar;
    @UiField(provided = true) SuggestBox activity;
    @UiField(provided = true) SuggestBox project;
    @UiField Html5TextBox time;
    @UiField InlineLabel header;
    @UiField InlineHTMLWithContextMenu previous;
    @UiField InlineHTMLWithContextMenu next;
    @UiField InlineHTMLWithContextMenu month;
    @UiField InlineHTMLWithContextMenu week;
    @UiField(provided = true) ActivitiesTable activitiesTable;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardView(final Binder binder, final I18n i18n, final Resources resources,
            final ActivitiesTableResources atr, final ProjectsCache projectsCache)
    {
        this.i18n = i18n;
        this.resources = resources;
        this.resources.navigation().ensureInjected();
        this.activitiesTable = new ActivitiesTable(atr);

        this.activityOracle = new ActivitySuggestOracle();
        Html5TextBox activityTextBox = new Html5TextBox();
        activityTextBox.setPlaceholder("Select an  activity");
        this.activity = new SuggestBox(activityOracle, activityTextBox);

        NamedModelSuggestOracle<Project> projectOracle = new NamedModelSuggestOracle<Project>(projectsCache);
        Html5TextBox projectTextBox = new Html5TextBox();
        projectTextBox.setPlaceholder("Select a project");
        this.project = new SuggestBox(projectOracle, projectTextBox);

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
    public void setUiHandlers(DashboardUiHandlers uiHandlers)
    {
        super.setUiHandlers(uiHandlers);
        activityOracle.setUiHandlers(uiHandlers);
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


    @Override
    public void setProject(Project project)
    {
        this.project.setValue(project.getName());
        if (getUiHandlers() != null)
        {
            getUiHandlers().onProjectSelected(project);
        }
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


    @UiHandler("activity")
    @SuppressWarnings("unchecked")
    void onActivitySelected(SelectionEvent<Suggestion> event)
    {
        NamedModelSuggestion<Activity> suggestion = (NamedModelSuggestion<Activity>) event.getSelectedItem();
        if (getUiHandlers() != null)
        {
            getUiHandlers().onActivitySelected(suggestion.getModel());
        }
    }


    @UiHandler("activity")
    void onActivityEntered(ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (value != null && value.length() != 0)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onActivityEntered(value);
            }
        }
    }


    @UiHandler("project")
    @SuppressWarnings("unchecked")
    void onProjectSelected(SelectionEvent<Suggestion> event)
    {
        NamedModelSuggestion<Project> suggestion = (NamedModelSuggestion<Project>) event.getSelectedItem();
        if (getUiHandlers() != null)
        {
            getUiHandlers().onProjectSelected(suggestion.getModel());
        }
    }


    @UiHandler("project")
    void onProjectEntered(ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (value != null && value.length() != 0)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onProjectEntered(value);
            }
        }
    }


    @UiHandler("time")
    void onTimeEntered(ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (value != null && value.length() != 0)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onTimeEntered(value);
            }
        }
    }


    @UiHandler("time")
    void onReturnOnTime(KeyUpEvent event)
    {
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onNewActivity();
            }
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
