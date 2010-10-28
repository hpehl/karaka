package name.pehl.tire.server;

import name.pehl.tire.dao.DaoModule;
import name.pehl.tire.rest.RestModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author $Author$
 * @version $Revision$
 */
public class TireConfig extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new AppEngineModule(), new DaoModule(), new RestModule(), new ServletModule());
    }
}
