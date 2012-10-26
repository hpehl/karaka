package name.pehl.tire.server.rest;

import name.pehl.taoki.rest.GuiceRouter;
import name.pehl.taoki.rest.RestletServlet;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author $Author$
 * @version $Date$ $Revision$
 */
@Singleton
public class TireRestletServlet extends RestletServlet
{
    /**
     * @param injector
     * @param context
     * @return
     * @see name.pehl.taoki.rest.RestletServlet#createRouter(com.google.inject.Injector,
     *      org.restlet.Context)
     */
    @Override
    protected GuiceRouter createRouter(Injector injector, Context context)
    {
        return new TireRouter(injector, context);
    }
}
