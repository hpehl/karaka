package name.pehl.karaka.server.sampledata;

import static com.google.appengine.api.utils.SystemProperty.environment;
import static com.google.appengine.api.utils.SystemProperty.Environment.Value.Development;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.weld.environment.servlet.Listener;

public class SampleDataContextListener implements ServletContextListener
{
    @Override
    @SuppressWarnings("unchecked")
    public void contextInitialized(ServletContextEvent event)
    {
        if (environment.value() == Development)
        {
            ServletContext servletContext = event.getServletContext();
            BeanManager beanManager = (BeanManager) servletContext.getAttribute(Listener.BEAN_MANAGER_ATTRIBUTE_NAME);
            if (beanManager != null)
            {
                Bean<SampleData> bean = (Bean<SampleData>) beanManager.getBeans(SampleData.class).iterator().next();
                CreationalContext<SampleData> context = beanManager.createCreationalContext(bean);
                SampleData sampleData = (SampleData) beanManager.getReference(bean, SampleData.class, context);
                sampleData.cleanup();
                sampleData.persit();
            }
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        // nop
    }
}
