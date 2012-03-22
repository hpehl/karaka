package name.pehl.tire.server.model;

import javax.xml.bind.annotation.XmlTransient;

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
    private static final long serialVersionUID = -8354180510050961764L;

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
    @XmlTransient
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
