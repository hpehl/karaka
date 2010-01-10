package name.pehl.tire.server.xml;

import org.apache.velocity.app.VelocityEngine;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class XmlModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(VelocityProperties.class);
        bind(TemplateConverter.class).to(VelocityConverter.class);
    }


    @Provides
    @Singleton
    VelocityEngine provideVelocityEngine(VelocityProperties properties)
    {
        try
        {
            VelocityEngine velocityEngine = new VelocityEngine(properties);
            return velocityEngine;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
