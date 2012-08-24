package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.INFO;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.NEW;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;

import java.util.List;
import java.util.logging.Logger;

import name.pehl.tire.client.activity.dispatch.DeleteActivityAction;
import name.pehl.tire.client.activity.dispatch.DeleteActivityResult;
import name.pehl.tire.client.activity.dispatch.SaveActivityAction;
import name.pehl.tire.client.activity.dispatch.SaveActivityResult;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent.RunningActivityLoadedHandler;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Project;
import name.pehl.tire.shared.model.Tag;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

/**
 * <p>
 * Singelton class which controls the current activity and the list of displayed
 * activities. This is the <strong>only</strong> place where the current
 * activity can be resumed, started, stopped, changed, deleted or copied. All
 * relevant presenters must notify this controller with appropriate events. In
 * return this controller responds with events to notify other presenters.
 * </p>
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>{@linkplain RunningActivityLoadedEvent}</li>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain ActivityActionEvent}</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain TickEvent}</li>
 * <li>{@linkplain ActivityChangedEvent}</li>
 * <li>{@linkplain ShowMessageEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>tbd</li>
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 13:52:44 +0100 (Do, 23. Dez 2010) $ $Revision: 192
 *          $
 */
public class ActivityController implements RepeatingCommand, HasHandlers, RunningActivityLoadedHandler,
        ActivitiesLoadedHandler, ActivityActionHandler
{
    // ------------------------------------------------------- (static) members

    static final long ONE_MINUTE_IN_MILLIS = 60 * 1000;
    static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    static final Logger logger = Logger.getLogger(ActivityController.class.getName());

    final EventBus eventBus;
    final Scheduler scheduler;
    final DispatchAsync dispatcher;
    final StartAndResumeCallback startCallback;
    final StartAndResumeCallback resumeCallback;

    /**
     * Should I tick?
     */
    boolean ticking;

    /**
     * The currently running actvity. Null if no activity is running.
     */
    Activity runningActivity;

    /**
     * The currently displayed activities
     */
    Activities activities;


    // ------------------------------------------------------------------ setup

    @Inject
    public ActivityController(final EventBus eventBus, final Scheduler scheduler, final DispatchAsync dispatcher)
    {
        super();
        this.eventBus = eventBus;
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.startCallback = new StartAndResumeCallback(eventBus, STARTED);
        this.resumeCallback = new StartAndResumeCallback(eventBus, RESUMED);
        registerEventListeners();
    }


    // --------------------------------------------------------- event handling

    @Override
    public void fireEvent(GwtEvent<?> event)
    {
        eventBus.fireEventFromSource(event, this);
    }


    void registerEventListeners()
    {
        eventBus.addHandler(RunningActivityLoadedEvent.getType(), this);
        eventBus.addHandler(ActivitiesLoadedEvent.getType(), this);
        eventBus.addHandler(ActivityActionEvent.getType(), this);
    }


    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        runningActivity = event.getActivity();
    }


    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        activities = event.getActivities();
    }


    @Override
    public void onActivityAction(ActivityActionEvent event)
    {
        Action action = event.getAction();
        Activity activity = event.getActivity();
        switch (action)
        {
            case SAVE:
                save(activity);
                break;
            case COPY:
                copy(activity);
                break;
            case START_STOP:
                if (activity.isRunning())
                {
                    stop(activity);
                }
                else
                {
                    start(activity);
                }
                break;
            case DELETE:
                delete(activity);
                break;
            default:
                break;
        }
    }


    // -------------------------------------------------- activity crud methods

    void save(final Activity activityToSave)
    {
        logger.fine("About to save " + activityToSave);
        dispatcher.execute(new SaveActivityAction(activityToSave), new TireCallback<SaveActivityResult>(eventBus)
        {
            @Override
            public void onSuccess(SaveActivityResult result)
            {
                Activity savedActivity = result.getStoredActivity();
                updateActivities(activityToSave, savedActivity);
                ActivityChangedEvent.fire(ActivityController.this, CHANGED, savedActivity, activities);
                ShowMessageEvent.fire(ActivityController.this,
                        new Message(INFO, "Activity \"" + savedActivity.getName() + "\" saved", true));
                checkAndrefreshProjectsAndTags(activityToSave);
            }
        });
    }


    void copy(Activity activity)
    {
        logger.fine("About to copy " + activity);
        Activity plusOneDay = activity.plus(ONE_DAY_IN_MILLIS);
        dispatcher.execute(new SaveActivityAction(plusOneDay), new TireCallback<SaveActivityResult>(eventBus)
        {
            @Override
            public void onSuccess(SaveActivityResult result)
            {
                Activity copiedActivity = result.getStoredActivity();
                // activities.insert(copiedActivity);
                ActivityChangedEvent.fire(ActivityController.this, NEW, copiedActivity, activities);
                ShowMessageEvent.fire(ActivityController.this,
                        new Message(INFO, "Activity \"" + copiedActivity.getName() + "\" added", true));
            }
        });
    }


    void start(final Activity activity)
    {
        logger.info("About to start " + activity);
        if (activity.isStopped())
        {
            // if there's currently another activity running, stop it.
            if (runningActivity != null && runningActivity.isRunning() && !runningActivity.equals(activity))
            {
                logger.info("Stopping currently running " + runningActivity);
                ticking = false;
                runningActivity.stop();
                dispatcher.execute(new SaveActivityAction(runningActivity), new TireCallback<SaveActivityResult>(
                        eventBus)
                {
                    @Override
                    public void onSuccess(SaveActivityResult result)
                    {
                        runningActivity = null;
                        Activity stoppedActivity = result.getStoredActivity();
                        logger.info("Successfully stopped " + stoppedActivity);
                        activities.remove(activity);
                        // activities.insert(stoppedActivity);
                        start(activity);
                    }
                });
            }
            else
            {
                // TODO Change logic for latestActivity. It is not the last
                // activity from activities, but rather the last started
                // activity!
                Activity latestActivity = activities != null ? activities.activities().first() : null;
                if (activity.isToday() && activity.equals(latestActivity))
                {
                    activity.resume();
                    logger.info("Resuming " + activity);
                    dispatcher.execute(new SaveActivityAction(activity), resumeCallback);
                }
                else
                {
                    Activity newActivity = null;
                    if (activity.isTransient())
                    {
                        // The parameter is the new transient activity we want
                        // to start.
                        newActivity = activity;
                    }
                    else
                    {
                        // The parameter is an existing activity. We have to
                        // copy this activity.
                        newActivity = activity.copy();
                        logger.info("Copy " + activity + " and start as a new " + newActivity);
                    }
                    newActivity.start();
                    dispatcher.execute(new SaveActivityAction(newActivity), startCallback);
                }
            }
        }
        else
        {
            logger.info(activity + " already running");
        }
    }


    void stop(final Activity activity)
    {
        logger.info("About to stop " + activity);
        if (activity != runningActivity)
        {
            throw new IllegalStateException(
                    "Trying to stop activity which is not the same as the internal running activity: "
                            + runningActivity + "!");
        }
        if (runningActivity.isRunning())
        {
            ticking = false;
            runningActivity.stop();
            dispatcher.execute(new SaveActivityAction(activity), new TireCallback<SaveActivityResult>(eventBus)
            {
                @Override
                public void onSuccess(SaveActivityResult result)
                {
                    runningActivity = null;
                    Activity stoppedActivity = result.getStoredActivity();
                    activities.remove(activity);
                    // activities.insert(stoppedActivity);
                    ActivityChangedEvent.fire(ActivityController.this, STOPPED, stoppedActivity, activities);
                    ShowMessageEvent.fire(ActivityController.this,
                            new Message(INFO, "Activity \"" + stoppedActivity.getName() + "\" stopped", true));
                }
            });
        }
        else
        {
            logger.info(runningActivity + " already stopped");
        }
    }


    void delete(final Activity activity)
    {
        logger.info("About to delete activity " + activity);
        if (activity.isRunning())
        {
            runningActivity = null;
            // tickCommand.stop();
            activity.stop();
        }
        dispatcher.execute(new DeleteActivityAction(activity), new TireCallback<DeleteActivityResult>(eventBus)
        {
            @Override
            public void onSuccess(DeleteActivityResult result)
            {
                activities.remove(activity);
                // getView().updateActivities(activities);
                ActivityChangedEvent.fire(ActivityController.this, DELETE, activity, activities);
                ShowMessageEvent.fire(ActivityController.this, new Message(INFO, "Activity \"" + activity.getName()
                        + "\" deleted", true));
            }
        });
    }


    private void updateActivities(Activity activityBefore, Activity activityAfter)
    {
        // if (activities.matchingRange(savedActivity))
        // {
        //
        // }
        // activities.remove(activityToSave);
        // activities.insert(savedActivity);

    }


    /**
     * Called after an activity was saved successfully. If the unsaved activity
     * contained transient project or tags, we have to refresh our local caches!
     * 
     * @param activity
     *            The activity before it was saved
     */
    private void checkAndrefreshProjectsAndTags(Activity activity)
    {
        Project project = activity.getProject();
        if (project != null && project.isTransient())
        {
            // TODO Refresh projects cache
        }
        List<Tag> tags = activity.getTags();
        for (Tag tag : tags)
        {
            if (tag.isTransient())
            {
                // TODO Refresh tags cache
                break;
            }
        }
    }


    // ------------------------------------------------------------------- tick

    @Override
    public boolean execute()
    {
        if (ticking)
        {
            logger.info("Tick for " + runningActivity);
            runningActivity.tick();
            dispatcher.execute(new SaveActivityAction(runningActivity), new TireCallback<SaveActivityResult>(eventBus)
            {
                @Override
                public void onSuccess(SaveActivityResult result)
                {
                    runningActivity = result.getStoredActivity();
                    TickEvent.fire(ActivityController.this, runningActivity);
                }
            });
        }
        return ticking;
    }

    // ---------------------------------------------------------- inner classes

    class StartAndResumeCallback extends TireCallback<SaveActivityResult>
    {
        final ChangeAction action;


        StartAndResumeCallback(EventBus eventBus, ChangeAction action)
        {
            super(eventBus);
            this.action = action;
        }


        @Override
        public void onSuccess(SaveActivityResult result)
        {
            runningActivity = result.getStoredActivity();
            // update(runningActivity);
            ActivityChangedEvent.fire(ActivityController.this, action, runningActivity, activities);
            ShowMessageEvent.fire(ActivityController.this, new Message(INFO, "Activity \"" + runningActivity.getName()
                    + "\" " + action.name().toLowerCase(), true));
            // tickCommand.start(runningActivity);
            // TODO call checkAndrefreshProjectsAndTags(activity);
        }
    }
}
