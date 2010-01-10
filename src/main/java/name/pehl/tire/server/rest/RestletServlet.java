package name.pehl.tire.server.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.ext.servlet.ServletAdapter;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Singleton
public class RestletServlet extends HttpServlet
{
    @Inject
    private Injector injector;
    private Context context;
    private ServletAdapter adapter;


    @Override
    public void init() throws ServletException
    {
        context = new Context();
        Application application = new Application();
        application.setContext(context);
        application.setInboundRoot(new TireRouter(injector, context));
        adapter = new ServletAdapter(getServletContext());
        adapter.setTarget(application);
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        adapter.service(request, response);
    }
}
