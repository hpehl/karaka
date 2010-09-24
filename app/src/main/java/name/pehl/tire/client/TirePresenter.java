package name.pehl.tire.client;

import name.pehl.tire.client.status.StatusPresenter;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
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
 * This presenter contains the {@link StatusPresenter} which is set to slot
 * {@link #SLOT_StatusContent} in {@link #onReveal()}.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 88
 *          $
 */
public class TirePresenter extends Presenter<TirePresenter.MyView, TirePresenter.MyProxy>
{
    @ProxyStandard
    public interface MyProxy extends Proxy<TirePresenter>
    {
    }

    public interface MyView extends View
    {
        void highlight(String token);
    }

    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     */
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

    /**
     * Constant for the status slot.
     */
    public static final Object SLOT_StatusContent = new Object();

    private final StatusPresenter statusPresenter;
    private final PlaceManager placeManager;


    @Inject
    public TirePresenter(final EventBus eventBus, final PlaceManager placeManager, final MyView view,
            final MyProxy proxy, final StatusPresenter statusPresenter)
    {
        super(eventBus, view, proxy);
        this.placeManager = placeManager;
        this.statusPresenter = statusPresenter;
    }


    @Override
    protected void revealInParent()
    {
        RevealRootLayoutContentEvent.fire(this, this);
    }


    /**
     * Sets the {@link StatusPresenter} to slot {@link #SLOT_StatusContent}.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReveal()
     */
    @Override
    protected void onReveal()
    {
        setInSlot(SLOT_StatusContent, statusPresenter);
    }


    /**
     * Used to {@linkplain MyView#highlight(String) highlight} the current place
     * in the view.
     * 
     * @see com.gwtplatform.mvp.client.PresenterWidget#onReset()
     */
    @Override
    protected void onReset()
    {
        PlaceRequest request = placeManager.getCurrentPlaceRequest();
        String token = request.getNameToken();
        getView().highlight(token);
    }
}
