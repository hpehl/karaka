package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final GetYearsHandler getYearsHandler,
            final GetActivitiesHandler getActivitiesHandler)
    {
        register(getYearsHandler);
        register(getActivitiesHandler);
    }
}
