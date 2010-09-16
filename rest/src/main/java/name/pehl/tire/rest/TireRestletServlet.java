package name.pehl.tire.server.rest;

import name.pehl.taoki.GuiceRouter;
import name.pehl.taoki.RestletServlet;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-16 16:22:23 +0200 (Do, 16. Sep 2010) $ $Revision: 41 $
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
