package name.pehl.tire.server.project;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import com.google.appengine.api.datastore.Key;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Entity
public class Activity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;

    private Project project;
    private String name;
    private String description;
    private Date start;
    private Date end;
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
