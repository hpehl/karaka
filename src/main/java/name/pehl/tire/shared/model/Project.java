package name.pehl.tire.shared.model;

import javax.persistence.Entity;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Entity
public class Project extends NamedEntity implements HasUser
{
    @Unindexed
    String description;

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
