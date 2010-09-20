package name.pehl.tire.server;

import name.pehl.tire.rest.RestModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
public class ServletConfig extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new ServletModule(), new RestModule());
    }
}
