package name.pehl.tire.server.rest.activity;

import java.util.Map;
import java.util.logging.Level;

import name.pehl.taoki.security.SecureRouter;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;

import com.google.inject.Injector;

/**
 * Router which routes the following URLs to the following resources:
 * <ul>
 * <li>TODO Javadoc
 * </ul>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-06 17:48:50 +0100 (Mo, 06. Dez 2010) $ $Revision: 123
 *          $
 */
public class SingleParamActivityRouter extends SecureRouter
{
    public SingleParamActivityRouter(Injector injector, Context context)
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
            ActivityParameters ap = new ActivityParameters().parse(attributes);
            if (ap.hasId())
            {
                return createFinder(ActivityResource.class);
            }
            else if (ap.isCurrentMonth() || ap.isCurrentWeek() || ap.isToday())
            {
                return createFinder(ActivitiesResource.class);
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