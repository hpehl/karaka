package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.SEVERE;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.SAVE;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.client.activity.dispatch.FindActivityAction;
import name.pehl.tire.client.activity.dispatch.FindActivityResult;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.model.Highlighter;
import name.pehl.tire.client.model.NamedModelSuggestion;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Duration;
import name.pehl.tire.shared.model.Project;
import name.pehl.tire.shared.model.Time;

import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ShowMessageEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>{@linkplain FindActivityAction}
 * </ul>
 */
public class NewActivityPresenter extends PresenterWidget<NewActivityPresenter.MyView> implements NewActivityUiHandlers
{
    // ---------------------------------------------------------- inner classes

    public interface MyView extends View, HasUiHandlers<NewActivityUiHandlers>
    {
        void setProject(Project project);
    }

    // ------------------------------------------------------- (static) members

    static final Logger logger = Logger.getLogger(NewActivityPresenter.class.getName());
    final DispatchAsync dispatcher;

    /**
     * The selected date
     */
    Date selectedDate;

    /**
     * The selected activity in the suggestbox
     */
    Activity selectedActivity;

    /**
     * The entered activity name in the suggestbox
     */
    String enteredActivity;

    /**
     * The selected project in the suggestbox
     */
    Project selectedProject;

    /**
     * The entered project name in the suggestbox
     */
    String enteredProject;

    /**
     * The duration entered in the textbox
     */
    Duration enteredDuration;


    // ------------------------------------------------------------------ setup

    @Inject
    public NewActivityPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher)
    {
        super(eventBus, view);
        this.dispatcher = dispatcher;
        getView().setUiHandlers(this);
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onSelectDate(final Date date)
    {
        this.selectedDate = date;
    }


    @Override
    public void onFindActivity(final Request request, final Callback callback)
    {
        final String query = request.getQuery();
        final Highlighter highlighter = new Highlighter(query);
        dispatcher.execute(new FindActivityAction(query), new TireCallback<FindActivityResult>(getEventBus())
        {
            @Override
            public void onSuccess(final FindActivityResult result)
            {
                List<Activity> activities = result.getActivities();
                if (!activities.isEmpty())
                {
                    List<NamedModelSuggestion<Activity>> suggestions = new ArrayList<NamedModelSuggestion<Activity>>();
                    for (Activity activity : activities)
                    {
                        StringBuilder displayString = new StringBuilder();
                        displayString.append(activity.getName());
                        if (activity.getDescription() != null)
                        {
                            displayString.append(": ").append(activity.getDescription());
                        }
                        NamedModelSuggestion<Activity> suggestion = new NamedModelSuggestion<Activity>(activity,
                                activity.getName(), highlighter.highlight(displayString.toString()));
                        suggestions.add(suggestion);
                    }
                    callback.onSuggestionsReady(request, new Response(suggestions));
                }
            }


            @Override
            public void onFailure(final Throwable caught)
            {
                // Just log
                logger.log(Level.WARNING, "No activities found for " + query + ": " + caught.getMessage(), caught);
            }
        });
    }


    @Override
    public void onActivitySelected(final Activity activity)
    {
        enteredActivity = null;
        selectedActivity = activity;
        if (activity.getProject() != null)
        {
            getView().setProject(activity.getProject());
        }
    }


    @Override
    public void onActivityChanged(final String name)
    {
        selectedActivity = null;
        enteredActivity = name;
    }


    @Override
    public void onProjectSelected(final Project project)
    {
        enteredProject = null;
        selectedProject = project;
    }


    @Override
    public void onProjectChanged(final String name)
    {
        selectedProject = null;
        enteredProject = name;
    }


    @Override
    public void onDurationChanged(final Duration duration)
    {
        enteredDuration = duration;
    }


    @Override
    @SuppressWarnings("deprecation")
    public void onNewActivity()
    {
        // 1. Activity
        Activity activity = null;
        if (enteredActivity != null)
        {
            activity = new Activity(enteredActivity);
        }
        else if (selectedActivity != null)
        {
            activity = selectedActivity;
        }
        else
        {
            ShowMessageEvent.fire(this, new Message(SEVERE, "Please specify a name for the activity", true));
            return;
        }

        // 2. Project
        Project project = null;
        if (enteredProject != null)
        {
            project = new Project(enteredProject);
        }
        else if (selectedProject != null)
        {
            project = selectedProject;
        }
        activity.setProject(project);

        // 3. Duration
        if (enteredDuration == null || enteredDuration.isZero())
        {
            ActivityActionEvent.fire(this, START_STOP, activity);
        }
        else
        {
            if (!activity.isTransient())
            {
                activity = activity.copy();
                activity.setProject(project);
            }
            // selectedDate must not be changed!
            Date now = new Date();
            Date start = selectedDate;
            if (start == null)
            {
                start = now;
            }
            else
            {
                start.setHours(now.getHours());
                start.setMinutes(now.getMinutes());
                start.setSeconds(now.getSeconds());
            }
            // This is the only valid use case to call
            // Activity.setMinutes(long) directly. On the server side it is
            // recognized that the activity is stopped and that there's a
            // start time, a value for minutes but no end time. In this case
            // the end time is calculated on the server
            activity.setStart(new Time(start));
            activity.setEnd(null);
            activity.setDuration(enteredDuration);
            ActivityActionEvent.fire(this, SAVE, activity);
        }
    }
}
