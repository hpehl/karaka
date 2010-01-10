package name.pehl.tire.server.servlet;

import name.pehl.tire.server.persistence.PersistenceModule;
import name.pehl.tire.server.rest.RestModule;
import name.pehl.tire.server.security.SecurityModule;
import name.pehl.tire.server.xml.XmlModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ServletConfig extends GuiceServletContextListener
{
    @Override
    protected Injector getInjector()
    {
        return Guice.createInjector(new ServletModule(), new SecurityModule(), new RestModule(), new XmlModule(),
                new PersistenceModule());
    }
}
