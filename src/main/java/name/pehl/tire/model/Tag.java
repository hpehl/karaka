package name.pehl.tire.model;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Entity
public class Tag extends NamedEntity implements HasUser
{
    private static final long serialVersionUID = -3947128324431639651L;

    private User user;
    private Key<Activity> activity;


    Tag()
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
