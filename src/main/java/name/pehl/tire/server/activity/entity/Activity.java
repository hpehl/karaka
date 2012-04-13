package name.pehl.tire.server.activity.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;

import name.pehl.tire.server.base.entity.DescriptiveEntity;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.tag.entity.Tag;
import name.pehl.tire.shared.model.Status;

import org.joda.time.Minutes;

import com.google.common.collect.ComparisonChain;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * Represent an activity of an user. An Activity has a specific state:
 * <ul>
 * <li> {@link Status#RUNNING}
 * <li> {@link Status#PAUSE}
 * <li> {@link Status#STOPPED}
 * </ul>
 * For a given user there must be only one activity with {@link Status#RUNNING}
 * or {@link Status#PAUSE}.
 * 
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
@Entity
public class Activity extends DescriptiveEntity implements Comparable<Activity>
{
    // -------------------------------------------------------- private members

    private static final long serialVersionUID = 3829933815690771262L;

    @Embedded
    private Time start;

    @Embedded
    @Unindexed
    private Time end;

    @Unindexed
    private long pause;

    @Unindexed
    private boolean billable;

    @Unindexed(IfStopped.class)
    private Status status;

    @Embedded
    private final List<Tag> tags;

    private Key<Project> project;


    // ----------------------------------------------------------- constructors

    Activity()
    {
        this(null, null, null);
    }


    public Activity(String name)
    {
        this(name, null, null);
    }


    public Activity(String name, String description)
    {
        this(name, description, null);
    }


    /**
     * Construct a new instance of this class
     * 
     * @param name
     * @param description
     * @param timeZoneId
     *            If <code>null</code> the default time zone is used.
     */
    public Activity(String name, String description, String timeZoneId)
    {
        super(name, description);
        this.start = new Time(timeZoneId);
        this.status = Status.STOPPED;
        this.tags = new ArrayList<Tag>();
    }


    // --------------------------------------------------------- public methods

    @Override
    public int compareTo(Activity that)
    {
        return ComparisonChain.start().compare(that.start, this.start).result();
    }


    /**
     * Returns {@link Class#getSimpleName()} [&lt;id&gt;, &lt;name&gt;,
     * &lt;start&gt;, &lt;end&gt;, &lt;pause&gt;, &lt;status&gt;]
     * 
     * @return
     * @see name.pehl.tire.server.base.entity.NamedEntity#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder(getClass().getSimpleName()).append(" [").append(getId()).append(", ")
                .append(getName()).append(", ").append(start).append(", ").append(end).append(", ").append(pause)
                .append(", ").append(status).append("]").toString();
    }


    public int getMinutes()
    {
        int minutes = 0;
        if (start != null && end != null)
        {
            Minutes m = Minutes.minutesBetween(start.getDateTime(), end.getDateTime());
            minutes = m.getMinutes();
        }
        return minutes;
    }


    public Time getStart()
    {
        return start;
    }


    public void setStart(Time start)
    {
        this.start = start;
    }


    public Time getEnd()
    {
        return end;
    }


    public void setEnd(Time end)
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


    public List<Tag> getTags()
    {
        return this.tags;
    }


    public void addTag(Tag tag)
    {
        if (!this.tags.contains(tag))
        {
            this.tags.add(tag);
        }
    }


    public void removeTag(Tag tag)
    {
        this.tags.remove(tag);
    }


    public void clearTags()
    {
        this.tags.clear();
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
}
