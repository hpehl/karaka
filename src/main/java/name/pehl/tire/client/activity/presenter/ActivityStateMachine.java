package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityResumedEvent;
import name.pehl.tire.client.activity.event.ActivityStartedEvent;
import name.pehl.tire.client.activity.event.ActivityStoppedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent.RunningActivityLoadedHandler;
import name.pehl.tire.client.activity.event.StartActivityEvent;
import name.pehl.tire.client.activity.event.StartActivityEvent.StartActivityHandler;
import name.pehl.tire.client.activity.event.StopActivityEvent;
import name.pehl.tire.client.activity.event.StopActivityEvent.StopActivityHandler;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

import static name.pehl.tire.shared.model.Status.RUNNING;
import static name.pehl.tire.shared.model.Status.STOPPED;

/**
 * This class is responsible for managing the currently running, paused or
 * stopped activity. It's used by various presenters.
 * <p>
 * Please note:<br/>
 * This is the <em>only</em> instance where the currently running, paused or
 * stopped activity can be changed. The communication is done by the following
 * events:
 * <ul>
 * <li>IN {@link ActivitiesLoadedEvent}
 * <li>IN {@link RunningActivityLoadedEvent}
 * <li>IN {@link StartActivityEvent}
 * <li>IN {@link StopActivityEvent}
 * <li>OUT {@link ActivityStartedEvent}
 * <li>OUT {@link ActivityResumedEvent}
 * <li>OUT {@link ActivityStoppedEvent}
 * </ul>
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivityStateMachine implements HasHandlers, ActivitiesLoadedHandler, RunningActivityLoadedHandler,
        StartActivityHandler, StopActivityHandler
{
    private static final Logger logger = Logger.getLogger(ActivityStateMachine.class.getName());
    /**
     * The currently managed actvity
     */
    private Activity currentActivity;
    /**
     * The latest / newest activity currently displayed in the dashboard.
     */
    private Activity latestActivity;
    private final EventBus eventBus;
    private final DispatchAsync dispatcher;


    @Inject
    public ActivityStateMachine(final EventBus eventBus, final DispatchAsync dispatcher)
    {
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;
        this.eventBus.addHandler(ActivitiesLoadedEvent.getType(), this);
        this.eventBus.addHandler(RunningActivityLoadedEvent.getType(), this);
        this.eventBus.addHandler(StartActivityEvent.getType(), this);
        this.eventBus.addHandler(StopActivityEvent.getType(), this);
    }


    // --------------------------------------------------------- event handlers

    @Override
    public void fireEvent(GwtEvent<?> event)
    {
        this.eventBus.fireEventFromSource(event, this);
    }


    @Override
    public void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        this.latestActivity = event.getActivities().getActivities().first();
    }


    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        this.currentActivity = event.getActivity();
    }


    @Override
    public void onStartActivity(StartActivityEvent event)
    {
        // TODO Make this method somehow 'transactional'
        // TODO Move code to start / stop activity to Activity.start() /
        // Activity.stop()
        Activity eventActivity = event.getActivity();
        logger.info("About to start activity " + eventActivity);
        switch (eventActivity.getStatus())
        {
            case RUNNING:
                logger.info("Activity " + eventActivity + " already running");
                break;
            case STOPPED:
                // if there's currently another activity running stop it.
                if (isRunning() && !eventActivity.equals(currentActivity))
                {
                    // TODO Store this activity on the server
                    logger.info("Stopping currently running activity " + currentActivity);
                    currentActivity.setStatus(STOPPED);
                }
                if (eventActivity.isToday() && eventActivity.equals(latestActivity))
                {
                    logger.info("Resuming activity " + eventActivity);
                    eventActivity.setStatus(RUNNING);
                    // TODO Update pause
                    currentActivity = eventActivity;
                    // TODO Fire event after updated activity was successfully
                    // saved on the server!
                    ActivityResumedEvent.fire(this, currentActivity);
                }
                else
                {
                    logger.info("Copy activity " + eventActivity + " and start as a new activity");
                    Activity newActivity = eventActivity.copy();
                    newActivity.setStatus(RUNNING);
                    currentActivity = newActivity;
                    // TODO Fire event after new activity was successfully
                    // saved on the server!
                    ActivityStartedEvent.fire(this, currentActivity);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onStopActivity(StopActivityEvent event)
    {
        // TODO Make this method somehow 'transactional'
        // TODO Move code to start / stop activity to Activity.start() /
        // Activity.stop()
        currentActivity = event.getActivity();
        logger.info("About to stop activity " + currentActivity);
        switch (currentActivity.getStatus())
        {
            case RUNNING:
                logger.info("Stopping activity " + currentActivity);
                currentActivity.setStatus(STOPPED);
                // TODO Fire event after activity was successfully saved on the
                // server!
                ActivityStoppedEvent.fire(this, currentActivity);
                break;
            case STOPPED:
                logger.info("Activity " + currentActivity + " already stopped");
                break;
            default:
                break;
        }
    }


    private boolean isRunning()
    {
        return currentActivity != null && currentActivity.getStatus() == RUNNING;
    }
}
