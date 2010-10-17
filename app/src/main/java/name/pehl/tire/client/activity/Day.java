package name.pehl.tire.client.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import name.pehl.piriti.client.json.JsonField;
import name.pehl.piriti.client.json.JsonReader;

import com.google.gwt.core.client.GWT;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Day implements Iterable<Activity>
{
    // @formatter:off
    interface DayReader extends JsonReader<Day> {}
    public static final DayReader JSON_READER = GWT.create(DayReader.class);
    
    @JsonField Date date;
    @JsonField List<Activity> activities;
    // @formatter:on

    public Day()
    {
        this(null);
    }


    public Day(Date date)
    {
        this.date = date;
        activities = new ArrayList<Activity>();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (date == null ? 0 : date.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Day other = (Day) obj;
        if (date == null)
        {
            if (other.date != null)
            {
                return false;
            }
        }
        else if (!date.equals(other.date))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Day [").append(date).append(", ").append(activities).append("]");
        return builder.toString();
    }


    @Override
    public Iterator<Activity> iterator()
    {
        return activities.iterator();
    }


    public boolean isEmpty()
    {
        return activities.isEmpty();
    }


    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }


    public int getMinutes()
    {
        int minutes = 0;
        for (Activity activity : this)
        {
            minutes += activity.getMinutes();
        }
        return minutes;
    }


    public Date getDate()
    {
        return date;
    }


    public void setDate(Date date)
    {
        this.date = date;
    }
}
