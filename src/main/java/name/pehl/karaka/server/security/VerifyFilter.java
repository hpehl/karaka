package name.pehl.karaka.server.security;

import static com.google.appengine.api.utils.SystemProperty.Environment.Value.Production;
import static com.google.appengine.api.utils.SystemProperty.environment;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.status;
import static name.pehl.karaka.server.security.SecurityToken.TOKEN_NAME;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

/**
 * Global JAX-RS filter to verify security
 *
 * @author $Author: harald.pehl $
 * @version $Revision: 180 $
 */
@Provider
public class VerifyFilter implements ContainerRequestFilter {

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        if (environment.value() == Production) {
            String token = readToken(requestContext);
            String cookie = readCookie(requestContext);
            if (token == null || cookie == null || !token.equals(cookie)) {
                requestContext.abortWith(status(UNAUTHORIZED).build());
            }
        }
    }

    private String readToken(ContainerRequestContext requestContext) {
        return requestContext.getHeaderString(TOKEN_NAME);
    }

    private String readCookie(ContainerRequestContext requestContext) {
        String value = null;
        final Map<String, Cookie> cookies = requestContext.getCookies();
        final Cookie token = cookies.get(TOKEN_NAME);
        if (token != null) {
            value = token.getValue();
        }
        return value;
    }
}
