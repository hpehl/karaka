package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.shared.model.Status.RUNNING;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActivitiesChangedEvent;
import name.pehl.tire.client.activity.event.ActivitiesChangedEvent.ActivitiesChangedHandler;
import name.pehl.tire.client.activity.event.ActivityResumedEvent;
import name.pehl.tire.client.activity.event.ActivityStartedEvent;
import name.pehl.tire.client.activity.event.ActivityStoppedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent.RunningActivityLoadedHandler;
import name.pehl.tire.client.activity.event.StartActivityEvent;
import name.pehl.tire.client.activity.event.StartActivityEvent.StartActivityHandler;
import name.pehl.tire.client.activity.event.StopActivityEvent;
import name.pehl.tire.client.activity.event.StopActivityEvent.StopActivityHandler;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

/**
 * This class is responsible for managing the currently running or stopped
 * activity. It's used by various presenters.
 * <p>
 * Please note:<br/>
 * This is the <em>only</em> instance where the currently running or stopped
 * activity can be changed. The communication is done by the following events:
 * <ul>
 * <li>IN {@link ActivitiesChangedEvent}
 * <li>IN {@link RunningActivityLoadedEvent}
 * <li>IN {@link StartActivityEvent}
 * <li>IN {@link StopActivityEvent}
 * <li>OUT {@link ActivityStartedEvent}
 * <li>OUT {@link ActivityResumedEvent}
 * <li>OUT {@link ActivityStoppedEvent}
 * <li>OUT {@link TickEvent}
 * </ul>
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivityStateMachine implements HasHandlers, ActivitiesChangedHandler, RunningActivityLoadedHandler,
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
    private final TickTimer tickTimer;


    @Inject
    public ActivityStateMachine(final EventBus eventBus, final DispatchAsync dispatcher)
    {
        this.eventBus = eventBus;
        this.dispatcher = dispatcher;
        this.tickTimer = new TickTimer();

        this.eventBus.addHandler(ActivitiesChangedEvent.getType(), this);
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
    public void onActivitiesChanged(ActivitiesChangedEvent event)
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
        Activity eventActivity = event.getActivity();
        logger.info("About to start activity " + eventActivity);
        switch (eventActivity.getStatus())
        {
            case RUNNING:
                logger.info("Activity " + eventActivity + " already running");
                break;
            case STOPPED:
                // if there's currently another activity running stop it.
                if (isCurrentActivityRunning() && !eventActivity.equals(currentActivity))
                {
                    // TODO Store this activity on the server
                    logger.info("Stopping currently running activity " + currentActivity);
                    currentActivity.stop();
                }
                if (eventActivity.isToday() && eventActivity.equals(latestActivity))
                {
                    logger.info("Resuming activity " + eventActivity);
                    eventActivity.resume();
                    currentActivity = eventActivity;
                    // TODO Fire event after updated activity was successfully
                    // saved on the server!
                    ActivityResumedEvent.fire(this, currentActivity);
                }
                else
                {
                    logger.info("Copy activity " + eventActivity + " and start as a new activity");
                    Activity newActivity = eventActivity.copy();
                    newActivity.start();
                    currentActivity = newActivity;
                    // TODO Fire event after new activity was successfully
                    // saved on the server!
                    ActivityStartedEvent.fire(this, currentActivity);
                }
                tickTimer.scheduleRepeating(60000);
                break;
            default:
                break;
        }
    }


    @Override
    public void onStopActivity(StopActivityEvent event)
    {
        // TODO Make this method somehow 'transactional'
        currentActivity = event.getActivity();
        logger.info("About to stop activity " + currentActivity);
        switch (currentActivity.getStatus())
        {
            case RUNNING:
                logger.info("Stopping activity " + currentActivity);
                currentActivity.stop();
                // TODO Fire event after activity was successfully saved on the
                // server!
                ActivityStoppedEvent.fire(this, currentActivity);
                tickTimer.cancel();
                break;
            case STOPPED:
                logger.info("Activity " + currentActivity + " already stopped");
                break;
            default:
                break;
        }
    }


    private boolean isCurrentActivityRunning()
    {
        return currentActivity != null && currentActivity.getStatus() == RUNNING;
    }

    class TickTimer extends Timer
    {
        @Override
        public void run()
        {
            TickEvent.fire(ActivityStateMachine.this, currentActivity);
        }
    }
}
