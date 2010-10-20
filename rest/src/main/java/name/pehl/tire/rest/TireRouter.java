package name.pehl.tire.rest;

import name.pehl.taoki.security.SecureRouter;
import name.pehl.tire.rest.activity.ActivitiesResource;
import name.pehl.tire.rest.activity.ActivityResource;

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
        attach("/activities", ActivitiesResource.class);
        attach("/activities/{year}/{monthOrWeek}", ActivitiesResource.class);
        attach("/activities/{year}/{month}/{day}", ActivitiesResource.class);
        attach("/activities/{id}", ActivityResource.class);
        attach("/activities/{id}/start", ActivityResource.class);
        attach("/activities/{id}/stop", ActivityResource.class);
        attach("/activities/{id}/pause", ActivityResource.class);
    }
}
