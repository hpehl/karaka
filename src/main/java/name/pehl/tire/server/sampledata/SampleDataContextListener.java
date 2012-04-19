package name.pehl.tire.server.sampledata;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static com.google.appengine.api.utils.SystemProperty.environment;
import static com.google.appengine.api.utils.SystemProperty.Environment.Value.Development;

public class SampleDataContextListener implements ServletContextListener
{
    @Inject
    SampleData sampleData;


    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        if (environment.value() == Development)
        {
            sampleData.generateAndPersit();
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        // nop
    }
}
