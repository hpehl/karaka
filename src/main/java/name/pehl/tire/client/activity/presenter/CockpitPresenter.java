package name.pehl.tire.client.activity.presenter;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.dispatch.GetMinutesAction;
import name.pehl.tire.client.activity.dispatch.GetMinutesResult;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityAction;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityResult;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.activity.event.TickEvent.TickHandler;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.Minutes;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.activity.event.ActivityAction.Action.START_STOP;

/**
 * <p>
 * Presenter which displays the most recent activity the duration for today, the
 * current week and month. The user can start / stop the activity. The activity
 * does not depend on the activities displayed in the dashboard.
 * </p>
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>{@linkplain ActivityChangedEvent}</li>
 * <li>{@linkplain TickEvent}</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ShowMessageEvent}</li>
 * <li>{@linkplain RunningActivityLoadedEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>{@linkplain GetMinutesAction}
 * <li>{@linkplain GetRunningActivityAction}
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 175
 *          $
 */
public class CockpitPresenter extends PresenterWidget<CockpitPresenter.MyView> implements CockpitUiHandlers,
        ActivityChangedHandler, TickHandler
{
    public interface MyView extends View, HasUiHandlers<CockpitUiHandlers>
    {
        void updateMinutes(Minutes minutes);


        void updateStatus(Activity activity);
    }

    static final Logger logger = Logger.getLogger(CockpitPresenter.class.getName());
    /**
     * The currently managed actvity
     */
    Activity currentActivity;
    final Scheduler scheduler;
    final DispatchAsync dispatcher;
    final GetMinutesCommand getMinutesCommand;
    final GetRunningActivityCommand getRunningActivityCommand;


    @Inject
    public CockpitPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final Scheduler scheduler)
    {
        super(eventBus, view);
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.getMinutesCommand = new GetMinutesCommand();
        this.getRunningActivityCommand = new GetRunningActivityCommand();

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
        scheduler.scheduleDeferred(getMinutesCommand);
        scheduler.scheduleDeferred(getRunningActivityCommand);
    }


    @Override
    public void onStartStop()
    {
        if (currentActivity == null)
        {
            ShowMessageEvent.fire(this, new Message(WARNING, "No activity selected", true));
        }
        else
        {
            ActivityActionEvent.fire(this, currentActivity, START_STOP);
        }
    }


    @Override
    public void onActivityChanged(ActivityChangedEvent event)
    {
        Activity activity = event.getActivity();
        ChangeAction action = event.getAction();
        switch (action)
        {
            case NEW:
                break;
            case RESUMED:
            case STARTED:
            case STOPPED:
                currentActivity = activity;
                break;
            case DELETE:
                if (activity.equals(currentActivity))
                {
                    currentActivity = null;
                }
                break;
            default:
                break;
        }
        getView().updateStatus(currentActivity);
        getMinutesCommand.execute();
    }


    @Override
    public void onTick(TickEvent event)
    {
        getMinutesCommand.execute();
    }

    class GetMinutesCommand implements ScheduledCommand
    {
        @Override
        public void execute()
        {
            dispatcher.execute(new GetMinutesAction(), new TireCallback<GetMinutesResult>(getEventBus())
            {
                @Override
                public void onSuccess(GetMinutesResult result)
                {
                    getView().updateMinutes(result.getMinutes());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load minutes for current month, week and/or day");
                    getView().updateMinutes(new Minutes());
                }
            });
        }
    }

    class GetRunningActivityCommand implements ScheduledCommand
    {
        @Override
        public void execute()
        {
            dispatcher.execute(new GetRunningActivityAction(),
                    new TireCallback<GetRunningActivityResult>(getEventBus())
                    {
                        @Override
                        public void onSuccess(GetRunningActivityResult result)
                        {
                            currentActivity = result.getActivity();
                            getView().updateStatus(currentActivity);
                            RunningActivityLoadedEvent.fire(CockpitPresenter.this, currentActivity);
                        }


                        @Override
                        public void onFailure(Throwable caught)
                        {
                            logger.info("No running activity found.");
                        }
                    });
        }
    }
}
