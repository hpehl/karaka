package name.pehl.tire.client.activity.presenter;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.Date;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.ActivitiesRequest;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;

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
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 13:52:44 +0100 (Do, 23. Dez 2010) $ $Revision: 192
 *          $
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> implements
        DashboardUiHandlers, ActivitiesLoadedHandler
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

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private final EditActivityPresenter editActivityPresenter;
    private final SelectYearAndMonthOrWeekPresenter selectMonthPresenter;
    private final SelectYearAndMonthOrWeekPresenter selectWeekPresenter;

    private Date activityDate;
    private Activities activities;


    // ------------------------------------------------------------------ setup

    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy,
            final EditActivityPresenter editActivityPresenter,
            final SelectYearAndMonthOrWeekPresenter selectMonthPresenter,
            final SelectYearAndMonthOrWeekPresenter selectWeekPresenter, final DispatchAsync dispatcher,
            final PlaceManager placeManager)
    {
        super(eventBus, view, proxy);
        this.editActivityPresenter = editActivityPresenter;
        this.selectMonthPresenter = selectMonthPresenter;
        this.selectMonthPresenter.setUnit(MONTH);
        this.selectWeekPresenter = selectWeekPresenter;
        this.selectWeekPresenter.setUnit(WEEK);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;

        getView().setUiHandlers(this);
        getEventBus().addHandler(ActivitiesLoadedEvent.getType(), this);
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
        Scheduler.get().scheduleDeferred(new ScheduledCommand()
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
                        Activities activities = result.getActivities();
                        if (activities != null)
                        {
                            // don't just call
                            // getView().updateActivities(activities) as
                            // other classes might also be interested in updated
                            // activities.
                            ActivitiesLoadedEvent.fire(DashboardPresenter.this, activities);
                        }
                    }


                    @Override
                    public void onFailure(Throwable caught)
                    {
                        String errorMessage = "Failed to load activities for " + activitiesRequest;
                        ShowMessageEvent.fire(DashboardPresenter.this, new Message(SEVERE, errorMessage, true));
                        logger.severe(errorMessage);
                    }
                });
            }
        });
    }


    // --------------------------------------------------------- event handlers

    @Override
    public final void onActivitiesLoaded(ActivitiesLoadedEvent event)
    {
        activities = event.getActivities();
        getView().updateActivities(activities);
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


    @Override
    public void onEdit(int rowIndex, Activity activity)
    {
        logger.fine("Edit " + activity + " in row #" + rowIndex);
        editActivityPresenter.getView().setActivity(activity);
        addToPopupSlot(null);
        addToPopupSlot(editActivityPresenter);
    }


    @Override
    public void onCopy(int rowIndex, Activity activity)
    {
        logger.fine("Copy " + activity + " in row #" + rowIndex);
    }


    @Override
    public void onGoon(int rowIndex, Activity activity)
    {
        logger.fine("Continue " + activity + " in row #" + rowIndex);
    }


    @Override
    public void onDelete(int rowIndex, Activity activity)
    {
        logger.fine("Delete " + activity + " in row #" + rowIndex);
    }
}
