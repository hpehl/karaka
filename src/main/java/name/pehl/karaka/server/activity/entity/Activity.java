package name.pehl.karaka.server.activity.entity;

import com.google.common.collect.ComparisonChain;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import name.pehl.karaka.server.entity.DescriptiveEntity;
import name.pehl.karaka.server.project.entity.Project;
import name.pehl.karaka.server.tag.entity.Tag;
import name.pehl.karaka.shared.model.Status;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;

import javax.persistence.Embedded;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.util.Collections.unmodifiableList;
import static name.pehl.karaka.shared.model.Status.RUNNING;
import static name.pehl.karaka.shared.model.Status.STOPPED;
import static org.joda.time.Minutes.minutesBetween;

/**
 * Represent an activity of an user. An Activity has a specific state:
 * <ul>
 * <li> {@link Status#RUNNING}
 * <li> {@link Status#STOPPED}
 * </ul>
 * For a given user there must be only one activity with {@link Status#RUNNING}.
 *
 * @author $Author: harald.pehl $
 * @version $Revision: 41 $
 */
@Entity
public class Activity extends DescriptiveEntity implements Comparable<Activity>
{
    // ------------------------------------------------------ members

    private static final long serialVersionUID = 3829933815690771262L;

    @Unindexed private final String timeZoneId;
    @Embedded private Time start;
    @Embedded @Unindexed private Time end;
    @Transient private DateTimeZone timeZone;
    @Unindexed private long pause;
    @Unindexed private boolean billable;
    @Indexed(IfRunning.class) private Status status;
    private List<Key<Tag>> tags;
    private Key<Project> project;


    // ------------------------------------------------------ constructors

    Activity()
    {
        this(null, null, null);
    }


    /**
     * Construct a new instance of this class
     *
     * @param name
     * @param timeZone If <code>null</code> the default time zone is used.
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
     * @param timeZone    If <code>null</code> the default time zone is used.
     */
    public Activity(String name, String description, DateTimeZone timeZone)
    {
        super(name, description);
        this.pause = 0;
        this.billable = false;
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
        this.start = now();
        this.end = now();
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


    // --------------------------------------------------------- business methods

    public Activity start()
    {
        ensureStart();
        ensureEnd();
        status = RUNNING;
        return this;
    }

    public Activity resume()
    {
        ensureStart();
        ensureEnd();
        DateTime now = new DateTime(timeZone);
        if (start.isAfterNow())
        {
            start = new Time(null, timeZone);
        }
        if (end.isAfterNow())
        {
            end = new Time(null, timeZone);
        }
        pause += minutesBetween(end, now).getMinutes();
        end = now();
        status = RUNNING;
        return this;
    }

    public Activity tick()
    {
        return tick(now());
    }

    public Activity tick(Time to)
    {
        if (status == RUNNING)
        {
            ensureStart();
            end = to;
        }
        return this;
    }

    public Activity stop()
    {
        ensureStart();
        end = now();
        status = STOPPED;
        return this;
    }

    private void ensureStart()
    {
        if (this.start == null)
        {
            this.start = now();
        }
    }

    private void ensureEnd()
    {
        if (this.end == null)
        {
            this.end = now();
        }
    }

    private Time now()
    {
        return new Time(null, timeZone);
    }


    // ------------------------------------------------------ object methods

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
     *
     * @see name.pehl.karaka.server.entity.NamedEntity#toString()
     */
    @Override
    public String toString()
    {
        return new StringBuilder().append("Activity [").append(getId()).append(", ").append(getName()).append(", ")
                .append(start).append(", ").append(end).append(", ").append(pause).append(", ").append(status)
                .append("]").toString();
    }


    // ------------------------------------------------------ properties

    public long getMinutes()
    {
        Minutes m = minutesBetween(start, end);
        long minutes = m.getMinutes() - pause;
        return max(0, minutes);
    }

    public Time getStart()
    {
        return start;
    }

    public Time getEnd()
    {
        return end;
    }

    public long getPause()
    {
        return pause;
    }

    public List<Key<Tag>> getTags()
    {
        return unmodifiableList(this.tags);
    }

    public void setTags(List<Key<Tag>> tags)
    {
        if (tags != null)
        {
            this.tags.clear();
            this.tags.addAll(tags);
        }
    }

    public void addTag(Key<Tag> key)
    {
        this.tags.add(key);
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
}
