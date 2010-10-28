package name.pehl.tire.dao;

import name.pehl.tire.model.Activity;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivityDao extends HasUserDao<Activity>
{
    @Inject
    public ActivityDao(User user)
    {
        super(Activity.class, user);
    }
}
