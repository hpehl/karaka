package name.pehl.tire.client.dispatch;

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
        super();
        this.placeManager = placeManager;
    }


    /**
     * Reveals the error place
     * 
     * @param caught
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(Throwable caught)
    {
        // TODO Set place parameter
        placeManager.revealPlace(null);
    }
}
