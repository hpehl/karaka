package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.activity.event.GetActivitiesHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final GetActivitiesHandler getActivitiesHandler)
    {
        register(getActivitiesHandler);
    }
}