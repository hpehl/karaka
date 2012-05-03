package name.pehl.tire.client.dispatch;

import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

import static java.util.logging.Level.SEVERE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class TireCallback<T> implements AsyncCallback<T>
{
    private static Logger logger = Logger.getLogger(TireCallback.class.getName());
    private final EventBus eventBus;


    public TireCallback(EventBus eventBus)
    {
        this.eventBus = eventBus;
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
        String message = caught.getMessage();
        logger.severe(message);
        eventBus.fireEvent(new ShowMessageEvent(new Message(SEVERE, message)));
    }
}
