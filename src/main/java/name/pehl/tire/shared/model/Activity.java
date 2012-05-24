package name.pehl.tire.shared.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.google.common.collect.ComparisonChain;

import static name.pehl.tire.shared.model.Status.RUNNING;
import static name.pehl.tire.shared.model.Status.STOPPED;

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
        copy.setStart(new Time());
        copy.setBillable(this.billable);
        copy.setStatus(Status.STOPPED);
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
        if (start == null)
        {
            start = new Time();
        }
        if (end == null)
        {
            end = new Time();
        }
        Date now = new Date();
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


    /**
     * Based on start and end.
     * 
     * @return
     * @see name.pehl.tire.shared.model.BaseModel#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (start == null ? 0 : start.hashCode());
        result = prime * result + (end == null ? 0 : end.hashCode());
        return result;
    }


    /**
     * Based on start and end.
     * 
     * @param obj
     * @return
     * @see name.pehl.tire.shared.model.BaseModel#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!super.equals(obj))
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Activity other = (Activity) obj;
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
        if (end == null)
        {
            if (other.end != null)
            {
                return false;
            }
        }
        else if (!end.equals(other.end))
        {
            return false;
        }
        return true;
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
