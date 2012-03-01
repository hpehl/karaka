package name.pehl.tire.server;

import name.pehl.taoki.GuiceRouter;
import name.pehl.taoki.RestletServlet;
import name.pehl.tire.rest.TireRouter;

import org.restlet.Context;

import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-08-31 22:04:53 +0200 (Mi, 31. Aug 2011) $ $Revision: 41
 *          $
 */
@Singleton
public class TireServlet extends RestletServlet
{
    private static final long serialVersionUID = 6736699669978670928L;


    @Override
    protected GuiceRouter createRouter(Injector injector, Context context)
    {
        return new TireRouter(injector, context);
    }
}
