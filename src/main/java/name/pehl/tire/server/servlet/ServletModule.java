package name.pehl.tire.server.servlet;

import name.pehl.tire.server.rest.RestletServlet;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ServletModule extends com.google.inject.servlet.ServletModule
{
    @Override
    protected void configureServlets()
    {
        serve("/rest/v1/*").with(RestletServlet.class);
    }
}
