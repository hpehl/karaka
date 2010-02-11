package name.pehl.tire.server.rest;

import name.pehl.taoki.rest.GuiceRouter;
import name.pehl.taoki.rest.RestletServlet;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-02-04 00:03:03 +0100 (Do, 04 Feb 2010) $ $Revision: 13 $
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
