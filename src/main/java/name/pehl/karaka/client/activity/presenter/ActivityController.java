package name.pehl.karaka.client.activity.presenter;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityAction;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityResult;
import name.pehl.karaka.client.activity.dispatch.SaveActivityAction;
import name.pehl.karaka.client.activity.dispatch.SaveActivityResult;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.karaka.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.karaka.client.activity.event.ActivityAction.Action;
import name.pehl.karaka.client.activity.event.ActivityActionEvent;
import name.pehl.karaka.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.karaka.client.activity.event.RunningActivityLoadedEvent.RunningActivityLoadedHandler;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.project.RefreshProjectsEvent;
import name.pehl.karaka.client.tag.RefreshTagsEvent;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Activity;
import name.pehl.karaka.shared.model.Project;
import name.pehl.karaka.shared.model.Tag;

import javax.annotation.Nullable;
import java.util.List;
import java.util.SortedSet;

import static java.util.logging.Level.INFO;
import static name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction.*;
import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.info;
import static name.pehl.karaka.client.logging.Logger.trace;

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
 * <li>{@linkplain RefreshProjectsEvent}</li>
 * <li>{@linkplain RefreshTagsEvent}</li>
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

    static final int TICK_INTERVAL = 60 * 1000;
    static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

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
        // the first 'tick' will happen in TICK_INTERVAL millis, so tick now to update the UI
        tick();
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
        trace(activity, "About to save " + activityToSave);
        dispatcher.execute(new SaveActivityAction(activityToSave), new KarakaCallback<SaveActivityResult>(eventBus)
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
        trace(activity, "About to copy " + activityToCopy);
        Activity plusOneDay = activityToCopy.plus(ONE_DAY_IN_MILLIS);
        dispatcher.execute(new SaveActivityAction(plusOneDay), new KarakaCallback<SaveActivityResult>(eventBus)
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
        info(activity, "About to start " + activityToStart);
        if (activityToStart.isStopped())
        {
            // if there's currently another activity running, stop it.
            if (runningActivity != null && runningActivity.isRunning() && !runningActivity.equals(activityToStart))
            {
                info(activity, "Stopping currently running " + runningActivity);
                Activity runningActivityBackup = runningActivity;
                stopTicking();
                dispatcher.execute(new SaveActivityAction(runningActivityBackup), new KarakaCallback<SaveActivityResult>(
                        eventBus)
                {
                    @Override
                    public void onSuccess(SaveActivityResult result)
                    {
                        Activity stoppedActivity = result.getStoredActivity();
                        info(activity, "Successfully stopped " + stoppedActivity);
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
                    info(activity, "Resuming " + activityToStart);
                    activityToStart.resume();
                    dispatcher.execute(new SaveActivityAction(activityToStart), new KarakaCallback<SaveActivityResult>(
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
                    info(activity, "Starting " + newActivity);
                    dispatcher.execute(new SaveActivityAction(newActivity), new KarakaCallback<SaveActivityResult>(
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
            info(activity, activityToStart + " already running");
        }
    }


    void stop(final Activity activityToStop)
    {
        info(activity, "About to stop " + activityToStop);
        if (activityToStop.isRunning())
        {
            if (!activityToStop.equals(runningActivity))
            {
                throw new IllegalStateException("Trying to stop " + activityToStop
                        + " which is not the same as the internal running " + runningActivity + "!");
            }
            stopTicking();
            activityToStop.stop();
            dispatcher.execute(new SaveActivityAction(activityToStop), new KarakaCallback<SaveActivityResult>(eventBus)
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
            info(activity, activityToStop + " already stopped");
        }
    }


    void delete(final Activity activityToDelete)
    {
        info(activity, "About to delete " + activityToDelete);
        if (activityToDelete.isRunning())
        {
            if (!activityToDelete.equals(runningActivity))
            {
                throw new IllegalStateException("Trying to delete running " + activityToDelete
                        + " which is not the same as the internal running " + runningActivity + "!");
            }
            stopTicking();
        }
        dispatcher.execute(new DeleteActivityAction(activityToDelete), new KarakaCallback<DeleteActivityResult>(eventBus)
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
            RefreshProjectsEvent.fire(this);
        }
        List<Tag> tags = activity.getTags();
        for (Tag tag : tags)
        {
            if (tag.isTransient())
            {
                RefreshTagsEvent.fire(this);
                break;
            }
        }
    }


    // ------------------------------------------------------------------- tick

    private void startTicking()
    {
        ticking = true;
        scheduler.scheduleFixedPeriod(ActivityController.this, TICK_INTERVAL);
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
        return tick();
    }

    private boolean tick()
    {
        if (ticking)
        {
            info(activity, "Tick for " + runningActivity);
            runningActivity.tick();
            dispatcher.execute(new SaveActivityAction(runningActivity), new KarakaCallback<SaveActivityResult>(eventBus)
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
