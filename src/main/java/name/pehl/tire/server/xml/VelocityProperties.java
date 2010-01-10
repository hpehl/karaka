package name.pehl.tire.server.xml;

import java.util.Properties;

import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.JdkLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class VelocityProperties extends Properties
{
    public VelocityProperties()
    {
        super();
        setProperty(RuntimeConstants.VM_LIBRARY, "");
        setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, JdkLogChute.class.getName());
        setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    }
}
