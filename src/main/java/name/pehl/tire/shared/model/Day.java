package name.pehl.tire.shared.model;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ComparisonChain;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Day implements Comparable<Day>, Iterable<Activity>
{
    int day;
    SortedSet<Activity> activities;


    public Day()
    {
        this.activities = new TreeSet<Activity>();
    }


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
    public int compareTo(Day that)
    {
        return ComparisonChain.start().compare(that.day, this.day).result();
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


    public SortedSet<Activity> getActivities()
    {
        return activities;
    }


    public Date getStart()
    {
        Date start = null;
        if (!activities.isEmpty())
        {
            return activities.first().getStart();
        }
        return start;
    }


    public Date getEnd()
    {
        Date end = null;
        if (!activities.isEmpty())
        {
            return activities.last().getStart();
        }
        return end;
    }
}
