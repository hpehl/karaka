package name.pehl.tire.client.activity.presenter;

import java.util.Date;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent.ActivitiesLoadedHandler;
import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.ActivitiesFormater;
import name.pehl.tire.client.activity.model.ActivitiesNavigator;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.resources.I18n;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Activity;
import name.pehl.tire.shared.model.TimeUnit;

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

import static java.util.logging.Level.FINE;
import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

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

    private final I18n i18n;
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
            final PlaceManager placeManager, final I18n i18n)
    {
        super(eventBus, view, proxy);
        this.editActivityPresenter = editActivityPresenter;
        this.selectMonthPresenter = selectMonthPresenter;
        this.selectMonthPresenter.setUnit(MONTH);
        this.selectWeekPresenter = selectWeekPresenter;
        this.selectWeekPresenter.setUnit(WEEK);
        this.i18n = i18n;
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
     * {@link ActivitiesNavigator} instance.
     * 
     * @param request
     * @see com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform.mvp.client.proxy.PlaceRequest)
     */
    @Override
    public void prepareFromRequest(PlaceRequest request)
    {
        super.prepareFromRequest(request);
        final ActivitiesNavigator activitiesNavigator = ActivitiesNavigator.fromPlaceRequest(request);
        final String instant = new ActivitiesFormater().instant(activitiesNavigator, i18n.enums());
        String message = "Loading activities for " + instant + "...";
        logger.log(FINE, message);
        ShowMessageEvent.fire(this, new Message(message));
        dispatcher.execute(new GetActivitiesAction(activitiesNavigator), new TireCallback<GetActivitiesResult>(
                getEventBus())
        {
            @Override
            public void onSuccess(GetActivitiesResult result)
            {
                Activities activities = result.getActivities();
                if (activities != null)
                {
                    // don't just call getView().updateActivities(activities) as
                    // other classes might also be interested about updated
                    // activities.
                    ActivitiesLoadedEvent.fire(DashboardPresenter.this, activities, activitiesNavigator.getUnit());
                }
            }


            @Override
            public void onFailure(Throwable caught)
            {
                String errorMessage = "Failed to load activities for " + instant;
                ShowMessageEvent.fire(DashboardPresenter.this, new Message(errorMessage));
                logger.severe(errorMessage);
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
        logger.fine("Selected date " + activityDate);
    }


    @Override
    public void onCurrentWeek()
    {
        PlaceRequest request = new PlaceRequest(NameTokens.dashboard).with("current", TimeUnit.WEEK.name()
                .toLowerCase());
        placeManager.revealPlace(request);
    }


    @Override
    public void onCurrentMonth()
    {
        PlaceRequest request = new PlaceRequest(NameTokens.dashboard).with("current", TimeUnit.MONTH.name()
                .toLowerCase());
        placeManager.revealPlace(request);
    }


    @Override
    public void onSelectWeek(int left, int top)
    {
        selectMonthPresenter.getView().setPosition(left, top);
        addToPopupSlot(selectMonthPresenter, false);
    }


    @Override
    public void onSelectMonth(int left, int top)
    {
        selectMonthPresenter.getView().setPosition(left, top);
        addToPopupSlot(selectWeekPresenter, false);
    }


    @Override
    public void onPrevious()
    {
        PlaceRequest request = new PlaceRequest(NameTokens.dashboard).with("previous", activities.getUnit().name()
                .toLowerCase());
        placeManager.revealPlace(request);
    }


    @Override
    public void onNext()
    {
        PlaceRequest request = new PlaceRequest(NameTokens.dashboard).with("next", activities.getUnit().name()
                .toLowerCase());
        placeManager.revealPlace(request);
    }


    @Override
    public void onEdit(int rowIndex, Activity activity)
    {
        logger.fine("Edit " + activity + " in row #" + rowIndex);
        editActivityPresenter.getView().setActivity(activity);
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