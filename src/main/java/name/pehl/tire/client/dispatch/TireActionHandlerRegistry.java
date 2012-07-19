package name.pehl.tire.client.dispatch;

import name.pehl.tire.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.tire.client.activity.dispatch.GetMinutesHandler;
import name.pehl.tire.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.tire.client.activity.dispatch.GetYearsHandler;
import name.pehl.tire.client.activity.dispatch.LookupActivityHandler;
import name.pehl.tire.client.activity.dispatch.SaveActivityHandler;
import name.pehl.tire.client.project.LookupProjectHandler;
import name.pehl.tire.client.settings.GetSettingsHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final DeleteActivityHandler deleteActivityHandler,
            final GetActivitiesHandler getActivitiesHandler, final GetMinutesHandler getMinutesHandler,
            final GetRunningActivityHandler getRunningActivityHandler, final GetSettingsHandler getSettingsHandler,
            final GetYearsHandler getYearsHandler, final LookupActivityHandler lookupActivityHandler,
            final LookupProjectHandler lookupProjectHandler, final SaveActivityHandler saveActivityHandler)
    {
        register(deleteActivityHandler);
        register(getActivitiesHandler);
        register(getMinutesHandler);
        register(getRunningActivityHandler);
        register(getSettingsHandler);
        register(getYearsHandler);
        register(lookupActivityHandler);
        register(lookupProjectHandler);
        register(saveActivityHandler);
    }
}
