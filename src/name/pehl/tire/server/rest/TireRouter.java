package name.pehl.tire.server.rest;

import name.pehl.taoki.security.SecurityRouter;
import name.pehl.tire.server.project.ProjectsResource;

import org.restlet.Context;

import com.google.inject.Injector;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class TireRouter extends SecurityRouter
{
    public TireRouter(Injector injector, Context context)
    {
        super(injector, context);
    }


    @Override
    protected void attachRoutes()
    {
        attach("/{token}/projects", ProjectsResource.class);
    }
}
