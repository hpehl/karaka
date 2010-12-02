package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.activity.GetActivitiesByWeekHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final GetActivitiesByWeekHandler getActivitiesByWeekHandler)
    {
        register(getActivitiesByWeekHandler);
    }
}
