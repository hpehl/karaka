package name.pehl.tire.server;

import name.pehl.taoki.security.SecurityCookieFilter;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ServletModule extends com.google.inject.servlet.ServletModule
{
    @Override
    protected void configureServlets()
    {
        filter("*").through(SecurityCookieFilter.class);
        serve("/rest/v1/*").with(TireServlet.class);
    }
}
