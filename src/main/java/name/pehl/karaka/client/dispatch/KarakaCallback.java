package name.pehl.karaka.client.dispatch;

import static java.util.logging.Level.SEVERE;

import java.util.logging.Logger;

import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;

import org.fusesource.restygwt.client.FailedStatusCodeException;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class KarakaCallback<T> implements AsyncCallback<T>
{
    private static Logger logger = Logger.getLogger(KarakaCallback.class.getName());
    private final EventBus eventBus;


    public KarakaCallback(final EventBus eventBus)
    {
        this.eventBus = eventBus;
    }


    /**
     * Call {@link #onNotFound(FailedStatusCodeException)} in case of 404. In
     * all other cases delegates to {@link #showError(Throwable)}.
     * 
     * @param caught
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(final Throwable caught)
    {
        if (caught instanceof FailedStatusCodeException && ((FailedStatusCodeException) caught).getStatusCode() == 404)
        {
            onNotFound((FailedStatusCodeException) caught);
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
    public void onNotFound(final FailedStatusCodeException caught)
    {
        showError(caught);
    }


    protected void showError(final Throwable caught)
    {
        String message = caught.getMessage();
        logger.severe(message);
        eventBus.fireEvent(new ShowMessageEvent(new Message(SEVERE, message, true)));
    }
}
