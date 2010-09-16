package name.pehl.tire.server.model;

import javax.persistence.Entity;

import com.google.appengine.api.users.User;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
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
