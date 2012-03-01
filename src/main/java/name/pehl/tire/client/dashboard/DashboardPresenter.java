package name.pehl.tire.client.dashboard;

import static java.util.logging.Level.FINE;

import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.event.GetActivitiesAction;
import name.pehl.tire.client.activity.event.GetActivitiesResult;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesNavigationData;
import name.pehl.tire.client.activity.model.ActivitiesNavigationDataAdapter;
import name.pehl.tire.client.activity.presenter.NewActivityPresenter;
import name.pehl.tire.client.activity.presenter.RecentActivitiesPresenter;
import name.pehl.tire.client.application.ApplicationPresenter;
import name.pehl.tire.client.dispatch.TireCallback;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
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

    private boolean useCache;
    private ActivitiesNavigationData currentAnd;
    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private final NewActivityPresenter newActivityPresenter;
    private final RecentActivitiesPresenter recentActivitiesPresenter;


    @Inject
    public DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy, final DispatchAsync dispatcher,
            final PlaceManager placeManager, final NewActivityPresenter newActivityPresenter,
            final RecentActivitiesPresenter recentActivitiesPresenter)
    {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.newActivityPresenter = newActivityPresenter;
        this.recentActivitiesPresenter = recentActivitiesPresenter;

        this.currentAnd = null;
        this.useCache = false;
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
        setInSlot(SLOT_NewActivity, newActivityPresenter);
        setInSlot(SLOT_RecentActivities, recentActivitiesPresenter);
    }


    /**
     * Turns the parameters in the place request into an
     * {@link ActivitiesNavigationData} instance using the
     * {@link ActivitiesNavigationDataAdapter}. If the parsed
     * {@link ActivitiesNavigationData} differs from the cached one, new data is
     * requested from the server.
     * 
     * @param request
     * @see com.gwtplatform.mvp.client.Presenter#prepareFromRequest(com.gwtplatform.mvp.client.proxy.PlaceRequest)
     */
    @Override
    public void prepareFromRequest(PlaceRequest request)
    {
        super.prepareFromRequest(request);
        if (currentAnd != null && request.getParameterNames().isEmpty())
        {
            // Special case when activities are already present and coming from
            // another place
            useCache = true;
        }
        else
        {
            ActivitiesNavigationData and = new ActivitiesNavigationDataAdapter().fromPlaceRequest(request);
            // TODO Does caching really make sense, or is it better to request
            // the data from the server each time?
            if (!and.equals(currentAnd))
            {
                useCache = false;
                currentAnd = and;
            }
        }
    }


    @Override
    protected void onReset()
    {
        if (!useCache)
        {
            logger.log(FINE, "Requesting new activities");
            dispatcher.execute(new GetActivitiesAction(currentAnd), new TireCallback<GetActivitiesResult>(placeManager)
            {
                @Override
                public void onSuccess(GetActivitiesResult result)
                {
                    Activities activities = result.getActivities();
                    if (activities != null)
                    {
                        ActivitiesLoadedEvent.fire(DashboardPresenter.this, activities, currentAnd.getUnit());
                    }
                }
            });
        }
        else
        {
            logger.log(FINE, "Using cached activities");
        }
    }
}
