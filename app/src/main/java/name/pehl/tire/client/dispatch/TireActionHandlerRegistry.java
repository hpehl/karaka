package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.activity.week.GetWeekHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final GetWeekHandler getActivitiesByWeekHandler)
    {
        register(getActivitiesByWeekHandler);
    }
}
