package name.pehl.tire.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.servlet.SessionScoped;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class AppEngineModule extends AbstractModule
{
    @Override
    protected void configure()
    {
    }


    @Provides
    UserService providesUserService()
    {
        return UserServiceFactory.getUserService();
    }


    @Inject
    @Provides
    @SessionScoped
    User providesCurrentUser(UserService userService)
    {
        return userService.getCurrentUser();
    }
}
