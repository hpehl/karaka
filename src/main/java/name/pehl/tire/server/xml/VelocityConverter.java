package name.pehl.tire.server.xml;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.google.inject.Inject;
import com.google.inject.internal.Nullable;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class VelocityConverter implements TemplateConverter
{
    private final Logger logger;
    private final VelocityEngine velocityEngine;


    @Inject
    public VelocityConverter(VelocityEngine velocityEngine, Logger logger)
    {
        this.velocityEngine = velocityEngine;
        this.logger = logger;
    }


    @Override
    public String convert(String template, @Nullable Context context)
    {
        try
        {
            VelocityContext velocityContext;
            StringWriter writer = new StringWriter();
            if (context == null)
            {
                velocityContext = new VelocityContext();
            }
            else
            {
                velocityContext = new VelocityContext(context.asMap());
            }
            velocityEngine.mergeTemplate(template, "UTF-8", velocityContext, writer);
            return writer.toString();
        }
        catch (ResourceNotFoundException e)
        {
            String errorMessage = "Cannot find template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
        catch (ParseErrorException e)
        {
            String errorMessage = "Cannot parse template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
        catch (MethodInvocationException e)
        {
            String errorMessage = "Error when executing methods in template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
        catch (Exception e)
        {
            String errorMessage = "Unexpected error in template '" + template + "': " + e.getMessage();
            logger.log(Level.SEVERE, errorMessage, e);
            throw new ConverterException(errorMessage, e);
        }
    }
}
