package name.pehl.tire.server.servlet;

import name.pehl.tire.server.rest.TireRestletServlet;

/**
 * @author $Author:$
 * @version $Revision:$
 */
public class ServletModule extends com.google.inject.servlet.ServletModule
{
    @Override
    protected void configureServlets()
    {
        serve("/rest/v1/*").with(TireRestletServlet.class);
    }
}
