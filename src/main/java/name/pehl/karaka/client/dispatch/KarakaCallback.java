package name.pehl.karaka.client.dispatch;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import name.pehl.karaka.client.application.Message;
import name.pehl.karaka.client.application.ShowMessageEvent;
import org.fusesource.restygwt.client.FailedStatusCodeException;

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
     * Call {@link #onNotFound(FailedStatusCodeException)} in case of 404. In all other cases delegates to {@link
     * #showError(Throwable)}.
     *
     * @param caught
     *
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(final Throwable caught)
    {
        if (caught instanceof FailedStatusCodeException && ((FailedStatusCodeException) caught).getStatusCode() == 404)
        {
            onNotFound((FailedStatusCodeException) caught);
        }
        else if (caught instanceof FailedStatusCodeException)
        {
            onFailedStatus((FailedStatusCodeException) caught);
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
    public void onFailedStatus(FailedStatusCodeException caught)
    {
        showError(caught);
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
        error(dispatch, message);
        eventBus.fireEvent(new ShowMessageEvent(new Message(SEVERE, message, true)));
    }
}
