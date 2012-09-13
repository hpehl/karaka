package name.pehl.tire.server.cache;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.cache.server.ServerCache;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class ServletServerCache implements ServletContextListener
{
    protected ResteasyProviderFactory providerFactory;
    protected AppEngineCache cache = new AppEngineCache();


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent)
    {
        ServletContext servletContext = servletContextEvent.getServletContext();
        providerFactory = (ResteasyProviderFactory) servletContext
                .getAttribute(ResteasyProviderFactory.class.getName());
        if (providerFactory == null)
        {
            throw new RuntimeException("Resteasy is not intialized, could not find ResteasyProviderFactory attribute");
        }

        String maxSize = servletContext.getInitParameter("tire.server.cache.maxsize");
        if (maxSize != null)
        {
            cache.setMaxSize(Integer.parseInt(maxSize));
        }

        String expiration = servletContext.getInitParameter("tire.server.cache.expiration");
        if (expiration != null)
        {
            cache.setExpiration(Integer.parseInt(expiration));
        }
        cache.setProviderFactory(providerFactory);

        cache.start();
        Dispatcher dispatcher = (Dispatcher) servletContext.getAttribute(Dispatcher.class.getName());
        dispatcher.getDefaultContextObjects().put(ServerCache.class, cache);
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent)
    {
    }
}
