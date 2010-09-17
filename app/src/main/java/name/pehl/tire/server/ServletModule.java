package name.pehl.tire.server;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
public class ServletModule extends com.google.inject.servlet.ServletModule
{
    @Override
    protected void configureServlets()
    {
        serve("/rest/v1/*").with(TireServlet.class);
    }
}
