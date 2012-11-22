package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import name.pehl.karaka.client.client.ClientsCache;

import java.util.Iterator;

import static java.lang.Boolean.TRUE;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadClients extends BootstrapStep
{
    private final ClientsCache clientsCache;

    @Inject
    public LoadClients(final ClientsCache clientsCache)
    {
        this.clientsCache = clientsCache;
    }

    @Override
    public void execute(Iterator<BootstrapStep> iterator, final AsyncCallback<Boolean> callback)
    {
        clientsCache.refresh();
        callback.onSuccess(TRUE);

        next(iterator, callback);
    }
}
