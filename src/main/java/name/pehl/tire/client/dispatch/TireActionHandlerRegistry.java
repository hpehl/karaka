package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetMinutesHandler;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final GetActivitiesHandler getActivitiesHandler,
            final GetMinutesHandler getMinutesHandler, final GetRunningActivityHandler getRunningActivityHandler,
            final GetYearsHandler getYearsHandler)
    {
        register(getActivitiesHandler);
        register(getMinutesHandler);
        register(getRunningActivityHandler);
        register(getYearsHandler);
    }
}
