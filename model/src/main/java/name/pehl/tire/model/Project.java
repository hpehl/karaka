package name.pehl.tire.model;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
@Entity
public class Project extends DescriptiveEntity implements HasUser
{
    private User user;
    private Key<Client> client;


    Project()
    {
        this(null, null);
    }


    public Project(String name)
    {
        this(name, null);
    }


    public Project(String name, String description)
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


    /**
     * @return the client.
     */
    public Key<Client> getClient()
    {
        return client;
    }
}
