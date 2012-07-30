package name.pehl.tire.shared.model;

import static name.pehl.tire.shared.model.Status.RUNNING;
import static name.pehl.tire.shared.model.Status.STOPPED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * <h3>Design by contract</h3>
 * <ul>
 * <li>Invariants:
 * <ul>
 * <li> {@link #getPause()} is never <code>null</code>. An undefined / empty
 * pause value must be expressed using {@link Duration#EMPTY}. Calling
 * {@link #setPause(Duration)} with <code>null</code> will throw an
 * {@link IllegalArgumentException}.
 * <li> {@link #getMinutes()} is never <code>null</code>. An undefined / empty
 * minutes value must be expressed using {@link Duration#EMPTY}. Calling
 * {@link #setMinutes(Duration)} with <code>null</code> will throw an
 * {@link IllegalArgumentException}.
 * <li>{@link #getStart()} is never <code>null</code>. Calling
 * {@link #setStart(Time)} with <code>null</code> will throw an
 * {@link IllegalArgumentException}.
 * </ul>
 * </ul>
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Activity extends DescriptiveModel
{
    // ------------------------------------------------------- member variables

    Time start;
    Time end;
    Duration pause;
    Duration minutes;
    boolean billable;
    Status status;
    Project project;
    List<Tag> tags;


    // ------------------------------------------------------------ constructor

    public Activity()
    {
        this(newId(), null);
    }


    public Activity(String name)
    {
        this(newId(), name);
    }


    public Activity(String id, String name)
    {
        super(id, name);
        this.pause = Duration.EMPTY;
        this.minutes = Duration.EMPTY;
        this.status = STOPPED;
        this.tags = new ArrayList<Tag>();
        ensureStart();
    }


    // ------------------------------------------------------- business methods

    /**
     * Creates a new activity which is a copy of this activitiy with the
     * following differences:
     * <ul>
     * <li>Id is <code>null</code> i.e. the copy is a transient activity
     * <li>Start is the current time, and end is <code>null</code>
     * <li>pause and minutes are 0.
     * <li>status is {@link Status#STOPPED}
     * </ul>
     * 
     * @return
     */
    public Activity copy()
    {
        Activity copy = new Activity(this.name);
        copy.setDescription(description);
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
        Time startCopy = new Time(new Date(this.start.getDate().getTime() + millis));
        Time endCopy = new Time();
        if (this.end != null)
        {
            endCopy.setDate(new Date(this.end.getDate().getTime() + millis));
        }
        else
        {
            endCopy.setDate(startCopy.getDate());
        }
        Activity copy = new Activity(this.name);
        copy.setDescription(description);
        copy.setStart(startCopy);
        copy.setEnd(endCopy);
        copy.setPause(this.pause);
        copy.setBillable(this.billable);
        copy.setStatus(STOPPED);
        copy.setProject(this.project);
        copy.getTags().addAll(this.tags);
        return copy;
    }


    public void start()
    {
        ensureStart().setDate(new Date());
        ensureEnd().setDate(new Date());
        status = RUNNING;
    }


    public void resume()
    {
        ensureStart();
        ensureEnd();
        Date now = new Date();
        if (start.after(now))
        {
            start = new Time();
        }
        if (end.after(now))
        {
            end = new Time();
        }
        pause = pause.plus(new Duration(end.getDate(), now));
        end.setDate(now);
        status = RUNNING;
    }


    public void stop()
    {
        ensureStart();
        ensureEnd().setDate(new Date());
        status = STOPPED;
    }


    public void tick()
    {
        if (status == RUNNING)
        {
            ensureStart();
            ensureEnd().setDate(new Date());
        }
    }


    /**
     * @return <code>true</code> if the start date of this activity is today,
     *         <code>false</code> otherwise.
     */
    @SuppressWarnings("deprecation")
    public boolean isToday()
    {
        Date now = new Date();
        Date startDate = start.getDate();
        if (now.getYear() == startDate.getYear() && now.getMonth() == startDate.getMonth()
                && now.getDate() == startDate.getDate())
        {
            return true;
        }
        return false;
    }


    private void calculateMinutes()
    {
        if (end != null)
        {
            minutes = new Duration(start.getDate(), end.getDate()).minus(pause);
        }
        else
        {
            minutes = Duration.EMPTY;
        }
    }


    private Time ensureStart()
    {
        if (this.start == null)
        {
            this.start = new Time();
        }
        return this.start;
    }


    private Time ensureEnd()
    {
        if (this.end == null)
        {
            this.end = new Time();
        }
        return this.end;
    }


    // --------------------------------------------------------- object methods

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


    /**
     * Calls {@link #calculateMinutes()} after assignement.
     * 
     * @param start
     *            must not be null
     * @throws IllegalArgumentException
     *             if start is <code>null</code>
     */
    public void setStart(Time start)
    {
        if (start == null)
        {
            throw new IllegalArgumentException("Start must not be null!");
        }
        this.start = start;
        calculateMinutes();
    }


    public Time getEnd()
    {
        return end;
    }


    /**
     * Calls {@link #calculateMinutes()} after assignement.
     * 
     * @param end
     */
    public void setEnd(Time end)
    {
        this.end = end;
        calculateMinutes();
    }


    public Duration getPause()
    {
        return pause;
    }


    /**
     * Calls {@link #calculateMinutes()} after assignement.
     * 
     * @param pause
     */
    public void setPause(Duration pause)
    {
        this.pause = pause;
        calculateMinutes();
    }


    public Duration getMinutes()
    {
        return minutes;
    }


    /**
     * Required for JSON (de)serialization - please don't call directly. Minutes
     * are calculated when calling {@link #setStart(Time)},
     * {@link #setEnd(Time)}, {@link #setPause(long)}, {@link #start()},
     * {@link #stop()}, {@link #resume()} and {@link #tick()}.
     * 
     * @param minutes
     */
    public void setMinutes(Duration minutes)
    {
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
