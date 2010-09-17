package name.pehl.tire.model;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Entity
public class Client extends DescriptiveEntity implements HasUser
{
    User user;


    public Client()
    {
        this(null, null);
    }


    public Client(String name)
    {
        this(name, null);
    }


    public Client(String name, String description)
    {
        super(name, description);
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
}
