package name.pehl.tire.rest;

import name.pehl.taoki.security.SecureRouter;

import org.restlet.Context;

import com.google.inject.Injector;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
public class TireRouter extends SecureRouter
{
    public TireRouter(Injector injector, Context context)
    {
        super(injector, context);
    }


    @Override
    protected void attachRoutes()
    {
        // TODO attach("/{token}/projects", ProjectsResource.class);
    }
}
