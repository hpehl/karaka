package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import name.pehl.karaka.client.client.ClientsCache;

import static java.lang.Boolean.TRUE;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadClients implements BootstrapCommand<Boolean>
{
    private final ClientsCache clientsCache;

    @Inject
    public LoadClients(final ClientsCache clientsCache)
    {
        this.clientsCache = clientsCache;
    }

    @Override
    public void execute(final AsyncCallback<Boolean> callback)
    {
        clientsCache.refresh();
        callback.onSuccess(TRUE);
    }
}
