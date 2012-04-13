package name.pehl.tire.server.security;

import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.SecurityPrecedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 180 $
 */
@Provider
@ServerInterceptor
@SecurityPrecedence
public class SecurityTokenInterceptor implements PreProcessInterceptor, SecurityToken
{
    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure,
            WebApplicationException
    {
        String token = readToken(request);
        String cookie = readCookie(request);
        if (!(token.equals(cookie)))
        {
            throw new UnauthorizedException("Invalid security token");
        }
        return null;
    }


    private String readToken(HttpRequest request)
    {
        String token = null;
        List<String> tokenHeader = request.getHttpHeaders().getRequestHeader(TOKEN_NAME);
        if (tokenHeader != null && !tokenHeader.isEmpty())
        {
            token = tokenHeader.get(0);
        }
        if (token == null)
        {
            throw new UnauthorizedException("No security token found in " + request.getUri());
        }
        return token;
    }


    private String readCookie(HttpRequest request)
    {
        String value = null;
        Map<String, Cookie> cookies = request.getHttpHeaders().getCookies();
        if (cookies != null && !cookies.isEmpty())
        {
            Cookie cookie = cookies.get(TOKEN_NAME);
            if (cookie != null)
            {
                value = cookie.getValue();
            }
        }
        return value;
    }

}
