package name.pehl.karaka.client.client;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.client.RefreshClientsEvent.RefreshClientsHandler;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.dispatch.RestException;
import name.pehl.karaka.client.model.AbstractModelCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.shared.model.Client;

import static name.pehl.karaka.client.logging.Logger.Category.cache;
import static name.pehl.karaka.client.logging.Logger.info;
import static name.pehl.karaka.client.logging.Logger.warn;

public class ClientsCache extends AbstractModelCache<Client> implements ModelCache<Client>, RefreshClientsHandler
{
    @Inject
    public ClientsCache(final Scheduler scheduler, final EventBus eventBus, final DispatchAsync dispatcher)
    {
        super(scheduler, eventBus, dispatcher);
        eventBus.addHandler(RefreshClientsEvent.getType(), this);
    }

    @Override
    public void refresh()
    {
        info(cache, "About to refresh clients...");
        scheduler.scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
                dispatcher.execute(new GetClientsAction(), new KarakaCallback<GetClientsResult>(eventBus)
                {
                    @Override
                    public void onSuccess(final GetClientsResult result)
                    {
                        models.clear();
                        models.addAll(result.getClients());
                        info(cache, "Clients refreshed.");
                    }

                    @Override
                    public void onNotFound(final RestException caught)
                    {
                        warn(cache, "No clients found.");
                    }
                });
            }
        });
    }

    @Override
    public void onRefreshClients(final RefreshClientsEvent event)
    {
        refresh();
    }
}
