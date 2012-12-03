package name.pehl.karaka.client.activity.presenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import name.pehl.karaka.client.activity.dispatch.GetDurationsAction;
import name.pehl.karaka.client.activity.dispatch.GetDurationsResult;
import name.pehl.karaka.client.activity.dispatch.GetRunningActivityAction;
import name.pehl.karaka.client.activity.dispatch.GetRunningActivityResult;
import name.pehl.karaka.client.activity.event.ActivityActionEvent;
import name.pehl.karaka.client.activity.event.ActivityChanged.ChangeAction;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent;
import name.pehl.karaka.client.activity.event.ActivityChangedEvent.ActivityChangedHandler;
import name.pehl.karaka.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.karaka.client.activity.event.TickEvent;
import name.pehl.karaka.client.activity.event.TickEvent.TickHandler;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.shared.model.Activity;
import name.pehl.karaka.shared.model.Durations;

import static java.util.logging.Level.WARNING;
import static name.pehl.karaka.client.activity.event.ActivityAction.Action.START_STOP;
import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.info;
import static name.pehl.karaka.client.logging.Logger.warn;

/**
 * <p>
 * Presenter which displays the most recent activity the duration for today, the
 * current week and month. The user can init / stop the activity. The activity
 * does not depend on the placeRequestFor displayed in the dashboard.
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
 * <li>{@linkplain GetDurationsAction}
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
        void updateDurations(Durations minutes);


        void updateStatus(Activity activity);
    }

    /**
     * The currently managed actvity
     */
    Activity currentActivity;
    final Scheduler scheduler;
    final DispatchAsync dispatcher;
    final GetDurationsCommand getDurationsCommand;
    final GetRunningActivityCommand getRunningActivityCommand;


    @Inject
    public CockpitPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final Scheduler scheduler)
    {
        super(eventBus, view);
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;
        this.getDurationsCommand = new GetDurationsCommand();
        this.getRunningActivityCommand = new GetRunningActivityCommand();

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
        scheduler.scheduleDeferred(getDurationsCommand);
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
            ActivityActionEvent.fire(this, START_STOP, currentActivity);
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
        getDurationsCommand.execute();
    }


    @Override
    public void onTick(TickEvent event)
    {
        getDurationsCommand.execute();
    }

    class GetDurationsCommand implements ScheduledCommand
    {
        @Override
        public void execute()
        {
            dispatcher.execute(new GetDurationsAction(), new KarakaCallback<GetDurationsResult>(getEventBus())
            {
                @Override
                public void onSuccess(GetDurationsResult result)
                {
                    getView().updateDurations(result.getMinutes());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    warn(activity, "Cannot load minutes for current month, week and/or day");
                    getView().updateDurations(new Durations());
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
                    new KarakaCallback<GetRunningActivityResult>(getEventBus())
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
                            info(activity, "No running activity found.");
                        }
                    });
        }
    }
}
