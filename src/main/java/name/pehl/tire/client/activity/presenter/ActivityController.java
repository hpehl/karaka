package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.INFO;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.NEW;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;

import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import name.pehl.tire.client.activity.dispatch.DeleteActivityAction;
import name.pehl.tire.client.activity.dispatch.DeleteActivityResult;
import name.pehl.tire.client.activity.dispatch.SaveActivityAction;
import name.pehl.tire.client.activity.dispatch.SaveActivityResult;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
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

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
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
 * <li>{@linkplain SaveActivityAction}</li>
 * <li>{@linkplain DeleteActivityAction}</li>
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

    static final int ONE_MINUTE_IN_MILLIS = 60 * 1000;
    static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    static final Logger logger = Logger.getLogger(ActivityController.class.getName());

    final EventBus eventBus;
    final Scheduler scheduler;
    final DispatchAsync dispatcher;

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
    }


    public void start()
    {
        eventBus.addHandler(RunningActivityLoadedEvent.getType(), this);
        eventBus.addHandler(ActivitiesLoadedEvent.getType(), this);
        eventBus.addHandler(ActivityActionEvent.getType(), this);
    }


    // --------------------------------------------------------- event handling

    @Override
    public void fireEvent(GwtEvent<?> event)
    {
        eventBus.fireEventFromSource(event, this);
    }


    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        runningActivity = event.getActivity();
        startTicking();
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


    void copy(Activity activityToCopy)
    {
        logger.fine("About to copy " + activityToCopy);
        Activity plusOneDay = activityToCopy.plus(ONE_DAY_IN_MILLIS);
        dispatcher.execute(new SaveActivityAction(plusOneDay), new TireCallback<SaveActivityResult>(eventBus)
        {
            @Override
            public void onSuccess(SaveActivityResult result)
            {
                Activity copiedActivity = result.getStoredActivity();
                updateActivities(null, copiedActivity);
                ActivityChangedEvent.fire(ActivityController.this, NEW, copiedActivity, activities);
                ShowMessageEvent.fire(ActivityController.this,
                        new Message(INFO, "Activity \"" + copiedActivity.getName() + "\" added", true));
            }
        });
    }


    void start(final Activity activityToStart)
    {
        logger.info("About to start " + activityToStart);
        if (activityToStart.isStopped())
        {
            // if there's currently another activity running, stop it.
            if (runningActivity != null && runningActivity.isRunning() && !runningActivity.equals(activityToStart))
            {
                logger.info("Stopping currently running " + runningActivity);
                Activity runningActivityBackup = runningActivity;
                stopTicking();
                dispatcher.execute(new SaveActivityAction(runningActivityBackup), new TireCallback<SaveActivityResult>(
                        eventBus)
                {
                    @Override
                    public void onSuccess(SaveActivityResult result)
                    {
                        Activity stoppedActivity = result.getStoredActivity();
                        logger.info("Successfully stopped " + stoppedActivity);
                        start(activityToStart);
                    }
                });
            }
            else
            {
                // Is there an activity to resume?
                boolean resume = false;
                if (activityToStart.isToday())
                {
                    // Get the activities for today
                    SortedSet<Activity> activitiesOfToday = Sets.filter(activities.activities(),
                            new Predicate<Activity>()
                            {
                                @Override
                                public boolean apply(@Nullable Activity input)
                                {
                                    return input != null && input.isToday();
                                }
                            });
                    resume = activitiesOfToday.contains(activityToStart);
                }
                if (resume)
                {
                    logger.info("Resuming " + activityToStart);
                    activityToStart.resume();
                    dispatcher.execute(new SaveActivityAction(activityToStart), new TireCallback<SaveActivityResult>(
                            eventBus)
                    {
                        @Override
                        public void onSuccess(SaveActivityResult result)
                        {
                            runningActivity = result.getStoredActivity();
                            updateActivities(activityToStart, runningActivity);
                            ActivityChangedEvent.fire(ActivityController.this, RESUMED, runningActivity, activities);
                            ShowMessageEvent.fire(ActivityController.this, new Message(INFO, "Activity \""
                                    + runningActivity.getName() + "\" resumed", true));
                            checkAndrefreshProjectsAndTags(activityToStart);
                            startTicking();
                        }
                    });
                }
                else
                {
                    // If the parameter is an existing activity. We have to copy
                    // this activity.
                    final Activity newActivity = activityToStart.isTransient() ? activityToStart : activityToStart
                            .copy();
                    newActivity.start();
                    logger.info("Starting " + newActivity);
                    dispatcher.execute(new SaveActivityAction(newActivity), new TireCallback<SaveActivityResult>(
                            eventBus)
                    {
                        @Override
                        public void onSuccess(SaveActivityResult result)
                        {
                            runningActivity = result.getStoredActivity();
                            updateActivities(newActivity, runningActivity);
                            ActivityChangedEvent.fire(ActivityController.this, STARTED, runningActivity, activities);
                            ShowMessageEvent.fire(ActivityController.this, new Message(INFO, "Activity \""
                                    + runningActivity.getName() + "\" started", true));
                            checkAndrefreshProjectsAndTags(activityToStart);
                            startTicking();
                        }
                    });
                }
            }
        }
        else
        {
            logger.info(activityToStart + " already running");
        }
    }


    void stop(final Activity activityToStop)
    {
        logger.info("About to stop " + activityToStop);
        if (activityToStop.isRunning())
        {
            if (!activityToStop.equals(runningActivity))
            {
                throw new IllegalStateException("Trying to stop " + activityToStop
                        + " which is not the same as the internal running " + runningActivity + "!");
            }
            stopTicking();
            activityToStop.stop();
            dispatcher.execute(new SaveActivityAction(activityToStop), new TireCallback<SaveActivityResult>(eventBus)
            {
                @Override
                public void onSuccess(SaveActivityResult result)
                {
                    Activity stoppedActivity = result.getStoredActivity();
                    updateActivities(activityToStop, stoppedActivity);
                    ActivityChangedEvent.fire(ActivityController.this, STOPPED, stoppedActivity, activities);
                    ShowMessageEvent.fire(ActivityController.this,
                            new Message(INFO, "Activity \"" + stoppedActivity.getName() + "\" stopped", true));
                }
            });
        }
        else
        {
            logger.info(activityToStop + " already stopped");
        }
    }


    void delete(final Activity activityToDelete)
    {
        logger.info("About to delete " + activityToDelete);
        if (activityToDelete.isRunning())
        {
            if (!activityToDelete.equals(runningActivity))
            {
                throw new IllegalStateException("Trying to delete running " + activityToDelete
                        + " which is not the same as the internal running " + runningActivity + "!");
            }
            stopTicking();
        }
        dispatcher.execute(new DeleteActivityAction(activityToDelete), new TireCallback<DeleteActivityResult>(eventBus)
        {
            @Override
            public void onSuccess(DeleteActivityResult result)
            {
                updateActivities(activityToDelete, null);
                ActivityChangedEvent.fire(ActivityController.this, DELETE, activityToDelete, activities);
                ShowMessageEvent.fire(ActivityController.this,
                        new Message(INFO, "Activity \"" + activityToDelete.getName() + "\" deleted", true));
            }
        });
    }


    private void updateActivities(Activity activityBefore, Activity activityAfter)
    {
        if (activities.contains(activityBefore))
        {
            activities.remove(activityBefore);
        }
        if (activities.matchingRange(activityAfter))
        {
            activities.add(activityAfter);
        }
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

    private void startTicking()
    {
        ticking = true;
        scheduler.scheduleFixedPeriod(ActivityController.this, ONE_MINUTE_IN_MILLIS);
    }


    /**
     * Stops the {@code runningActivity} and sets it to <code>null</code>.
     */
    private void stopTicking()
    {
        ticking = false;
        runningActivity.stop();
        runningActivity = null;
    }


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
                    updateActivities(runningActivity, runningActivity);
                    TickEvent.fire(ActivityController.this, runningActivity, activities);
                }
            });
        }
        return ticking;
    }
}
