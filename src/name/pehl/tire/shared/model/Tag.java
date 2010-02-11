package name.pehl.tire.shared.model;

import javax.persistence.Entity;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@Entity
public class Tag extends NamedEntity implements HasUser
{
    User user;
    Key<Activity> activity;


    public Tag()
    {
        this(null);
    }


    public Tag(String name)
    {
        super(name);
    }


    @Override
    public User getUser()
    {
        return user;
    }


    @Override
    public void attachToUser(User user)
    {
        this.user = user;
    }


    public Key<Activity> getActivity()
    {
        return activity;
    }


    public void setActivity(Key<Activity> activity)
    {
        this.activity = activity;
    }
}
