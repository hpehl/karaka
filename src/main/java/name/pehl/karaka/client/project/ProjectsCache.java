package name.pehl.karaka.client.project;

import static java.util.logging.Level.INFO;
import name.pehl.karaka.client.dispatch.KarakaCallback;
import name.pehl.karaka.client.model.AbstractModelCache;
import name.pehl.karaka.client.model.ModelCache;
import name.pehl.karaka.client.project.RefreshProjectsEvent.RefreshProjectsHandler;
import name.pehl.karaka.shared.model.Project;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class ProjectsCache extends AbstractModelCache<Project> implements ModelCache<Project>, RefreshProjectsHandler
{
    @Inject
    public ProjectsCache(EventBus eventBus, Scheduler scheduler, DispatchAsync dispatcher)
    {
        super(eventBus, dispatcher);
        eventBus.addHandler(RefreshProjectsEvent.getType(), this);
    }


    @Override
    public void refresh()
    {
        logger.log(INFO, "About to refresh projects...");
        dispatcher.execute(new GetProjectsAction(), new KarakaCallback<GetProjectsResult>(eventBus)
        {
            @Override
            public void onSuccess(GetProjectsResult result)
            {
                models.clear();
                models.addAll(result.getProjects());
                logger.log(INFO, "Projects refreshed.");
            }
        });
    }


    @Override
    public void onRefreshProjects(RefreshProjectsEvent event)
    {
        refresh();
    }
}
