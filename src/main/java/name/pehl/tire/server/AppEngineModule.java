package name.pehl.tire.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-02 13:30:00 +0100 (Do, 02. Dez 2010) $ $Revision: 120
 *          $
 */
public class AppEngineModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(User.class).toProvider(CurrentUserProvier.class);
    }


    @Provides
    UserService providesUserService()
    {
        return UserServiceFactory.getUserService();
    }
}
