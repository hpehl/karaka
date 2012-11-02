package name.pehl.karaka.client.activity.view;

import java.util.Date;

import name.pehl.karaka.client.activity.presenter.NewActivityPresenter;
import name.pehl.karaka.client.activity.presenter.NewActivityUiHandlers;
import name.pehl.karaka.client.model.NamedModelSuggestOracle;
import name.pehl.karaka.client.model.NamedModelSuggestion;
import name.pehl.karaka.client.project.ProjectsCache;
import name.pehl.karaka.client.resources.Resources;
import name.pehl.karaka.client.ui.Html5TextBox;
import name.pehl.karaka.shared.model.Activity;
import name.pehl.karaka.shared.model.Duration;
import name.pehl.karaka.shared.model.Project;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class NewActivityView extends ViewWithUiHandlers<NewActivityUiHandlers> implements NewActivityPresenter.MyView
{
    // ---------------------------------------------------------- inner classes

    public interface Binder extends UiBinder<Widget, NewActivityView>
    {
    }

    // ---------------------------------------------------------- private stuff

    private static final long ONE_DAY = 86400000l;
    private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd. MMM");

    final Widget widget;
    final Resources resources;
    final ActivitySuggestOracle activityOracle;

    @UiField Anchor theDayBeforeYesterday;
    @UiField Anchor yesterday;
    @UiField Anchor today;
    @UiField CalendarLink calendar;
    @UiField(provided = true) SuggestBox activity;
    @UiField(provided = true) SuggestBox project;
    @UiField DurationTextBox duration;


    // ------------------------------------------------------------------ setup

    @Inject
    public NewActivityView(final Binder binder, final Resources resources, final ProjectsCache projectsCache)
    {
        this.resources = resources;
        this.resources.navigation().ensureInjected();

        this.activityOracle = new ActivitySuggestOracle();
        Html5TextBox activityTextBox = new Html5TextBox();
        activityTextBox.setPlaceholder("Select or enter a new activity");
        this.activity = new SuggestBox(activityOracle, activityTextBox);

        NamedModelSuggestOracle<Project> projectOracle = new NamedModelSuggestOracle<Project>(projectsCache);
        Html5TextBox projectTextBox = new Html5TextBox();
        projectTextBox.setPlaceholder("Select or enter a new project");
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
    public void setUiHandlers(final NewActivityUiHandlers uiHandlers)
    {
        super.setUiHandlers(uiHandlers);
        activityOracle.setUiHandlers(uiHandlers);
    }


    // ----------------------------------------------------------- view methods

    @Override
    public void setProject(final Project project)
    {
        this.project.setValue(project.getName());
        if (getUiHandlers() != null)
        {
            getUiHandlers().onProjectSelected(project);
        }
    }


    // ------------------------------------------------------------ ui handlers

    @UiHandler("theDayBeforeYesterday")
    void onTheDayBeforeYesterdayClicked(final ClickEvent event)
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
    void onYesterdayClicked(final ClickEvent event)
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
    void onTodayClicked(final ClickEvent event)
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
    void onCalendarClicked(final ClickEvent event)
    {
        theDayBeforeYesterday.removeStyleName(resources.navigation().selectedDate());
        yesterday.removeStyleName(resources.navigation().selectedDate());
        today.removeStyleName(resources.navigation().selectedDate());
        calendar.addStyleName(resources.navigation().selectedDate());
    }


    @UiHandler("calendar")
    void onCalendarChanged(final ValueChangeEvent<Date> event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onSelectDate(event.getValue());
        }
    }


    @UiHandler("activity")
    @SuppressWarnings("unchecked")
    void onActivitySelected(final SelectionEvent<Suggestion> event)
    {
        NamedModelSuggestion<Activity> suggestion = (NamedModelSuggestion<Activity>) event.getSelectedItem();
        if (getUiHandlers() != null)
        {
            getUiHandlers().onActivitySelected(suggestion.getModel());
        }
    }


    @UiHandler("activity")
    void onActivityChanged(final ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (value != null && value.length() != 0)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onActivityChanged(value);
            }
        }
    }


    @UiHandler("project")
    @SuppressWarnings("unchecked")
    void onProjectSelected(final SelectionEvent<Suggestion> event)
    {
        NamedModelSuggestion<Project> suggestion = (NamedModelSuggestion<Project>) event.getSelectedItem();
        if (getUiHandlers() != null)
        {
            getUiHandlers().onProjectSelected(suggestion.getModel());
        }
    }


    @UiHandler("project")
    void onProjectChanged(final ValueChangeEvent<String> event)
    {
        String value = event.getValue();
        if (value != null && value.length() != 0)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onProjectChanged(value);
            }
        }
    }


    @UiHandler("duration")
    void onDurationChanged(final ValueChangeEvent<Duration> event)
    {
        if (getUiHandlers() != null)
        {
            getUiHandlers().onDurationChanged(event.getValue());
        }
    }


    @UiHandler("duration")
    void onReturnOnDuration(final KeyUpEvent event)
    {
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER)
        {
            if (getUiHandlers() != null)
            {
                getUiHandlers().onNewActivity();
            }
        }
    }
}
