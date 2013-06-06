package name.pehl.karaka.server.version;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.ArrayUtils;

@Provider
public class VersionCheckInterceptor implements ContainerRequestFilter {

    public static final String ALLOW_ALL = "ALLOW_ALL";
    private static final Pattern pattern = Pattern.compile("ver\\s*=\\s*([\\d]+.[\\d]+)");
    @Inject ResourceInfo resourceInfo;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {

        Versioned methodAnnotation = resourceInfo.getResourceMethod().getAnnotation(Versioned.class);
        if (methodAnnotation != null && methodAnnotation.versions() != null
                && Arrays.binarySearch(methodAnnotation.versions(), ALLOW_ALL) < 0) {
            return;
        }

        Versioned classAnnotation = resourceInfo.getResourceClass().getAnnotation(Versioned.class);
        Versioned packageAnnotation = resourceInfo.getResourceClass().getPackage().getAnnotation(Versioned.class);
        Versioned annotation = methodAnnotation != null ? methodAnnotation : (classAnnotation != null ? classAnnotation
                : packageAnnotation);
        if (annotation == null) {
            return;
        }

        List<String> acceptHeaders = requestContext.getHeaders().get("Accept");
        boolean reqContainsSupportedVersion = false;
        for (String version : acceptHeaders) {
            Matcher matcher = pattern.matcher(version);
            if (!matcher.find()) {
                continue;
            }

            String versNum = matcher.group(1);
            if (versNum == null) {
                continue;
            }

            if (ArrayUtils.contains(annotation.versions(), versNum)) {
                reqContainsSupportedVersion = true;
                break;
            }
        }

        if (!reqContainsSupportedVersion) {
            final String message =
                    String.format("No supported version found for %s in %s. Supported versions are: %s.",
                            requestContext.getUriInfo().getRequestUri(), acceptHeaders, annotation.versions());
            requestContext.abortWith(Response.status(UNAUTHORIZED).entity(message).build());
        }
    }
}
