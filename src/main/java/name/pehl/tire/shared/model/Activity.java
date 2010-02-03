package name.pehl.tire.shared.model;

import java.util.Date;

import javax.persistence.Entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Entity
public class Activity extends NamedEntity
{
    @Unindexed
    String description;

    Date start;

    Date end;

    long pause;

    boolean billable;

    @Parent
    Key<Project> project;


    public Activity()
    {
        this(null, null);
    }


    public Activity(String name, String description)
    {
        super(name);
        this.description = description;
        this.start = new Date();
    }


    @Override
    public String toString()
    {
        return simpleClassname() + " [name=" + name + ", start=" + start + ", end=" + end + ", pause=" + pause + "]";
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


    public Key<Project> getProject()
    {
        return project;
    }


    public void setProject(Key<Project> project)
    {
        this.project = project;
    }
}
