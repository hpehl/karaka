package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.NameTokens.dashboard;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.CHANGED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.NEW;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.Date;
import java.util.logging.Logger;

import name.pehl.tire.client.activity.dispatch.ActivitiesRequest;
import name.pehl.tire.client.activity.dispatch.DeleteActivityAction;
import name.pehl.tire.client.activity.dispatch.DeleteActivityResult;
import name.pehl.tire.client.activity.dispatch.GetActivitiesAction;
import name.pehl.tire.client.activity.dispatch.GetActivitiesResult;
import name.pehl.tire.client.activity.dispatch.SaveActivityAction;
import name.pehl.tire.client.activity.dispatch.SaveActivityResult;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivityAction.Action;
import name.pehl.tire.client.activity.event.ActivityActionEvent;
import name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler;
import name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction;
import name.pehl.tire.client.activity.event.ActivityChangedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent;
import name.pehl.tire.client.activity.event.RunningActivityLoadedEvent.RunningActivityLoadedHandler;
import name.pehl.tire.client.activity.event.TickEvent;
import name.pehl.tire.client.activity.event.TickEvent.TickHandler;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

import org.fusesource.restygwt.client.FailedStatusCodeException;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * <p>
 * The main presenter in Tire. This presenter is responsible to start, resume
 * and stop activities. Other presenters are notified with appropriate events.
 * </p>
 * <h3>Events</h3> </p>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>{@linkplain RunningActivityLoadedEvent}</li>
 * <li>{@linkplain ActivityActionEvent}</li>
 * <li>{@linkplain TickEvent}</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>{@linkplain ActivitiesLoadedEvent}</li>
 * <li>{@linkplain ActivityChangedEvent}</li>
 * <li>{@linkplain ShowMessageEvent}</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>{@linkplain GetActivitiesAction}
 * <li>{@linkplain SaveActivityAction}
 * <li>{@linkplain DeleteActivityAction}
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 13:52:44 +0100 (Do, 23. Dez 2010) $ $Revision: 192
 *          $
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> implements
        DashboardUiHandlers, RunningActivityLoadedHandler, ActivityActionHandler, TickHandler
{
    // ---------------------------------------------------------- inner classes

    @ProxyStandard
    @NameToken(dashboard)
    public interface MyProxy extends ProxyPlace<DashboardPresenter>
    {
    }

    public interface MyView extends View, HasUiHandlers<DashboardUiHandlers>
    {
        void updateActivities(Activities activities);
    }

    // ------------------------------------------------------- (static) members

    /**
     * Constant for the find activities presenter widget.
     */
    public static final Object SLOT_Find_Activities = new Object();

    static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    static final Logger logger = Logger.getLogger(DashboardPresenter.class.getName());

    final Scheduler scheduler;
    final DispatchAsync dispatcher;
    final PlaceManager placeManager;
    final LookupActivityPresenterWidget findActivitiesPresenter;
    final SelectMonthPresenter selectMonthPresenter;
    final SelectWeekPresenter selectWeekPresenter;
    final EditActivityPresenter editActivityPresenter;

    TickCommand tickCommand;
    StartAndResumeCallback startCallback;
    StartAndResumeCallback resumeCallback;

    /**
     * The currently running actvity. Null if no activity is running.
     */
    Activity runningActivity;

    /**
     * The selected date
     */
    Date activityDate;

    /**
     * The currently displayed activities
     */
    Activities activities;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy,
            final LookupActivityPresenterWidget findActivitiesPresenter,
            final SelectMonthPresenter selectMonthPresenter, final SelectWeekPresenter selectWeekPresenter,
            final EditActivityPresenter editActivityPresenter, final DispatchAsync dispatcher,
            final PlaceManager placeManager, final Scheduler scheduler)
    {
        super(eventBus, view, proxy);
        this.findActivitiesPresenter = findActivitiesPresenter;
        this.selectMonthPresenter = selectMonthPresenter;
        this.selectWeekPresenter = selectWeekPresenter;
        this.editActivityPresenter = editActivityPresenter;
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.scheduler = scheduler;
        this.tickCommand = new TickCommand(eventBus, scheduler, dispatcher);
        this.startCallback = new StartAndResumeCallback(eventBus, STARTED);
        this.resumeCallback = new StartAndResumeCallback(eventBus, RESUMED);

        getView().setUiHandlers(this);
        getEventBus().addHandler(RunningActivityLoadedEvent.getType(), this);
        getEventBus().addHandler(ActivityActionEvent.getType(), this);
        getEventBus().addHandler(TickEvent.getType(), this);
    }


    // ---------------------------------------------------- presenter lifecycle

    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }


    @Override
    protected void onReveal()
    {
        super.onReveal();
        setInSlot(SLOT_Find_Activities, findActivitiesPresenter);
    }


    @Override
    protected void onHide()
    {
        super.onHide();
        removeFromSlot(SLOT_Find_Activities, findActivitiesPresenter);
    }


    /**
     * Turns the parameters in the place request into an
     * {@link ActivitiesRequest} instance and calls {@link GetActivitiesAction}.
     * 
     * @param placeRequest
     * @see com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform.mvp.client.proxy.PlaceRequest)
     */
    @Override
    public void prepareFromRequest(PlaceRequest placeRequest)
    {
        super.prepareFromRequest(placeRequest);
        final ActivitiesRequest activitiesRequest = new ActivitiesRequest(placeRequest);
        String message = "Loading activities for " + activitiesRequest + "...";
        logger.fine(message);
        ShowMessageEvent.fire(this, new Message(INFO, message, false));
        scheduler.scheduleDeferred(new GetActivitiesCommand(activitiesRequest));
    }


    // --------------------------------------------------------- event handlers

    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        runningActivity = event.getActivity();
        update(runningActivity);
        tickCommand.start(runningActivity);
    }


    @Override
    public void onTick(TickEvent event)
    {
        update(event.getActivity());
    }


    /**
     * This method is called from the <em>application</em> event
     * {@link ActivityActionEvent}.
     * 
     * @see name.pehl.tire.client.activity.event.ActivityActionEvent.ActivityActionHandler#onActivityAction(name.pehl.tire.client.activity.event.ActivityActionEvent)
     */
    @Override
    public void onActivityAction(ActivityActionEvent event)
    {
        activityAction(event.getActivity(), event.getAction());
    }


    // ------------------------------------------------------------ ui handlers

    /**
     * This method is called from the <em>UI</em> event
     * {@link ActivityActionEvent}
     */
    @Override
    public void onActivityAction(Activity activity, Action action)
    {
        activityAction(activity, action);
    }


    @Override
    public void onSelectDate(Date date)
    {
        this.activityDate = date;
        logger.info("Selected date " + activityDate);
    }


    @Override
    public void onCurrentWeek()
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("current", WEEK.name().toLowerCase());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onCurrentMonth()
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard).with("current", MONTH.name().toLowerCase());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onSelectWeek(int left, int top)
    {
        addToPopupSlot(null);
        selectWeekPresenter.getView().setPosition(left, top);
        addToPopupSlot(selectWeekPresenter, false);
    }


    @Override
    public void onSelectMonth(int left, int top)
    {
        addToPopupSlot(null);
        selectMonthPresenter.getView().setPosition(left, top);
        addToPopupSlot(selectMonthPresenter, false);
    }


    @Override
    public void onPrevious()
    {
        PlaceRequest placeRequest = null;
        int newYear = activities.getYear();
        if (activities.getUnit() == MONTH)
        {
            int newMonth = activities.getMonth();
            newMonth--;
            if (newMonth < 1)
            {
                newMonth = 12;
                newYear--;
            }
            placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("month",
                    String.valueOf(newMonth));
        }
        else if (activities.getUnit() == WEEK)
        {
            int newWeek = activities.getWeek();
            newWeek--;
            if (newWeek < 1)
            {
                newWeek = 52;
                newYear--;
            }
            placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("week",
                    String.valueOf(newWeek));
        }
        if (placeRequest != null)
        {
            placeManager.revealPlace(placeRequest);
        }
    }


    @Override
    public void onNext()
    {
        PlaceRequest placeRequest = null;
        int newYear = activities.getYear();
        if (activities.getUnit() == MONTH)
        {
            int newMonth = activities.getMonth();
            newMonth++;
            if (newMonth > 12)
            {
                newMonth = 1;
                newYear++;
            }
            placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("month",
                    String.valueOf(newMonth));
        }
        else if (activities.getUnit() == WEEK)
        {
            int newWeek = activities.getWeek();
            newWeek++;
            if (newWeek > 52)
            {
                newWeek = 1;
                newYear++;
            }
            placeRequest = new PlaceRequest(dashboard).with("year", String.valueOf(newYear)).with("week",
                    String.valueOf(newWeek));
        }
        if (placeRequest != null)
        {
            placeManager.revealPlace(placeRequest);
        }
    }


    // ------------------------------------------------------- business methods

    void activityAction(Activity activity, Action action)
    {
        switch (action)
        {
            case DETAILS:
                details(activity);
                break;
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


    void details(Activity activity)
    {
        logger.fine("Open " + activity + " for edit");
        editActivityPresenter.getView().setActivity(activity);
        addToPopupSlot(null);
        addToPopupSlot(editActivityPresenter);
    }


    void save(Activity activity)
    {
        logger.fine("About to save " + activity);
        dispatcher.execute(new SaveActivityAction(activity), new TireCallback<SaveActivityResult>(getEventBus())
        {
            @Override
            public void onSuccess(SaveActivityResult result)
            {
                Activity savedActivity = result.getStoredActivity();
                update(savedActivity);
                ActivityChangedEvent.fire(DashboardPresenter.this, savedActivity, CHANGED);
                ShowMessageEvent.fire(DashboardPresenter.this,
                        new Message(INFO, "Activity \"" + savedActivity.getName() + "\" saved", true));
            }
        });
    }


    void copy(Activity activity)
    {
        logger.fine("About to copy " + activity);
        Activity plusOneDay = activity.plus(ONE_DAY_IN_MILLIS);
        dispatcher.execute(new SaveActivityAction(plusOneDay), new TireCallback<SaveActivityResult>(getEventBus())
        {
            @Override
            public void onSuccess(SaveActivityResult result)
            {
                Activity newActivity = result.getStoredActivity();
                update(newActivity);
                ActivityChangedEvent.fire(DashboardPresenter.this, newActivity, NEW);
                ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Activity \"" + newActivity.getName()
                        + "\" added", true));
            }
        });
    }


    void start(final Activity activity)
    {
        logger.info("About to start " + activity);
        if (activity.isStopped())
        {
            // if there's currently another activity running stop it.
            if (runningActivity != null && runningActivity.isRunning() && !runningActivity.equals(activity))
            {
                logger.info("Stopping currently running " + runningActivity);
                tickCommand.stop();
                runningActivity.stop();
                dispatcher.execute(new SaveActivityAction(runningActivity), new TireCallback<SaveActivityResult>(
                        getEventBus())
                {
                    @Override
                    public void onSuccess(SaveActivityResult result)
                    {
                        runningActivity = null;
                        Activity stoppedActivity = result.getStoredActivity();
                        logger.info("Successfully stopped " + stoppedActivity);
                        update(stoppedActivity);
                        start(activity);
                    }
                });
            }
            else
            {
                Activity latestActivity = activities != null ? activities.activities().first() : null;
                if (activity.isToday() && activity.equals(latestActivity))
                {
                    activity.resume();
                    logger.info("Resuming " + activity);
                    dispatcher.execute(new SaveActivityAction(activity), resumeCallback);
                }
                else
                {
                    Activity newActivity = activity.copy();
                    newActivity.start();
                    logger.info("Copy " + activity + " and start as a new " + newActivity);
                    dispatcher.execute(new SaveActivityAction(newActivity), startCallback);
                }
            }
        }
        else
        {
            logger.info(activity + " already running");
        }
    }


    void stop(Activity activity)
    {
        logger.info("About to stop " + runningActivity);
        if (activity.isRunning())
        {
            tickCommand.stop();
            activity.stop();
            dispatcher.execute(new SaveActivityAction(activity), new TireCallback<SaveActivityResult>(getEventBus())
            {
                @Override
                public void onSuccess(SaveActivityResult result)
                {
                    runningActivity = null;
                    Activity stoppedActivity = result.getStoredActivity();
                    update(stoppedActivity);
                    ActivityChangedEvent.fire(DashboardPresenter.this, stoppedActivity, STOPPED);
                    ShowMessageEvent.fire(DashboardPresenter.this,
                            new Message(INFO, "Activity \"" + stoppedActivity.getName() + "\" stopped", true));
                }
            });
        }
        else
        {
            logger.info(activity + " already stopped");
        }
    }


    void delete(final Activity activity)
    {
        logger.info("About to delete activity " + activity);
        if (activity.isRunning())
        {
            runningActivity = null;
            tickCommand.stop();
            activity.stop();
        }
        dispatcher.execute(new DeleteActivityAction(activity), new TireCallback<DeleteActivityResult>(getEventBus())
        {
            @Override
            public void onSuccess(DeleteActivityResult result)
            {
                activities.remove(activity);
                getView().updateActivities(activities);
                ActivityChangedEvent.fire(DashboardPresenter.this, activity, DELETE);
                ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Activity \"" + activity.getName()
                        + "\" deleted", true));
            }
        });
    }


    void update(Activity activity)
    {
        activities.update(activity);
        if (activities.matchingRange(activity))
        {
            activities.add(activity);
        }
        if (activities.contains(activity))
        {
            getView().updateActivities(activities);
        }
    }

    // --------------------------------------------------- commands & callbacks

    class GetActivitiesCommand implements ScheduledCommand
    {
        final ActivitiesRequest activitiesRequest;


        GetActivitiesCommand(ActivitiesRequest activitiesRequest)
        {
            this.activitiesRequest = activitiesRequest;
        }


        @Override
        public void execute()
        {
            dispatcher.execute(new GetActivitiesAction(activitiesRequest), new TireCallback<GetActivitiesResult>(
                    getEventBus())
            {
                @Override
                public void onSuccess(GetActivitiesResult result)
                {
                    activities = result.getActivities();
                    runningActivity = activities.getRunningActivity();
                    if (runningActivity != null)
                    {
                        tickCommand.update(runningActivity);
                    }
                    getView().updateActivities(activities);
                    ActivitiesLoadedEvent.fire(DashboardPresenter.this, activities);
                }


                @Override
                public void onFailure(Throwable caught)
                {
                    if (caught instanceof FailedStatusCodeException
                            && ((FailedStatusCodeException) caught).getStatusCode() == 404)
                    {
                        String errorMessage = "No activities found for " + activitiesRequest;
                        ShowMessageEvent.fire(DashboardPresenter.this, new Message(WARNING, errorMessage, true));
                        logger.warning(errorMessage);
                    }
                    else
                    {
                        super.onFailure(caught);
                    }
                }
            });
        }
    }

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
            update(runningActivity);
            ActivityChangedEvent.fire(DashboardPresenter.this, runningActivity, action);
            ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Activity \"" + runningActivity.getName()
                    + "\" " + action.name().toLowerCase(), true));
            tickCommand.start(runningActivity);
        }
    }
}
