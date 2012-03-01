package name.pehl.tire.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class CurrentUserProvier implements Provider<User>
{
    private final UserService userService;


    @Inject
    public CurrentUserProvier(UserService userService)
    {
        this.userService = userService;
    }


    @Override
    public User get()
    {
        return userService.getCurrentUser();
    }
}
