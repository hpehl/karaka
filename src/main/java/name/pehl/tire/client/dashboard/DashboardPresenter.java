package name.pehl.tire.client.dashboard;

import static java.util.logging.Level.FINE;

import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.ActivitiesNavigator;
import name.pehl.tire.client.activity.presenter.NewActivityPresenter;
import name.pehl.tire.client.activity.presenter.RecentActivitiesPresenter;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.shared.model.Activities;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-23 13:52:44 +0100 (Do, 23. Dez 2010) $ $Revision: 192
 *          $
 */
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy>
{
    @ProxyStandard
    @NameToken(NameTokens.dashboard)
    public interface MyProxy extends ProxyPlace<DashboardPresenter>
    {
    }

    public interface MyView extends View
    {
    }

    /**
     * Constant for the new activity slot.
     */
    public static final Object SLOT_NewActivity = new Object();

    /**
     * Constant for the recent activities slot.
     */
    public static final Object SLOT_RecentActivities = new Object();

    private static final Logger logger = Logger.getLogger(DashboardPresenter.class.getName());

    private final DispatchAsync dispatcher;
    private final NewActivityPresenter newActivityPresenter;
    private final RecentActivitiesPresenter recentActivitiesPresenter;


    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy, final DispatchAsync dispatcher,
            final NewActivityPresenter newActivityPresenter, final RecentActivitiesPresenter recentActivitiesPresenter)
    {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.newActivityPresenter = newActivityPresenter;
        this.recentActivitiesPresenter = recentActivitiesPresenter;
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }


    /**
     * Sets the {@link NewActivityPresenter} to slot {@link #SLOT_NewActivity}
     * and {@link RecentActivitiesPresenter} to slot
     * {@link #SLOT_RecentActivities}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        super.onReveal();
        setInSlot(SLOT_NewActivity, newActivityPresenter);
        setInSlot(SLOT_RecentActivities, recentActivitiesPresenter);
    }


    @Override
    protected void onHide()
    {
        super.onHide();
        removeFromSlot(SLOT_NewActivity, newActivityPresenter);
        removeFromSlot(SLOT_RecentActivities, recentActivitiesPresenter);
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
        logger.log(FINE, "Requesting new activities");
        ShowMessageEvent.fire(this, new Message("Loading activities..."));
        dispatcher.execute(new GetActivitiesAction(activitiesNavigator), new TireCallback<GetActivitiesResult>(
                getEventBus())
        {
            @Override
            public void onSuccess(GetActivitiesResult result)
            {
                Activities activities = result.getActivities();
                if (activities != null)
                {
                    ActivitiesLoadedEvent.fire(DashboardPresenter.this, activities, activitiesNavigator.getUnit());
                }
            }
        });
    }
}
