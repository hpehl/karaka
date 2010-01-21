package name.pehl.tire.shared.project;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Activity
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Project project;
    @Persistent
    private String name;
    @Persistent
    private String description;
    @Persistent
    private Date start;
    @Persistent
    private Date end;
    @Persistent
    private long pause;


    public Activity(Project project, String name)
    {
        this(project, name, null);
    }


    public Activity(Project project, String name, String description)
    {
        this.project = project;
        this.name = name;
        this.description = description;
        this.start = new Date();
    }


    /**
     * Based on project, name and start
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
        result = prime * result + ((project == null) ? 0 : project.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }


    /**
     * Based on project, name and start
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
        Activity other = (Activity) obj;
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
        if (project == null)
        {
            if (other.project != null)
            {
                return false;
            }
        }
        else if (!project.equals(other.project))
        {
            return false;
        }
        if (start == null)
        {
            if (other.start != null)
            {
                return false;
            }
        }
        else if (!start.equals(other.start))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return "Activity [project=" + project.getName() + ", name=" + name + ", start=" + start + ", end=" + end
                + ", pause=" + pause + "]";
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


    public Date getStart()
    {
        return start;
    }


    public void setStart(Date start)
    {
        this.start = start;
    }


    public Date getEnd()
    {
        return end;
    }


    public void setEnd(Date end)
    {
        this.end = end;
    }


    public long getPause()
    {
        return pause;
    }


    public void setPause(long pause)
    {
        this.pause = pause;
    }


    public Key getKey()
    {
        return key;
    }


    public Project getProject()
    {
        return project;
    }
}
