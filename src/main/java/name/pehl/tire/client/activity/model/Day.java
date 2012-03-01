package name.pehl.tire.client.activity.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Day implements Iterable<Activity>
{
    int day;
    List<Activity> activities;


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
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
        if (day != other.day)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Day [day=").append(day).append(", activities=").append(activities).append("]");
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


    public long getMinutes()
    {
        long minutes = 0;
        for (Activity activity : this)
        {
            minutes += activity.getMinutes();
        }
        return minutes;
    }


    public int getDay()
    {
        return day;
    }


    public List<Activity> getActivities()
    {
        return activities;
    }


    public Date getStart()
    {
        Date start = null;
        if (!activities.isEmpty())
        {
            return activities.get(activities.size() - 1).getStart();
        }
        return start;
    }


    public Date getEnd()
    {
        Date end = null;
        if (!activities.isEmpty())
        {
            return activities.get(0).getStart();
        }
        return end;
    }
}
