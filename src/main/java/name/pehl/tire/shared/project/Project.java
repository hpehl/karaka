package name.pehl.tire.shared.project;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Entity
public class Project
{
    @Id
    Long id;
    
    String name;
    
    @Unindexed
    String description;
    
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
        this.name = name;
        this.description = description;
    }


    /**
     * Based on name and user
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }


    /**
     * Base on name and user
     * 
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Project other = (Project) obj;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        if (user == null)
        {
            if (other.user != null)
            {
                return false;
            }
        }
        else if (!user.equals(other.user))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return "Project [" + name + "]";
    }


    public Long getId()
    {
        return id;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public void attachToUser(User user)
    {
        this.user = user;
    }
}
