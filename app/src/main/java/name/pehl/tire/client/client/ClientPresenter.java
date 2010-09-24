package name.pehl.tire.client.client;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.TirePresenter;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

/**
 * @author $Author$
 * @version $Date$ $Revision: 85
 *          $
 */
public class ClientPresenter extends Presenter<ClientPresenter.MyView, ClientPresenter.MyProxy>
{
    @ProxyCodeSplit
    @NameToken(NameTokens.clients)
    public interface MyProxy extends ProxyPlace<ClientPresenter>
    {
    }

    public interface MyView extends View
    {
    }


    @Inject
    public ClientPresenter(EventBus eventBus, MyView view, MyProxy proxy)
    {
        super(eventBus, view, proxy);
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, TirePresenter.TYPE_SetMainContent, this);
    }
}
