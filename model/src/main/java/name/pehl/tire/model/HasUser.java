package name.pehl.tire.model;

import com.google.appengine.api.users.User;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
public interface HasUser
{
    User getUser();


    void attachToUser(User user);
}
