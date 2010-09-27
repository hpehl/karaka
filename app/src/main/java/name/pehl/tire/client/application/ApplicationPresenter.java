package name.pehl.tire.client.application;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
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
 * <li>{@link ControlPresenter} in {@link #SLOT_Control}
 * <li>{@link ChartPresenter} in {@link #SLOT_Chart}
 * </ul>
 * 
 * @author $Author$
 * @version $Date$ $Revision$
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
    public static final Object SLOT_Control = new Object();

    /**
     * Constant for the static chart slot.
     */
    public static final Object SLOT_Chart = new Object();

    private final NavigationPresenter navigationPresenter;
    private final ControlPresenter controlPresenter;
    private final ChartPresenter chartPresenter;
    private final PlaceManager placeManager;


    @Inject
    public ApplicationPresenter(final EventBus eventBus, final PlaceManager placeManager, final MyView view,
            final MyProxy proxy, final NavigationPresenter navigationPresenter,
            final ControlPresenter controlPresenter, final ChartPresenter chartPresenter)
    {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
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
     * {@link ControlPresenter} in {@link #SLOT_Control} and
     * {@link ChartPresenter} in {@link #SLOT_Chart}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        setInSlot(SLOT_Navigation, navigationPresenter);
        setInSlot(SLOT_Control, controlPresenter);
        setInSlot(SLOT_Chart, chartPresenter);
    }


    /**
     * {@linkplain NavigationPresenter.MyView#highlight(String) Highlights} the
     * current place in the {@linkplain NavigationPresenter.MyView navigation
     * view}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset()
    {
        PlaceRequest request = placeManager.getCurrentPlaceRequest();
        String token = request.getNameToken();
        navigationPresenter.getView().highlight(token);
    }
}
