package name.pehl.tire.server.model;

import javax.persistence.Entity;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Entity
public class Project extends DescriptiveEntity implements HasUser
{
    @Parent
    Key<Client> project;

    User user;


    public Project()
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
}
