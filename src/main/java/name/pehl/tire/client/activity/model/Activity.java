package name.pehl.tire.client.activity.model;

import java.util.Date;
import java.util.List;

import name.pehl.piriti.commons.client.Format;
import name.pehl.tire.client.Defaults;
import name.pehl.tire.client.model.DescriptiveModel;
import name.pehl.tire.client.project.Project;
import name.pehl.tire.client.tag.Tag;
import name.pehl.tire.server.model.Status;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Activity extends DescriptiveModel
{
    // @formatter:off
    @Format(Defaults.DATE_TIME_PATTERN) private Date start;
    @Format(Defaults.DATE_TIME_PATTERN) private Date end;
    private long pause;
    private long minutes;
    private boolean billable;
    private Status status;
    private Project project;
    private List<Tag> tags;
    // @formatter:on

    public Activity()
    {
        this(null);
    }


    public Activity(Date start)
    {
        this.start = start;
    }


    /**
     * Based on start and end.
     * 
     * @return
     * @see name.pehl.tire.client.model.BaseModel#hashCode()
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
     * @see name.pehl.tire.client.model.BaseModel#equals(java.lang.Object)
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
