package name.pehl.karaka.server.activity.entity;

import com.google.common.base.Strings;
import com.google.common.collect.ComparisonChain;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;
import name.pehl.karaka.server.entity.DescriptiveEntity;
import name.pehl.karaka.server.project.entity.Project;
import name.pehl.karaka.server.tag.entity.Tag;
import name.pehl.karaka.shared.model.Status;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.Period;

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
            start.init(new DateTime(start.date, timeZone));
        }
        if (end != null && end.date != null)
        {
            end.init(new DateTime(end.date, timeZone));
        }
    }


    // --------------------------------------------------------- business methods

    /**
     * Creates a new activity which is a copy of this activitiy with the
     * following differences:
     * <ul>
     * <li>Id is <code>null</code> i.e. the copy is a transient activity
     * <li>Start and end is the current time.
     * <li>pause and duration are 0.
     * <li>status is {@link Status#STOPPED}
     * </ul>
     *
     * @return
     */
    public Activity copy()
    {
        Activity copy = new Activity(getName(), getDescription(), timeZone);
        copy.setProject(project);
        copy.setTags(tags);
        return copy;
    }

    /**
     * Copies the current activity and adds the specified period to the start and end date.
     * The period must follow <a href="http://en.wikipedia.org/wiki/ISO_8601#Durations">ISO8601</a>.
     *
     * @param period
     *
     * @return
     *
     * @throws IllegalArgumentException if the duration is null, empty or
     */
    public Activity copy(final String period)
    {
        if (Strings.emptyToNull(period) == null)
        {
            throw new IllegalArgumentException("Period must not be null");
        }
        Period p = Period.parse(period);
        Activity copy = copy();
        copy.setEnd(new Time(end.dateTime.withPeriodAdded(p, 1)));
        copy.setStart(new Time(start.dateTime.withPeriodAdded(p, 1)));
        return copy;
    }

    public void start()
    {
        start = now();
        end = now();
        status = RUNNING;
    }

    public void resume()
    {
        if (start.isAfterNow())
        {
            start = now();
        }
        if (end.isAfterNow())
        {
            end = now();
        }
        pause += minutesBetween(end, now()).getMinutes();
        end = now();
        status = RUNNING;
    }

    public void tick()
    {
        if (status == RUNNING)
        {
            end = now();
        }
    }

    public void stop()
    {
        end = now();
        status = STOPPED;
    }

    private Time now()
    {
        return new Time();
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

    public boolean isToday()
    {
        DateMidnight now = new DateMidnight(timeZone);
        return now.equals(start.toDateMidnight());
    }

    public long getDuration()
    {
        Minutes m = minutesBetween(start, end);
        long minutes = m.getMinutes() - pause;
        return max(0, minutes);
    }

    public Time getStart()
    {
        return start;
    }

    public void setStart(final Time start)
    {
        if (start != null)
        {
            if (start.isAfter(end))
            {
                this.start = new Time(end);
                this.pause = 0;
            }
            else
            {
                this.start = start;
            }
        }
    }

    public Time getEnd()
    {
        return end;
    }

    public void setEnd(final Time end)
    {
        if (end != null)
        {
            if (end.isBefore(start))
            {
                this.end = new Time(start);
                this.pause = 0;
            }
            else
            {
                this.end = end;
            }
        }
    }

    public long getPause()
    {
        return pause;
    }

    public void setPause(final long pause)
    {
        if (pause >= 0)
        {
            long duration = minutesBetween(start, end).getMinutes();
            if (pause <= duration)
            {
                this.pause = pause;
            }
        }
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
