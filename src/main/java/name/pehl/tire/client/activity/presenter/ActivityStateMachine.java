package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.event.ActiveActivityLoadedEvent;
import name.pehl.tire.client.activity.event.ActiveActivityLoadedEvent.ActiveActivityLoadedHandler;
import name.pehl.tire.client.activity.event.ActivityStartedEvent;
import name.pehl.tire.client.activity.event.ActivityStoppedEvent;
import name.pehl.tire.client.activity.event.StartActivityEvent;
import name.pehl.tire.client.activity.event.StartActivityEvent.StartActivityHandler;
import name.pehl.tire.client.activity.event.StopActivityEvent;
import name.pehl.tire.client.activity.event.StopActivityEvent.StopActivityHandler;
import name.pehl.tire.shared.model.Activity;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

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
 * <li>IN {@link ActiveActivityLoadedEvent}
 * <li>IN {@link StartActivityEvent}
 * <li>IN {@link StopActivityEvent}
 * <li>OUT {@link ActivityStartedEvent}
 * <li>OUT {@link ActivityStoppedEvent}
 * </ul>
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivityStateMachine implements HasHandlers, ActiveActivityLoadedHandler, StartActivityHandler,
        StopActivityHandler
{
    private static final Logger logger = Logger.getLogger(ActivityStateMachine.class.getName());
    private Activity activity;
    private final EventBus eventBus;


    @Inject
    public ActivityStateMachine(final EventBus eventBus)
    {
        this.eventBus = eventBus;
        this.eventBus.addHandler(ActiveActivityLoadedEvent.getType(), this);
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
    public void onActiveActivityLoaded(ActiveActivityLoadedEvent event)
    {
        this.activity = event.getActivity();
    }


    @Override
    public void onStartActivity(StartActivityEvent event)
    {
        activity = event.getActivity();
        logger.info("About to start activity " + activity);
        switch (activity.getStatus())
        {
            case PAUSE:
                logger.info("Pause not yet implemented");
                break;
            case RUNNING:
                logger.info("Activity " + activity + " already running");
                break;
            case STOPPED:
                if (activity.isToday())
                {
                    logger.info("Resuming activity " + activity);
                    activity.setStatus(RUNNING);
                }
                else
                {
                    logger.info("Copy activity " + activity + " and start as a new activity");
                    Activity newActivity = activity.copy();
                    newActivity.setStatus(RUNNING);
                }
                // TODO Save new / updated activity on the server!
                ActivityStartedEvent.fire(this, activity);
                break;
            default:
                break;
        }
    }


    @Override
    public void onStopActivity(StopActivityEvent event)
    {
        activity = event.getActivity();
        logger.info("About to stop activity " + activity);
        switch (activity.getStatus())
        {
            case PAUSE:
                logger.info("Pause not yet implemented");
                break;
            case RUNNING:
                logger.info("Stopping activity " + activity);
                activity.setStatus(STOPPED);
                // TODO Save stopped activity on the server!
                ActivityStoppedEvent.fire(this, activity);
                break;
            case STOPPED:
                logger.info("Activity " + activity + " already stopped");
                break;
            default:
                break;
        }
    }
}
