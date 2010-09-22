package name.pehl.tire.client;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
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
 * 
 * @author $Author$
 * @version $Date$ $Revision$
 */
public class TirePresenter extends Presenter<TirePresenter.MyView, TirePresenter.MyProxy>
{
    /**
     * Use this in leaf presenters, inside their {@link #revealInParent} method.
     */
    @ContentSlot
    public static final Type<RevealContentHandler<?>> TYPE_SetMainContent = new Type<RevealContentHandler<?>>();

    @ProxyStandard
    public interface MyProxy extends Proxy<TirePresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public TirePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealRootLayoutContentEvent.fire(this, this);
    }
}
