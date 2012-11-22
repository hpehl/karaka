package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import name.pehl.karaka.client.client.ClientsCache;

import java.util.Iterator;

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
    public void execute(Iterator<BootstrapStep> iterator, final Command command)
    {
        clientsCache.refresh();
        next(iterator, command);
    }
}
