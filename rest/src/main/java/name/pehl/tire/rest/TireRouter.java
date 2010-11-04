package name.pehl.tire.rest;

import static name.pehl.tire.rest.activity.ActivityParameters.*;
import name.pehl.taoki.security.SecureRouter;
import name.pehl.tire.rest.activity.ActivitiesResource;
import name.pehl.tire.rest.activity.ActivityResource;
import name.pehl.tire.rest.activity.ActivityRouter;
import name.pehl.tire.rest.client.ClientResource;
import name.pehl.tire.rest.client.ClientsResource;

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
        // Unambiguous URIs
        attach("/{TST}/activities", ActivitiesResource.class);
        attach(String.format("/{TST}/activities/{%s}", ID), ActivityResource.class);
        attach(String.format("/{TST}/activities/{%s}/{%s}/{%s}", YEAR, MONTH, DAY), ActivitiesResource.class);
        attach("/{TST}/clients", ClientsResource.class);
        attach("/{TST}/clients/{id}", ClientResource.class);

        // Ambiguous URIs
        ActivityRouter router = new ActivityRouter(getInjector(), getContext());
        attach(String.format("/{TST}/activities/{%s}/{%s}", YEAR_OR_ID, MONTH_OR_WEEK_OR_ACTION), router);
    }
}
