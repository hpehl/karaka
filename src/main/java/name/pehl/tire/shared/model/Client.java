package name.pehl.tire.shared.model;

import javax.persistence.Entity;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@Entity
public class Client extends NamedEntity implements HasUser
{
    @Unindexed
    String description;

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
        super(name);
        this.description = description;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
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
