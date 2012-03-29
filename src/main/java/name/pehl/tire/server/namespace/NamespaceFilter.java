package name.pehl.tire.server.namespace;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class NamespaceFilter implements Filter
{
    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
    }


    @Override
    public void destroy()
    {
    }


    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
            ServletException
    {
        if (NamespaceManager.get() == null)
        {
            User currentUser = UserServiceFactory.getUserService().getCurrentUser();
            if (currentUser == null)
            {
                NamespaceManager.set(NamespaceManager.getGoogleAppsNamespace());
            }
            else
            {
                NamespaceManager.set(currentUser.getUserId());
            }
        }
    }
}
