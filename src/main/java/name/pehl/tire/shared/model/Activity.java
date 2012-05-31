package name.pehl.tire.shared.model;

import static name.pehl.tire.shared.model.Status.RUNNING;
import static name.pehl.tire.shared.model.Status.STOPPED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.google.common.collect.ComparisonChain;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Activity extends DescriptiveModel implements Comparable<Activity>
{
    // ------------------------------------------------------- member variables

    Time start;
    Time end;
    long pause;
    long minutes;
    boolean billable;
    Status status;
    Project project;
    List<Tag> tags;


    // ------------------------------------------------------------ constructor

    public Activity()
    {
        this(null, null);
    }


    public Activity(String name)
    {
        this(null, name);
    }


    public Activity(String id, String name)
    {
        super(id, name);
        this.start = new Time();
        this.tags = new ArrayList<Tag>();
    }


    // --------------------------------------------------------- public methods

    /**
     * Creates a new activity which is a copy of this activitiy with the
     * following differences:
     * <ul>
     * <li>Id is <code>null</code> i.e. the copy is a transient activity
     * <li>Start date is the current date/time
     * <li>status is {@link Status#STOPPED}
     * </ul>
     * 
     * @return
     */
    public Activity copy()
    {
        Activity copy = new Activity(this.name);
        copy.setDescription(description);
        copy.setStart(new Time());
        copy.setBillable(this.billable);
        copy.setStatus(Status.STOPPED);
        copy.setProject(this.project);
        copy.getTags().addAll(this.tags);
        return copy;
    }


    /**
     * Creates a copy of this activity and adds the specified amount of
     * milliseconds to both start and end. No matter what the status of this
     * activity is, the copied status will be {@link Status#STOPPED}.
     * 
     * @param millis
     *            The amount of milliseconds to add to this start and end.
     * @return
     */
    public Activity plus(long millis)
    {
        if (this.start == null)
        {
            this.start = new Time();
        }
        if (this.end == null)
        {
            this.end = new Time();
        }

        Activity copy = new Activity(this.name);
        copy.setDescription(description);
        copy.setStart(new Time(new Date(this.start.getDate().getTime() + millis)));
        copy.setEnd(new Time(new Date(this.end.getDate().getTime() + millis)));
        copy.setBillable(this.billable);
        copy.setStatus(STOPPED);
        copy.setProject(this.project);
        copy.getTags().addAll(this.tags);
        return copy;
    }


    /**
     * @return <code>true</code> if the start date of this activity is today,
     *         <code>false</code> otherwise.
     */
    @SuppressWarnings("deprecation")
    public boolean isToday()
    {
        boolean today = false;
        if (start != null)
        {
            Date now = new Date();
            Date startDate = start.getDate();
            if (now.getYear() == startDate.getYear() && now.getMonth() == startDate.getMonth()
                    && now.getDate() == startDate.getDate())
            {
                today = true;
            }
        }
        return today;
    }


    public void start()
    {
        if (start == null)
        {
            start = new Time();
        }
        start.setDate(new Date());
        if (end == null)
        {
            end = new Time();
        }
        end.setDate(new Date());
        status = RUNNING;
    }


    public void resume()
    {
        Date now = new Date();
        if (start == null)
        {
            start = new Time();
        }
        else if (start.after(now))
        {
            start = new Time();
        }
        if (end == null)
        {
            end = new Time();
        }
        else if (end.after(now))
        {
            end = new Time();
        }
        pause += diffInMinutes(end.getDate(), now);
        end.setDate(now);
        status = RUNNING;
        calculateMinutes();
    }


    public void stop()
    {
        if (start == null)
        {
            start = new Time();
        }
        if (end == null)
        {
            end = new Time();
        }
        end.setDate(new Date());
        status = STOPPED;
        calculateMinutes();
    }


    public void tick()
    {
        if (status == RUNNING)
        {
            if (start == null)
            {
                start = new Time();
            }
            if (end == null)
            {
                end = new Time();
            }
            end.setDate(new Date());
            calculateMinutes();
        }
    }


    private void calculateMinutes()
    {
        minutes = diffInMinutes(start.getDate(), end.getDate()) - pause;
    }


    private long diffInMinutes(Date from, Date to)
    {
        long minutes = to.getTime() - from.getTime();
        if (minutes > 0)
        {
            minutes /= 60000;
        }
        return minutes;
    }


    // --------------------------------------------------------- object methods

    @Override
    public int compareTo(Activity that)
    {
        return ComparisonChain.start().compare(that.start, this.start).result();
    }


    @Override
    public String toString()
    {
        return new StringBuilder("Activity [").append(getId()).append(", ").append(getName()).append(", ")
                .append(start).append(", ").append(end).append(", ").append(pause).append(", ").append(status)
                .append("]").toString();
    }


    // ------------------------------------------------------------- properties

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


    public long getMinutes()
    {
        return minutes;
    }


    public void setMinutes(long minutes)
    {
        // TODO Remove public visibility when test code in
        // QuickChartPresenter.onReset() is no longer needed
        this.minutes = minutes;
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


    public boolean isStopped()
    {
        return status == STOPPED;
    }


    public boolean isRunning()
    {
        return status == RUNNING;
    }


    public void setStatus(Status status)
    {
        this.status = status;
    }


    public Project getProject()
    {
        return project;
    }


    public void setProject(Project project)
    {
        this.project = project;
    }


    public List<Tag> getTags()
    {
        return tags;
    }


    public void setTags(List<Tag> tags)
    {
        this.tags = tags;
    }
}
