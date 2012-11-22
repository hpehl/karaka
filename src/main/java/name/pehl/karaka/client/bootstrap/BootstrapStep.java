package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Iterator;

import static name.pehl.karaka.client.logging.Logger.Category.bootstrap;
import static name.pehl.karaka.client.logging.Logger.info;

public abstract class BootstrapStep
{
    public abstract void execute(Iterator<BootstrapStep> iterator, AsyncCallback<Boolean> outcome);

    protected final void next(final Iterator<BootstrapStep> iterator, final AsyncCallback<Boolean> outcome)
    {
        if (iterator.hasNext())
        {
            BootstrapStep next = iterator.next();
            info(bootstrap, next.getClass().getName());
            next.execute(iterator, outcome);
        }
    }
}
