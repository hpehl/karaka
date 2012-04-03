package name.pehl.tire.server.rest.version;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.ArrayUtils;
import org.jboss.resteasy.annotations.interception.HeaderDecoratorPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

@Provider
@ServerInterceptor
@HeaderDecoratorPrecedence
public class VersionCheckInterceptor implements PreProcessInterceptor
{
    public static final String ALLOW_ALL = "ALLOW_ALL";
    private static final Pattern pattern = Pattern.compile("ver\\s*=\\s*([\\d]+.[\\d]+)");


    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethod resourceMethod) throws Failure,
            WebApplicationException
    {
        Versioned methodAnnotation = resourceMethod.getMethod().getAnnotation(Versioned.class);
        if (methodAnnotation != null && methodAnnotation.versions() != null
                && ArrayUtils.contains(methodAnnotation.versions(), ALLOW_ALL))
        {
            return null;
        }

        Versioned classAnnotation = resourceMethod.getResourceClass().getAnnotation(Versioned.class);
        Versioned packageAnnotation = resourceMethod.getResourceClass().getPackage().getAnnotation(Versioned.class);
        Versioned annotation = methodAnnotation != null ? methodAnnotation : (classAnnotation != null ? classAnnotation
                : packageAnnotation);
        if (annotation == null)
        {
            return null;
        }

        List<String> acceptHeaders = request.getHttpHeaders().getRequestHeader("Accept");
        boolean reqContainsSupportedVersion = false;
        for (String version : acceptHeaders)
        {
            Matcher matcher = pattern.matcher(version);
            if (!matcher.find())
            {
                continue;
            }

            String versNum = matcher.group(1);
            if (versNum == null)
            {
                continue;
            }

            if (ArrayUtils.contains(annotation.versions(), versNum))
            {
                reqContainsSupportedVersion = true;
                break;
            }
        }

        if (!reqContainsSupportedVersion)
        {

            throw new NotFoundException("No supported version found for " + acceptHeaders + " in " + request.getUri()
                    + ". Supported versions are: " + annotation.versions() + ".");
        }
        return null;
    }
}
