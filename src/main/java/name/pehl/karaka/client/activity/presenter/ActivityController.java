package name.pehl.karaka.client.activity.presenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.activity.dispatch.CopyActivityAction;
import name.pehl.karaka.client.activity.dispatch.CopyActivityResult;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityAction;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityResult;
import name.pehl.karaka.client.activity.dispatch.SaveActivityAction;
import name.pehl.karaka.client.activity.dispatch.SaveActivityResult;
import name.pehl.karaka.client.activity.dispatch.StartActivityAction;
import name.pehl.karaka.client.activity.dispatch.StartActivityResult;
import name.pehl.karaka.client.activity.dispatch.StopActivityAction;
import name.pehl.karaka.client.activity.dispatch.StopActivityResult;
import name.pehl.karaka.client.activity.dispatch.TickActivityAction;
import name.pehl.karaka.client.activity.dispatch.TickActivityResult;
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

import java.util.List;
import java.util.Set;

import static java.util.logging.Level.INFO;
import static name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction.*;
import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.info;

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
 * <li>{@linkplain ActivityActionEvent}</li>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain RunningActivityLoadedEvent}</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ActivityChangedEvent}</li>
 * <li>{@linkplain RefreshProjectsEvent}</li>
 * <li>{@linkplain RefreshTagsEvent}</li>
 * <li>{@linkplain ShowMessageEvent}</li>
 * <li>{@linkplain TickEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>{@linkplain CopyActivityAction}</li>
 * <li>{@linkplain DeleteActivityAction}</li>
 * <li>{@linkplain SaveActivityAction}</li>
 * <li>{@linkplain StartActivityAction}</li>
 * <li>{@linkplain StopActivityAction}</li>
 * <li>{@linkplain TickActivityAction}</li>
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
    static final String ONE_DAY = "P1D";
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

    public void init()
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
        info(activity, "About to save " + activityToSave);
        dispatcher.execute(new SaveActivityAction(activityToSave), new KarakaCallback<SaveActivityResult>(eventBus)
        {
            @Override
            public void onSuccess(SaveActivityResult result)
            {
                Activity savedActivity = result.getSaved();
                info(activity, activityToSave + " successfully saved as " + savedActivity);
                activities.update(savedActivity);
//                updateActivities(activityToSave, savedActivity);
                ActivityChangedEvent.fire(ActivityController.this, CHANGED, savedActivity, activities);
                ShowMessageEvent.fire(ActivityController.this,
                        new Message(INFO, "Activity \"" + savedActivity.getName() + "\" saved", true));
                checkAndrefreshProjectsAndTags(activityToSave);
            }
        });
    }

    void copy(final Activity activityToCopy)
    {
        info(activity, "About to copy " + activityToCopy);
        dispatcher.execute(new CopyActivityAction(activityToCopy, ONE_DAY),
                new KarakaCallback<CopyActivityResult>(eventBus)
                {
                    @Override
                    public void onSuccess(CopyActivityResult result)
                    {
                        Activity copiedActivity = result.getCopy();
                        info(activity, activityToCopy + " successfully copied as " + copiedActivity);
                        activities.update(copiedActivity);
//                        updateActivities(null, copiedActivity);
                        ActivityChangedEvent.fire(ActivityController.this, NEW, copiedActivity, activities);
                        ShowMessageEvent.fire(ActivityController.this,
                                new Message(INFO, "Activity \"" + copiedActivity.getName() + "\" added", true));
                    }
                });
    }

    void start(final Activity activityToResumeOrStart)
    {
        info(activity, "About to resume / start " + activityToResumeOrStart);
        if (activityToResumeOrStart.isRunning())
        {
            info(activity, activityToResumeOrStart + " already running");
        }
        else
        {
            dispatcher.execute(new StartActivityAction(activityToResumeOrStart),
                    new KarakaCallback<StartActivityResult>(eventBus)
                    {
                        @Override
                        public void onSuccess(final StartActivityResult result)
                        {
                            Set<Activity> modifiedActivities = result.getModified();
                            runningActivity = extractRunningActivity(modifiedActivities);
                            boolean resumed = modifiedActivities.contains(activityToResumeOrStart);
                            info(activity,
                                    activityToResumeOrStart + " successfully " + (resumed ? "resumed" : "started") + ". Modified activities: " + modifiedActivities);
                            activities.update(modifiedActivities.toArray(new Activity[]{}));
                            ActivityChangedEvent
                                    .fire(ActivityController.this, resumed ? RESUMED : STARTED, runningActivity,
                                            activities);
                            ShowMessageEvent.fire(ActivityController.this, new Message(INFO, "Activity \""
                                    + runningActivity.getName() + "\" " + (resumed ? "resumed" : "started"), true));
                            checkAndrefreshProjectsAndTags(activityToResumeOrStart);
                            startTicking();
                        }
                    });
        }
    }

    void stop(final Activity activityToStop)
    {
        info(activity, "About to stop " + activityToStop);
        if (activityToStop.isStopped())
        {
            info(activity, activityToStop + " already stopped");
        }
        else
        {
            if (!activityToStop.equals(runningActivity))
            {
                throw new IllegalStateException("Trying to stop " + activityToStop
                        + " which is not the same as the internal running " + runningActivity + "!");
            }
            stopTicking();
            dispatcher.execute(new StopActivityAction(activityToStop), new KarakaCallback<StopActivityResult>(eventBus)
            {
                @Override
                public void onSuccess(StopActivityResult result)
                {
                    Activity stoppedActivity = result.getStopped();
                    info(activity,
                            activityToStop + " successfully stopped as " + stoppedActivity);
                    activities.update(stoppedActivity);
//                    updateActivities(activityToStop, stoppedActivity);
                    ActivityChangedEvent.fire(ActivityController.this, STOPPED, stoppedActivity, activities);
                    ShowMessageEvent.fire(ActivityController.this,
                            new Message(INFO, "Activity \"" + stoppedActivity.getName() + "\" stopped", true));
                }
            });
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
        dispatcher
                .execute(new DeleteActivityAction(activityToDelete), new KarakaCallback<DeleteActivityResult>(eventBus)
                {
                    @Override
                    public void onSuccess(DeleteActivityResult result)
                    {
                        info(activity, activityToDelete + " successfully deleted");
                        activities.remove(activityToDelete);
//                        updateActivities(activityToDelete, null);
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
     * contained a transient project or tags, we have to refresh our local caches!
     *
     * @param activity The activity before it was saved
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

    private Activity extractRunningActivity(final Set<Activity> activities)
    {
        for (Activity activity : activities)
        {
            if (activity.isRunning())
            {
                return activity;
            }
        }
        return null;
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
            info(activity, "About to tick " + runningActivity);
            dispatcher.execute(new TickActivityAction(runningActivity), new KarakaCallback<TickActivityResult>(eventBus)
            {
                @Override
                public void onSuccess(TickActivityResult result)
                {
                    Set<Activity> modifiedActivities = result.getModified();
                    runningActivity = extractRunningActivity(modifiedActivities);
                    info(activity,
                            runningActivity + " successfully ticked. Modified activities: " + modifiedActivities);
                    activities.update(modifiedActivities.toArray(new Activity[]{}));
//                    updateActivities(runningActivity, runningActivity);
                    TickEvent.fire(ActivityController.this, runningActivity, activities);
                }
            });
        }
        return ticking;
    }
}
