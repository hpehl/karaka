package name.pehl.tire.client.application;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
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

    private final NavigationPresenter navigationPresenter;
    private final CockpitPresenter controlPresenter;
    private final QuickChartPresenter chartPresenter;


    @Inject
    public ApplicationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final NavigationPresenter navigationPresenter, final CockpitPresenter controlPresenter,
            final QuickChartPresenter chartPresenter)
    {
        super(eventBus, view, proxy);
        this.navigationPresenter = navigationPresenter;
        this.controlPresenter = controlPresenter;
        this.chartPresenter = chartPresenter;
    }


    @Override
    protected void revealInParent()
    {
        RevealRootLayoutContentEvent.fire(this, this);
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
        setInSlot(SLOT_Cockpit, controlPresenter);
        setInSlot(SLOT_QuickChart, chartPresenter);
    }
}
