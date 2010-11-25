package name.pehl.tire.client.activity;

import java.util.Date;
import java.util.List;

import name.pehl.piriti.client.json.Json;
import name.pehl.piriti.client.json.JsonReader;
import name.pehl.piriti.client.json.JsonWriter;
import name.pehl.tire.client.model.DescriptiveModel;
import name.pehl.tire.client.project.Project;
import name.pehl.tire.client.tag.Tag;

import com.google.gwt.core.client.GWT;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Activity extends DescriptiveModel
{
    // @formatter:off
    interface ActivityReader extends JsonReader<Activity> {}
    public static final ActivityReader JSON_READER = GWT.create(ActivityReader.class);

    interface ActivityWriter extends JsonWriter<Activity> {}
    public static final ActivityWriter JSON_WRITER = GWT.create(ActivityWriter.class);
    
    @Json private Date start;
    @Json private Date end;
    @Json private int pause;
    @Json private int minutes;
    @Json private boolean billable;
    @Json private Project project;
    @Json private List<Tag> tags;
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
        StringBuilder builder = new StringBuilder("Activity [").append(start).append(", ").append(end).append(", ")
                .append(pause).append(", ").append(billable);
        return builder.toString();
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


    public int getPause()
    {
        return pause;
    }


    public void setPause(int pause)
    {
        this.pause = pause;
    }


    public int getMinutes()
    {
        return minutes;
    }


    public void setMinutes(int minutes)
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
