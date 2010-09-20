package name.pehl.tire.server;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ServletModule extends com.google.inject.servlet.ServletModule
{
    @Override
    protected void configureServlets()
    {
        serve("/rest/v1/*").with(TireServlet.class);
    }
}
