package name.pehl.tire.server;

import name.pehl.taoki.GuiceRouter;
import name.pehl.taoki.RestletServlet;
import name.pehl.tire.rest.TireRouter;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-16 16:22:23 +0200 (Do, 16. Sep 2010) $ $Revision: 41
 *          $
 */
@Singleton
public class TireServlet extends RestletServlet
{
    @Override
    protected GuiceRouter createRouter(Injector injector, Context context)
    {
        return new TireRouter(injector, context);
    }
}
