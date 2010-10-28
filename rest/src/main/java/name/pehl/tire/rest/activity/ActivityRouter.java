package name.pehl.tire.rest.activity;

import java.util.Map;
import java.util.logging.Level;

import name.pehl.taoki.security.SecureRouter;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;

import com.google.inject.Injector;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivityRouter extends SecureRouter
{
    public ActivityRouter(Injector injector, Context context)
    {
        super(injector, context);
    }


    @Override
    protected void attachRoutes()
    {
        // nop
    }


    /**
     * Depending on the template parameter in the URI this router delegates to
     * either {@link ActivityResource} or {@link ActivitiesResource}.
     * <ul>
     * <li>{@link ActivityResource}: if ...
     * <li>{@link ActivitiesResource}: if ...
     * </ul>
     * 
     * @param request
     * @param response
     * @return
     * @see org.restlet.routing.Router#getNext(org.restlet.Request,
     *      org.restlet.Response)
     */
    @Override
    public Restlet getNext(Request request, Response response)
    {
        Map<String, Object> attributes = request.getAttributes();
        try
        {
            ActivityParameters parameters = new ActivityParameters().parse(attributes);
            if (parameters.hasYear() && (parameters.hasMonth() || parameters.hasWeek()))
            {
                return createFinder(ActivitiesResource.class);
            }
            else if (parameters.hasId() && parameters.hasAction())
            {
                return createFinder(ActivityResource.class);
            }
            else
            {
                getLogger().log(Level.SEVERE,
                        String.format("Cannot find resource for template parameters %s", attributes));
                return null;
            }
        }
        catch (IllegalArgumentException e)
        {
            getLogger().log(Level.SEVERE,
                    String.format("Cannot find resource for template parameters %s: %s", attributes, e.getMessage()));
            return null;
        }
    }
}
