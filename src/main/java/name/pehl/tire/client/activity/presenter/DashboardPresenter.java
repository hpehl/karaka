package name.pehl.tire.client.activity.presenter;

import java.util.Date;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
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

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.DELETE;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.RESUMED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STARTED;
import static name.pehl.tire.client.activity.event.ActivityChanged.ChangeAction.STOPPED;
import static name.pehl.tire.shared.model.Status.RUNNING;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 13:52:44 +0100 (Do, 23. Dez 2010) $ $Revision: 192
 *          $
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> implements
        DashboardUiHandlers, RunningActivityLoadedHandler, ActivityActionHandler, TickHandler
{
    // ---------------------------------------------------------- inner classes

    @ProxyStandard
    @NameToken(NameTokens.dashboard)
    public interface MyProxy extends ProxyPlace<DashboardPresenter>
    {
    }

    public interface MyView extends View, HasUiHandlers<DashboardUiHandlers>
    {
        void updateActivities(Activities activities);
    }

    // ---------------------------------------------------------- private stuff

    private static final Logger logger = Logger.getLogger(DashboardPresenter.class.getName());

    private final Scheduler scheduler;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private final EditActivityPresenter editActivityPresenter;
    private final SelectYearAndMonthOrWeekPresenter selectMonthPresenter;
    private final SelectYearAndMonthOrWeekPresenter selectWeekPresenter;
    private final TickCommand tickCommand;
    private final StartResumeCallback startCallback;
    private final StartResumeCallback resumeCallback;

    /**
     * The currently managed actvity
     */
    private Activity currentActivity;
    /**
     * The selected date
     */
    private Date activityDate;
    /**
     * The currently displayed activities
     */
    private Activities activities;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy,
            final EditActivityPresenter editActivityPresenter,
            final SelectYearAndMonthOrWeekPresenter selectMonthPresenter,
            final SelectYearAndMonthOrWeekPresenter selectWeekPresenter, final DispatchAsync dispatcher,
            final PlaceManager placeManager, final Scheduler scheduler)
    {
        super(eventBus, view, proxy);
        this.editActivityPresenter = editActivityPresenter;
        this.selectMonthPresenter = selectMonthPresenter;
        this.selectMonthPresenter.setUnit(MONTH);
        this.selectWeekPresenter = selectWeekPresenter;
        this.selectWeekPresenter.setUnit(WEEK);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.scheduler = scheduler;
        this.tickCommand = new TickCommand(eventBus, scheduler, dispatcher);
        this.startCallback = new StartResumeCallback(eventBus, true);
        this.resumeCallback = new StartResumeCallback(eventBus, false);

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
        scheduler.scheduleDeferred(new ScheduledCommand()
        {
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
        });
    }


    // --------------------------------------------------------- event handlers

    @Override
    public void onRunningActivityLoaded(RunningActivityLoadedEvent event)
    {
        currentActivity = event.getActivity();
        if (activities.contains(currentActivity))
        {
            activities.remove(currentActivity);
            activities.add(currentActivity);
        }
        if (activities.matchingRange(currentActivity))
        {
            activities.add(currentActivity);
            getView().updateActivities(activities);
        }
        tickCommand.start(currentActivity);
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


    @Override
    public void onTick(TickEvent event)
    {
        Activity activity = event.getActivity();
        if (activities.getActivities().contains(activity))
        {
            getView().updateActivities(activities);
        }
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onSelectDate(Date date)
    {
        this.activityDate = date;
        logger.info("Selected date " + activityDate);
    }


    @Override
    public void onCurrentWeek()
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("current", WEEK.name().toLowerCase());
        placeManager.revealPlace(placeRequest);
    }


    @Override
    public void onCurrentMonth()
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard).with("current", MONTH.name().toLowerCase());
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
            placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", String.valueOf(newYear)).with("month",
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
            placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", String.valueOf(newYear)).with("week",
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
            placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", String.valueOf(newYear)).with("month",
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
            placeRequest = new PlaceRequest(NameTokens.dashboard).with("year", String.valueOf(newYear)).with("week",
                    String.valueOf(newWeek));
        }
        if (placeRequest != null)
        {
            placeManager.revealPlace(placeRequest);
        }
    }


    /**
     * This method is called from the <em>UI</em> event
     * {@link ActivityActionEvent}
     */
    @Override
    public void onActivityAction(Activity activity, Action action)
    {
        activityAction(activity, action);
    }


    // ------------------------------------------------------- business methods

    private void activityAction(Activity activity, Action action)
    {
        switch (action)
        {
            case EDIT:
                edit(activity);
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


    private void edit(Activity activity)
    {
        logger.fine("Open " + activity + " for edit");
        editActivityPresenter.getView().setActivity(activity);
        addToPopupSlot(null);
        addToPopupSlot(editActivityPresenter);
    }


    private void copy(Activity activity)
    {
        logger.fine("About to copy " + activity);
        ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Copy not yet implemented", true));
        // TODO Call server side resource which adds one day to the specified
        // activity
    }


    private void start(Activity activity)
    {
        // TODO Make this method somehow 'transactional'
        logger.info("About to start " + activity);
        if (activity.isStopped())
        {
            // if there's currently another activity running stop it.
            if (currentActivity != null && currentActivity.getStatus() == RUNNING && !activity.equals(currentActivity))
            {
                logger.info("Stopping currently running " + currentActivity);
                currentActivity.stop();
                dispatcher.execute(new SaveActivityAction(currentActivity), new TireCallback<SaveActivityResult>(
                        getEventBus())
                {
                    @Override
                    public void onSuccess(SaveActivityResult result)
                    {
                        logger.info("Successfully stopped " + result.getStoredActivity());
                    }
                });
            }
            Activity latestActivity = activities != null ? activities.getActivities().first() : null;
            if (activity.isToday() && activity.equals(latestActivity))
            {
                logger.info("Resuming " + activity);
                activity.resume();
                dispatcher.execute(new SaveActivityAction(activity), resumeCallback);
            }
            else
            {
                logger.info("Copy " + activity + " and start as a new activity");
                Activity newActivity = activity.copy();
                newActivity.start();
                dispatcher.execute(new SaveActivityAction(newActivity), startCallback);
            }
        }
        else
        {
            logger.info(activity + " already running");
        }
    }


    private void stop(Activity activity)
    {
        // TODO Make this method somehow 'transactional'
        logger.info("About to stop " + currentActivity);
        if (activity.isRunning())
        {
            logger.info("Stopping " + currentActivity);
            tickCommand.stop();
            activity.stop();
            dispatcher.execute(new SaveActivityAction(activity), new TireCallback<SaveActivityResult>(getEventBus())
            {
                @Override
                public void onSuccess(SaveActivityResult result)
                {
                    Activity previousActivity = currentActivity;
                    currentActivity = result.getStoredActivity();
                    if (activities.contains(previousActivity))
                    {
                        activities.remove(previousActivity);
                        activities.add(currentActivity);
                    }
                    getView().updateActivities(activities);
                    ActivityChangedEvent.fire(DashboardPresenter.this, currentActivity, STOPPED);
                    ShowMessageEvent.fire(DashboardPresenter.this,
                            new Message(INFO, "Activity \"" + currentActivity.getName() + "\" stopped", true));
                }
            });
        }
        else
        {
            logger.info(activity + " already stopped");
        }
    }


    private void delete(final Activity activity)
    {
        logger.info("About to delete activity " + currentActivity);
        if (activity.isRunning())
        {
            tickCommand.stop();
            activity.stop();
            ActivityChangedEvent.fire(DashboardPresenter.this, currentActivity, STOPPED);
        }
        dispatcher.execute(new DeleteActivityAction(activity), new TireCallback<DeleteActivityResult>(getEventBus())
        {
            @Override
            public void onSuccess(DeleteActivityResult result)
            {
                currentActivity = null;
                activities.remove(activity);
                getView().updateActivities(activities);
                ActivityChangedEvent.fire(DashboardPresenter.this, activity, DELETE);
                ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Activity \"" + activity.getName()
                        + "\" deleted", true));
            }
        });
    }

    class StartResumeCallback extends TireCallback<SaveActivityResult>
    {
        final boolean start;


        StartResumeCallback(EventBus eventBus, boolean start)
        {
            super(eventBus);
            this.start = start;
        }


        @Override
        public void onSuccess(SaveActivityResult result)
        {
            Activity previousActivity = currentActivity;
            currentActivity = result.getStoredActivity();
            if (activities.contains(previousActivity))
            {
                activities.remove(previousActivity);
                activities.add(currentActivity);
            }
            if (activities.matchingRange(currentActivity))
            {
                activities.add(currentActivity);
                getView().updateActivities(activities);
            }
            ActivityChangedEvent.fire(DashboardPresenter.this, currentActivity, start ? STARTED : RESUMED);
            ShowMessageEvent.fire(DashboardPresenter.this, new Message(INFO, "Activity \"" + currentActivity.getName()
                    + "\" " + (start ? "started" : "resumed"), true));
            tickCommand.start(currentActivity);
        }
    }
}
