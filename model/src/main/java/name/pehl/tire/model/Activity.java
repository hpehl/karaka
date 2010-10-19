package name.pehl.tire.model;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

/**
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
@Entity
public class Activity extends DescriptiveEntity
{
    private Date start;
    private Date end;
    private long pause;
    private boolean billable;
    private Status status;
    @Parent
    private Key<Project> project;


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
        this.status = Status.STOPPED;
    }


    /**
     * Returns {@link Class#getSimpleName()} [&lt;id&gt;, &lt;name&gt;,
     * &lt;start&gt;, &lt;end&gt;, &lt;pause&gt;, &lt;status&gt;]
     * 
     * @return
     * @see name.pehl.tire.shared.model.NamedEntity#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(getClass().getSimpleName()).append(" [").append(id).append(", ").append(name)
                .append(", ").append(start).append(", ").append(end).append(", ").append(pause).append(", ")
                .append(status).append("]").toString();
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


    public boolean isBillable()
    {
        return billable;
    }


    public void setBillable(boolean billable)
    {
        this.billable = billable;
    }


    public Status getStatus()
    {
        return status;
    }


    public void setStatus(Status status)
    {
        this.status = status;
    }

    public static enum Status
    {
        STOPPED,
        RUNNING,
        PAUSE;
    }
}
