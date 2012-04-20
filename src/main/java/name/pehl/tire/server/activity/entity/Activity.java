package name.pehl.tire.server.activity.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import name.pehl.tire.server.entity.DescriptiveEntity;
import name.pehl.tire.server.project.entity.Project;
import name.pehl.tire.server.tag.entity.Tag;
import name.pehl.tire.shared.model.Status;

import org.joda.time.DateTimeZone;
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

    @Embedded private Time start;

    @Embedded @Unindexed private Time end;

    @Unindexed private String timeZoneId;

    @Transient private DateTimeZone timeZone;

    @Unindexed private int pause;

    @Unindexed private boolean billable;

    @Unindexed(IfStopped.class) private Status status;

    private List<Key<Tag>> tags;

    private Key<Project> project;


    // ----------------------------------------------------------- constructors

    Activity()
    {
        this(null, null, null);
    }


    /**
     * Construct a new instance of this class
     * 
     * @param name
     * @param timeZone
     *            If <code>null</code> the default time zone is used.
     */
    public Activity(String name, DateTimeZone timeZone)
    {
        this(name, null, timeZone);
    }


    /**
     * Construct a new instance of this class
     * 
     * @param name
     * @param description
     * @param timeZone
     *            If <code>null</code> the default time zone is used.
     */
    public Activity(String name, String description, DateTimeZone timeZone)
    {
        super(name, description);
        this.status = Status.STOPPED;
        this.tags = new ArrayList<Key<Tag>>();
        if (timeZone == null)
        {
            this.timeZone = DateTimeZone.getDefault();
        }
        else
        {
            this.timeZone = timeZone;
        }
        this.timeZoneId = this.timeZone.getID();
    }


    // ------------------------------------------------------ lifecycle methods

    @PostLoad
    void restoreDateTimes()
    {
        if (timeZoneId == null)
        {
            timeZone = DateTimeZone.getDefault();
        }
        else
        {
            timeZone = DateTimeZone.forID(timeZoneId);
        }
        if (start != null && start.date != null)
        {
            start.init(start.date, timeZone);
        }
        if (end != null && end.date != null)
        {
            end.init(end.date, timeZone);
        }
    }


    // --------------------------------------------------------- public methods

    public void start()
    {
        status = Status.RUNNING;
        setStart(new Time(new Date(), timeZone));
    }


    public void stop()
    {
        status = Status.STOPPED;
        setEnd(new Time(new Date(), timeZone));
    }


    @Override
    public int compareTo(Activity that)
    {
        return ComparisonChain.start().compare(that.start, this.start).result();
    }


    /**
     * Returns "Activity [&lt;id&gt;, &lt;name&gt;, &lt;start&gt;, &lt;end&gt;,
     * &lt;pause&gt;, &lt;status&gt;]"
     * 
     * @return
     * @see name.pehl.tire.server.entity.NamedEntity#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder().append("Activity [").append(getId()).append(", ").append(getName()).append(", ")
                .append(start).append(", ").append(end).append(", ").append(pause).append(", ").append(status)
                .append("]").toString();
    }


    public int getMinutes()
    {
        int minutes = 0;
        if (start != null && end != null && start.getDateTime().isBefore(end.getDateTime()))
        {
            Minutes m = Minutes.minutesBetween(start.getDateTime(), end.getDateTime());
            minutes = m.getMinutes() - pause;
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


    public int getPause()
    {
        return pause;
    }


    public void setPause(int pause)
    {
        this.pause = pause;
    }


    public List<Key<Tag>> getTags()
    {
        return this.tags;
    }


    public void addTag(Key<Tag> tag)
    {
        if (!this.tags.contains(tag))
        {
            this.tags.add(tag);
        }
    }


    public void removeTag(Key<Tag> tag)
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
