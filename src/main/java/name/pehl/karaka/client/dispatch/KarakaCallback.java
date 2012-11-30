package name.pehl.karaka.client.dispatch;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;

import static java.util.logging.Level.SEVERE;
import static name.pehl.karaka.client.logging.Logger.Category.dispatch;
import static name.pehl.karaka.client.logging.Logger.error;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class KarakaCallback<T> implements AsyncCallback<T>
{
    private final EventBus eventBus;


    public KarakaCallback(final EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    /**
     * Call {@link #onNotFound(RestException)} in case of 404. In all other cases delegates to
     * {@link #showError(Throwable)}.
     *
     * @param caught
     *
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(final Throwable caught)
    {
        if (caught instanceof RestException)
        {
            RestException restException = (RestException) caught;
            if (restException.getStatusCode() == 404)
            {
                onNotFound(restException);
            }
            else
            {
                onRestFailure(restException);
            }
        }
        else
        {
            showError(caught);
        }
    }

    /**
     * Default implementation delegates to {@link #showError(Throwable)}
     *
     * @param caught
     */
    public void onRestFailure(final RestException caught)
    {
        showError(caught);
    }

    /**
     * Default implementation delegates to {@link #showError(Throwable)}
     *
     * @param caught
     */
    public void onNotFound(final RestException caught)
    {
        showError(caught);
    }

    protected void showError(final Throwable caught)
    {
        String message = caught.getMessage();
        error(dispatch, message);
        eventBus.fireEvent(new ShowMessageEvent(new Message(SEVERE, message, true)));
    }
}
