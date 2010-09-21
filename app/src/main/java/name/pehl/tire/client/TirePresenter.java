package name.pehl.tire.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

/**
 * This is the top-level presenter of the hierarchy. Other presenters reveal
 * themselves within this presenter.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class TirePresenter extends Presenter<TirePresenter.View, TirePresenter.Proxy>
{
    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     */
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

    @ProxyStandard
    public interface Proxy extends com.gwtplatform.mvp.client.proxy.Proxy<TirePresenter>, Place
    {
    }

    public interface View extends com.gwtplatform.mvp.client.View
    {
    }


    @Inject
    public TirePresenter(final EventBus eventBus, final View view, final Proxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealRootContentEvent.fire(this, this);
    }

}
