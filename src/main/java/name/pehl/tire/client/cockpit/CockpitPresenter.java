package name.pehl.tire.client.cockpit;

import java.util.logging.Logger;

import name.pehl.tire.client.activity.dispatch.ActivitiesRequest;
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
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Activity;

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
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 175
 *          $
 */
public class CockpitPresenter extends PresenterWidget<CockpitPresenter.MyView> implements CockpitUiHandlers,
        ActivityChangedHandler, TickHandler
{
    public interface MyView extends View, HasUiHandlers<CockpitUiHandlers>
    {
        void updateMonth(long minutes);


        void updateWeek(long minutes);


        void updateToday(long minutes);


        void updateStatus(Activity activity);
    }

    private static final Logger logger = Logger.getLogger(CockpitPresenter.class.getName());
    /**
     * The currently managed actvity
     */
    private Activity currentActivity;
    private final Scheduler scheduler;
    private final DispatchAsync dispatcher;


    @Inject
    public CockpitPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final Scheduler scheduler)
    {
        super(eventBus, view);
        this.scheduler = scheduler;
        this.dispatcher = dispatcher;

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivityChangedEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
        scheduler.scheduleDeferred(new GetMinutesCommand());
        scheduler.scheduleDeferred(new GetRunningActivityCommand());
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
        internalUpdate(event.getActivity(), event.getAction());
    }


    private void internalUpdate(Activity activity, ChangeAction action)
    {
        if (action == null || action == RESUMED || action == STARTED || action == STOPPED)
        {
            currentActivity = activity;
        }
        getView().updateStatus(currentActivity);
        scheduler.scheduleDeferred(new GetMinutesCommand());
    }


    @Override
    public void onTick(TickEvent event)
    {
        scheduler.scheduleDeferred(new GetMinutesCommand());
    }

    class GetMinutesCommand implements ScheduledCommand
    {
        @Override
        public void execute()
        {
            final ActivitiesRequest currentMonth = new ActivitiesRequest(new UrlBuilder().module("rest")
                    .path("activities", "currentMonth", "minutes").toUrl());
            dispatcher.execute(new GetMinutesAction(currentMonth), new TireCallback<GetMinutesResult>(getEventBus())
            {
                @Override
                public void onSuccess(GetMinutesResult result)
                {
                    getView().updateMonth(result.getMinutes());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load minutes for " + currentMonth);
                }
            });

            final ActivitiesRequest currentWeek = new ActivitiesRequest(new UrlBuilder().module("rest")
                    .path("activities", "currentWeek", "minutes").toUrl());
            dispatcher.execute(new GetMinutesAction(currentWeek), new TireCallback<GetMinutesResult>(getEventBus())
            {
                @Override
                public void onSuccess(GetMinutesResult result)
                {
                    getView().updateWeek(result.getMinutes());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load minutes for " + currentWeek);
                }
            });

            final ActivitiesRequest today = new ActivitiesRequest(new UrlBuilder().module("rest")
                    .path("activities", "today", "minutes").toUrl());
            dispatcher.execute(new GetMinutesAction(today), new TireCallback<GetMinutesResult>(getEventBus())
            {
                @Override
                public void onSuccess(GetMinutesResult result)
                {
                    getView().updateToday(result.getMinutes());
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    logger.warning("Cannot load activities for " + today);
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
                            Activity activity = result.getActivity();
                            internalUpdate(activity, null);
                            RunningActivityLoadedEvent.fire(CockpitPresenter.this, activity);
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
