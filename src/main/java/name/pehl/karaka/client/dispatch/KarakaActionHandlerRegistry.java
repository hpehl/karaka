package name.pehl.karaka.client.dispatch;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.DefaultClientActionHandlerRegistry;
import name.pehl.karaka.client.activity.dispatch.CopyActivityHandler;
import name.pehl.karaka.client.activity.dispatch.DeleteActivityHandler;
import name.pehl.karaka.client.activity.dispatch.FindActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetActivitiesHandler;
import name.pehl.karaka.client.activity.dispatch.GetDurationsHandler;
import name.pehl.karaka.client.activity.dispatch.GetLatestActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetRunningActivityHandler;
import name.pehl.karaka.client.activity.dispatch.GetYearsHandler;
import name.pehl.karaka.client.activity.dispatch.SaveActivityHandler;
import name.pehl.karaka.client.activity.dispatch.StartActivityHandler;
import name.pehl.karaka.client.activity.dispatch.StopActivityHandler;
import name.pehl.karaka.client.activity.dispatch.TickActivityHandler;
import name.pehl.karaka.client.client.GetClientsHandler;
import name.pehl.karaka.client.project.GetProjectsHandler;
import name.pehl.karaka.client.settings.GetSettingsHandler;
import name.pehl.karaka.client.tag.GetTagsHandler;

public class KarakaActionHandlerRegistry extends DefaultClientActionHandlerRegistry
{
    @Inject
    public KarakaActionHandlerRegistry(final CopyActivityHandler copyActivityHandler,
            final DeleteActivityHandler deleteActivityHandler,
            final FindActivityHandler findActivityHandler,
            final GetActivitiesHandler getActivitiesHandler,
            final GetClientsHandler getClientsHandler,
            final GetDurationsHandler getDurationsHandler,
            final GetLatestActivityHandler getLatestActivityHandler,
            final GetProjectsHandler getProjectsHandler,
            final GetRunningActivityHandler getRunningActivityHandler,
            final GetSettingsHandler getSettingsHandler,
            final GetTagsHandler getTagsHandler,
            final GetYearsHandler getYearsHandler,
            final SaveActivityHandler saveActivityHandler,
            final StartActivityHandler startActivityHandler,
            final StopActivityHandler stopActivityHandler,
            final TickActivityHandler tickActivityHandler)
    {
        register(copyActivityHandler);
        register(deleteActivityHandler);
        register(findActivityHandler);
        register(getActivitiesHandler);
        register(getClientsHandler);
        register(getDurationsHandler);
        register(getLatestActivityHandler);
        register(getProjectsHandler);
        register(getRunningActivityHandler);
        register(getSettingsHandler);
        register(getTagsHandler);
        register(getYearsHandler);
        register(saveActivityHandler);
        register(startActivityHandler);
        register(stopActivityHandler);
        register(tickActivityHandler);
    }
}
