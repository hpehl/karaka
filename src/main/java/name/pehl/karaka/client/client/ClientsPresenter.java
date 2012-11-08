package name.pehl.karaka.client.client;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import name.pehl.karaka.client.NameTokens;
import name.pehl.karaka.client.application.ApplicationPresenter;
import name.pehl.karaka.client.client.ClientAction.Action;
import name.pehl.karaka.shared.model.Client;

import java.util.List;

/**
 * <h3>Events</h3>
 * <ol>
 * <li>IN</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * <li>OUT</li>
 * <ul>
 * <li>none</li>
 * </ul>
 * </ol>
 * <h3>Dispatcher actions</h3>
 * <ul>
 * <li>none</li>
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-01 22:35:01 +0100 (Mi, 01. Dez 2010) $ $Revision: 85
 *          $
 */
public class ClientsPresenter extends Presenter<ClientsPresenter.MyView, ClientsPresenter.MyProxy> implements
        ClientsUiHandlers
{
    // ---------------------------------------------------------- inner classes

    @ProxyCodeSplit
    @NameToken(NameTokens.clients)
    public interface MyProxy extends ProxyPlace<ClientsPresenter>
    {
    }

    public interface MyView extends View, HasUiHandlers<ClientsUiHandlers>
    {
        void updateClients(List<Client> clients);
    }

    // ------------------------------------------------------- (static) members

    final ClientsCache clientsCache;


    @Inject
    public ClientsPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
            final ClientsCache clientsCache)
    {
        super(eventBus, view, proxy);
        this.clientsCache = clientsCache;
        getView().setUiHandlers(this);
    }


    // ---------------------------------------------------- presenter lifecycle

    @Override
    public void prepareFromRequest(final PlaceRequest placeRequest)
    {
        super.prepareFromRequest(placeRequest);
        getView().updateClients(clientsCache.list());
    }


    @Override
    protected void revealInParent()
    {
        RevealContentEvent.fire(this, ApplicationPresenter.TYPE_SetMainContent, this);
    }


    // ------------------------------------------------------------ ui handlers

    @Override
    public void onClientAction(final Action action, final Client client)
    {
        // TODO Auto-generated method stub
    }
}
