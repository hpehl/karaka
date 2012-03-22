package name.pehl.tire.server.model;

import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;

/**
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 41 $
 */
@Entity
public class Client extends DescriptiveEntity implements HasUser
{
    private static final long serialVersionUID = -3043154607347333944L;

    private User user;


    Client()
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
}
