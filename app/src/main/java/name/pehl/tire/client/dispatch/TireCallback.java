package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.NameTokens;

import org.restlet.client.data.Status;
import org.restlet.client.resource.ResourceException;

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
     * Depending on the exception this method delegates to
     * <ul>
     * <li> {@link #onResourceException(Status, ResourceException)} in case of a
     * {@link ResourceException}
     * <li> {@link #onException(Throwable)} in all other cases
     * </ul>
     * 
     * @param caught
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public final void onFailure(Throwable caught)
    {
        if (caught instanceof ResourceException)
        {
            ResourceException re = (ResourceException) caught;
            Status status = re.getStatus();
            onResourceException(status, re);
        }
        else
        {
            onException(caught);
        }
    }


    /**
     * Called when a {@link ResourceException} was caught in
     * {@link #onFailure(Throwable)}. Reveals {@link NameTokens#dashboard}.
     * 
     * @param status
     * @param resourceException
     */
    protected void onResourceException(Status status, ResourceException resourceException)
    {
        // TODO Error handling
        placeManager.revealErrorPlace(NameTokens.dashboard);
    }


    /**
     * Called when a Non-{@link ResourceException} was caught in
     * {@link #onFailure(Throwable)}. Reveals {@link NameTokens#dashboard}.
     * 
     * @param caught
     */
    private void onException(Throwable caught)
    {
        // TODO Error handling
        placeManager.revealErrorPlace(NameTokens.dashboard);
    }
}
