package name.pehl.karaka.client.dispatch;

import name.pehl.karaka.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.karaka.client.activity.dispatch.FindActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.karaka.client.activity.dispatch.GetMinutesHandler;
import name.pehl.karaka.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetYearsHandler;
import name.pehl.karaka.client.activity.dispatch.SaveActivityHandler;
import name.pehl.karaka.client.client.GetClientsHandler;
import name.pehl.karaka.client.project.GetProjectsHandler;
import name.pehl.karaka.client.settings.GetSettingsHandler;
import name.pehl.karaka.client.tag.GetTagsHandler;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;

public class TireActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public TireActionHandlerRegistry(final DeleteActivityHandler deleteActivityHandler,
            final FindActivityHandler findActivityHandler, final GetActivitiesHandler getActivitiesHandler,
            final GetClientsHandler getClientsHandler, final GetMinutesHandler getMinutesHandler,
            final GetProjectsHandler getProjectsHandler, final GetRunningActivityHandler getRunningActivityHandler,
            final GetSettingsHandler getSettingsHandler, final GetTagsHandler getTagsHandler,
            final GetYearsHandler getYearsHandler, final SaveActivityHandler saveActivityHandler)
    {
        register(deleteActivityHandler);
        register(findActivityHandler);
        register(getActivitiesHandler);
        register(getClientsHandler);
        register(getMinutesHandler);
        register(getProjectsHandler);
        register(getRunningActivityHandler);
        register(getSettingsHandler);
        register(getTagsHandler);
        register(getYearsHandler);
        register(saveActivityHandler);
    }
}
