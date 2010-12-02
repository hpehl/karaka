package name.pehl.tire.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author $Author$
 * @version $Date$ $Revision: 120
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
