package name.pehl.tire.server.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.restlet.data.Cookie;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class SecurityInterceptor implements MethodInterceptor
{
    private static final String APPENGINE_COOKIE = "ACSID";


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user == null)
        {
            throw new SecurityException("No user");
        }

        ServerResource resource = (ServerResource) invocation.getThis();
        String token = (String) resource.getRequest().getAttributes().get("token");
        if (token == null || token.length() == 0)
        {
            throw new SecurityException("No security token");
        }

        // Skip check on localhost so we can test in AppEngine local dev env
        String sessionId = findSessionId(resource);
        String serverName = resource.getReference().getHostDomain();
        if (!("localhost".equals(serverName)) && !(token.equals(sessionId)))
        {
            throw new SecurityException("Security token invalid");
        }

        return invocation.proceed();
    }


    private String findSessionId(ServerResource resource)
    {
        String result = null;
        Series<Cookie> cookies = resource.getCookies();
        for (Cookie cookie : cookies)
        {
            if (APPENGINE_COOKIE.equals(cookie.getName()))
            {
                result = cookie.getValue();
                break;
            }
        }
        return result;
    }
}
