package name.pehl.tire.shared.model;

import com.google.appengine.api.users.User;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface HasUser
{
    User getUser();


    void attachToUser(User user);
}
