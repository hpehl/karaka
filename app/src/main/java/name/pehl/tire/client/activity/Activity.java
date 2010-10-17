package name.pehl.tire.client.activity;

import java.util.Date;

import name.pehl.piriti.client.json.JsonField;
import name.pehl.piriti.client.json.JsonReader;
import name.pehl.piriti.client.json.JsonWriter;
import name.pehl.tire.client.model.DescriptiveModel;

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
    
    @JsonField Date start;
    @JsonField Date end;
    @JsonField int pause;
    @JsonField int minutes;
    @JsonField boolean billable;
    // @formatter:on

    public Activity()
    {
        this(null);
    }


    public Activity(Date start)
    {
        this.start = start;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (start == null ? 0 : start.hashCode());
        result = prime * result + (end == null ? 0 : end.hashCode());
        return result;
    }


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
}
