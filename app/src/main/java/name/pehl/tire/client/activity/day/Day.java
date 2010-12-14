package name.pehl.tire.client.activity.day;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import name.pehl.piriti.client.json.Json;
import name.pehl.tire.client.Defaults;
import name.pehl.tire.client.activity.Activity;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Day implements Iterable<Activity>
{
    // @formatter:off
    @Json(format = Defaults.DATE_TIME_PATTERN) private Date date;
    @Json(setter = DayActivitiesSetter.class) private final List<Activity> activities;
    // @formatter:on

    public Day()
    {
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


    public void clearActivities()
    {
        activities.clear();
    }


    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }


    public List<Activity> getActivities()
    {
        return activities;
    }


    public long getMinutes()
    {
        long minutes = 0;
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
