package name.pehl.tire.server;

import name.pehl.taoki.security.SecurityCookieFilter;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 112 $
 */
public class ServletModule extends com.google.inject.servlet.ServletModule
{
    @Override
    protected void configureServlets()
    {
        filter("*").through(SecurityTokenCookieFilter.class);
        serve("/rest/v1/*").with(TireServlet.class);
    }
}
