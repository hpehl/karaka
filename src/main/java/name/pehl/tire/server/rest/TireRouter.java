package name.pehl.tire.server.rest;

import static name.pehl.tire.server.rest.activity.ActivityParameters.*;
import name.pehl.taoki.security.SecureRouter;
import name.pehl.tire.server.rest.activity.ActivitiesResource;
import name.pehl.tire.server.rest.activity.SingleParamActivityRouter;
import name.pehl.tire.server.rest.activity.TwoParamActivityRouter;
import name.pehl.tire.server.rest.client.ClientResource;
import name.pehl.tire.server.rest.client.ClientsResource;

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
        setRoutingMode(MODE_LAST_MATCH);
    }


    @Override
    protected void attachRoutes()
    {
        // Unambiguous URIs
        attach("/activities", ActivitiesResource.class);
        attach(String.format("/activities/{%s}/{%s}/{%s}", YEAR, MONTH, DAY), ActivitiesResource.class);
        attach("/clients", ClientsResource.class);
        attach("/clients/{id}", ClientResource.class);

        // Ambiguous URIs
        SingleParamActivityRouter singleParamRouter = new SingleParamActivityRouter(getInjector(), getContext());
        attach(String.format("/activities/{%s}", ID_OR_CURRENT_MONTH_OR_CURRENT_WEEK_OR_TODAY), singleParamRouter);

        TwoParamActivityRouter twoParamRouter = new TwoParamActivityRouter(getInjector(), getContext());
        attach(String.format("/activities/{%s}/{%s}", YEAR_OR_RELATIVE_OR_ID, MONTH_OR_WEEK_OR_ACTION), twoParamRouter);
    }
}
