package name.pehl.karaka.client.bootstrap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import name.pehl.karaka.client.project.ProjectsCache;

import java.util.Iterator;

import static java.lang.Boolean.TRUE;

/**
 * @author Harald Pehl
 * @date 11/08/2012
 */
public class LoadProjects extends BootstrapStep
{
    private final ProjectsCache projectsCache;

    @Inject
    public LoadProjects(final ProjectsCache projectsCache)
    {
        this.projectsCache = projectsCache;
    }

    @Override
    public void execute(Iterator<BootstrapStep> iterator, final AsyncCallback<Boolean> callback)
    {
        projectsCache.refresh();
        callback.onSuccess(TRUE);

        next(iterator, callback);
    }
}
