package name.pehl.tire.client.application;

import name.pehl.tire.client.activity.Activities;
import name.pehl.tire.client.activity.ActivitiesLoadedEvent;
import name.pehl.tire.client.activity.ActivitiesNavigationData;
import name.pehl.tire.client.activity.GetActivitiesAction;
import name.pehl.tire.client.activity.GetActivitiesResult;
import name.pehl.tire.client.activity.QuickChartPresenter;
import name.pehl.tire.client.cockpit.CockpitPresenter;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.navigation.NavigationPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootLayoutContentEvent;

/**
 * This is the top-level presenter of the hierarchy. Other presenters reveal
 * themselves within this presenter. Therefore they must use the
 * {@link #TYPE_SetMainContent} slot.
 * <p>
 * Besides this slot the presenter contains the following nested
 * {@link PresenterWidget}s:
 * <ul>
 * <li>{@link NavigationPresenter} in {@link #SLOT_Navigation}
 * <li>{@link CockpitPresenter} in {@link #SLOT_Cockpit}
 * <li>{@link QuickChartPresenter} in {@link #SLOT_QuickChart}
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision: 102
 *          $
 */
public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
{
    @ProxyStandard
    public interface MyProxy extends Proxy<ApplicationPresenter>
    {
    }

    public interface MyView extends View
    {
    }

    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     */
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

    /**
     * Constant for the static navigation slot.
     */
    public static final Object SLOT_Navigation = new Object();

    /**
     * Constant for the static control slot.
     */
    public static final Object SLOT_Cockpit = new Object();

    /**
     * Constant for the static chart slot.
     */
    public static final Object SLOT_QuickChart = new Object();

    private final DispatchAsync dispatcher;
    private final PlaceManager placeManager;
    private final NavigationPresenter navigationPresenter;
    private final CockpitPresenter cockpitPresenter;
    private final QuickChartPresenter quickChartPresenter;


    @Inject
    public ApplicationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final DispatchAsync dispatcher, final PlaceManager placeManager,
            final NavigationPresenter navigationPresenter, final CockpitPresenter cockpitPresenter,
            final QuickChartPresenter quickChartPresenter)
    {
        super(eventBus, view, proxy);
        this.dispatcher = dispatcher;
        this.placeManager = placeManager;
        this.navigationPresenter = navigationPresenter;
        this.cockpitPresenter = cockpitPresenter;
        this.quickChartPresenter = quickChartPresenter;
    }


    @Override
    protected void revealInParent()
    {
        RevealRootLayoutContentEvent.fire(this, this);
        dispatcher.execute(new GetActivitiesAction(new ActivitiesNavigationData()),
                new TireCallback<GetActivitiesResult>(placeManager)
                {
                    @Override
                    public void onSuccess(GetActivitiesResult result)
                    {
                        Activities activities = result.getActivities();
                        if (activities != null)
                        {
                            ActivitiesLoadedEvent.fire(ApplicationPresenter.this, activities);
                        }
                    }
                });
    }


    /**
     * Sets {@link NavigationPresenter} in {@link #SLOT_Navigation},
     * {@link CockpitPresenter} in {@link #SLOT_Cockpit} and
     * {@link QuickChartPresenter} in {@link #SLOT_QuickChart}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        setInSlot(SLOT_Navigation, navigationPresenter);
        setInSlot(SLOT_Cockpit, cockpitPresenter);
        setInSlot(SLOT_QuickChart, quickChartPresenter);
    }
}
