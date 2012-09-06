package name.pehl.tire.client.project;

import static java.util.logging.Level.INFO;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.model.AbstractModelCache;
import name.pehl.tire.client.model.ModelCache;
import name.pehl.tire.client.project.RefreshProjectsEvent.RefreshProjectsHandler;
import name.pehl.tire.shared.model.Project;

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
        dispatcher.execute(new GetProjectsAction(), new TireCallback<GetProjectsResult>(eventBus)
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
