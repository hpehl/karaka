package name.pehl.karaka.client.project;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.dispatch.RestException;
import name.pehl.karaka.client.model.AbstractModelCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.client.project.RefreshProjectsEvent.RefreshProjectsHandler;
import name.pehl.karaka.shared.model.Project;

import static name.pehl.karaka.client.logging.Logger.Category.cache;
import static name.pehl.karaka.client.logging.Logger.info;
import static name.pehl.karaka.client.logging.Logger.warn;

public class ProjectsCache extends AbstractModelCache<Project> implements ModelCache<Project>, RefreshProjectsHandler
{
    @Inject
    public ProjectsCache(Scheduler scheduler, EventBus eventBus, DispatchAsync dispatcher)
    {
        super(scheduler, eventBus, dispatcher);
        eventBus.addHandler(RefreshProjectsEvent.getType(), this);
    }

    @Override
    public void refresh()
    {
        info(cache, "About to refresh projects...");
        scheduler.scheduleDeferred(new Scheduler.ScheduledCommand()
        {
            @Override
            public void execute()
            {
                dispatcher.execute(new GetProjectsAction(), new KarakaCallback<GetProjectsResult>(eventBus)
                {
                    @Override
                    public void onSuccess(GetProjectsResult result)
                    {
                        models.clear();
                        models.addAll(result.getProjects());
                        info(cache, "Projects refreshed.");
                    }

                    @Override
                    public void onNotFound(final RestException caught)
                    {
                        warn(cache, "No projects found.");
                    }

                });
            }
        });
    }

    @Override
    public void onRefreshProjects(RefreshProjectsEvent event)
    {
        refresh();
    }
}
