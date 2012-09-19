package name.pehl.tire.client.client;

import static java.util.logging.Level.INFO;
import name.pehl.tire.client.client.RefreshClientsEvent.RefreshClientsHandler;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.model.AbstractModelCache;
import name.pehl.tire.client.model.ModelCache;
import name.pehl.tire.shared.model.Client;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class ClientsCache extends AbstractModelCache<Client> implements ModelCache<Client>, RefreshClientsHandler
{
    @Inject
    public ClientsCache(final EventBus eventBus, final Scheduler scheduler, final DispatchAsync dispatcher)
    {
        super(eventBus, dispatcher);
        eventBus.addHandler(RefreshClientsEvent.getType(), this);
    }


    @Override
    public void refresh()
    {
        logger.log(INFO, "About to refresh clients...");
        dispatcher.execute(new GetClientsAction(), new TireCallback<GetClientsResult>(eventBus)
        {
            @Override
            public void onSuccess(final GetClientsResult result)
            {
                models.clear();
                models.addAll(result.getClients());
                logger.log(INFO, "Projects refreshed.");
            }
        });
    }


    @Override
    public void onRefreshClients(final RefreshClientsEvent event)
    {
        refresh();
    }
}
