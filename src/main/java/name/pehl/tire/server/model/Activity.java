package name.pehl.tire.server.model;

import java.util.Date;

import javax.persistence.Entity;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

/**
 * @author $Author:$
 * @version $Revision:$
 */
@Entity
public class Activity extends DescriptiveEntity
{
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


    public Activity(String name)
    {
        this(name, null);
    }


    public Activity(String name, String description)
    {
        super(name, description);
        this.start = new Date();
    }


    /**
     * Returns {@link #simpleClassname()} [&lt;id&gt;, &lt;name&gt;,
     * &lt;start&gt;, &lt;end&gt;, &lt;pause&gt;]
     * 
     * @return
     * @see name.pehl.tire.shared.model.NamedEntity#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(simpleClassname()).append(" [").append(id).append(", ").append(name).append(", ")
                .append(start).append(", ").append(end).append(", ").append(pause).append("]").toString();
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
