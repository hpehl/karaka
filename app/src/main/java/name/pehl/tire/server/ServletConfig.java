package name.pehl.tire.server;

import name.pehl.tire.rest.RestModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ServletConfig extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new ServletModule(), new RestModule());
    }
}
