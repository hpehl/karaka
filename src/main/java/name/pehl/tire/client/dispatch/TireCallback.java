package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.NameTokens;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class TireCallback<T> implements AsyncCallback<T>
{
    private final PlaceManager placeManager;


    public TireCallback(PlaceManager placeManager)
    {
        this.placeManager = placeManager;
    }


    /**
     * Reveals {@link NameTokens#dashboard}.
     * 
     * @param caught
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(Throwable caught)
    {
        // TODO Error handling
        placeManager.revealErrorPlace(NameTokens.dashboard);
    }
}
