package name.pehl.tire.client.dispatch;

import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.application.Message;
import name.pehl.tire.client.application.ShowMessageEvent;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;

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
        logger.severe(caught.getMessage());
        ShowMessageEvent event = new ShowMessageEvent(new Message("No activities found"));
        eventBus.fireEvent(event);
    }
}
