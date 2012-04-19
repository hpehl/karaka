package name.pehl.tire.shared.model;

import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.google.common.collect.ComparisonChain;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Day implements Comparable<Day>, Iterable<Activity>
{
    // ------------------------------------------------------- member variables

    int day;
    SortedSet<Activity> activities;


    // ------------------------------------------------------------ constructor

    public Day()
    {
        this(0);
    }


    public Day(int day)
    {
        this.day = day;
        this.activities = new TreeSet<Activity>();
    }


    // --------------------------------------------------------- object methods

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


    // --------------------------------------------------- methods & properties

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


    public SortedSet<Activity> getActivities()
    {
        return activities;
    }


    public void setActivities(SortedSet<Activity> activities)
    {
        this.activities = activities;
    }


    public void addActivity(Activity activity)
    {
        activities.add(activity);
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


    public int getDay()
    {
        return day;
    }


    public void setDay(int day)
    {
        this.day = day;
    }
}
