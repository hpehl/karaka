package name.pehl.tire.shared.model;

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
    int month;
    SortedSet<Activity> activities;


    // ------------------------------------------------------------ constructor

    public Day()
    {
        this(0, 0);
    }


    public Day(int month, int day)
    {
        this.month = month;
        this.day = day;
        this.activities = new TreeSet<Activity>();
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on month and day
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + month;
        return result;
    }


    /**
     * Based on month and day;
     * 
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        if (!(obj instanceof Day))
        {
            return false;
        }
        Day other = (Day) obj;
        if (day != other.day)
        {
            return false;
        }
        if (month != other.month)
        {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(Day that)
    {
        return ComparisonChain.start().compare(this.monthDay(), that.monthDay()).result();
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


    public boolean contains(Activity activity)
    {
        if (activity != null)
        {
            return activities.contains(activity);
        }
        return false;
    }


    public void remove(Activity activity)
    {
        if (activity != null)
        {
            activities.remove(activity);
        }
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


    public void add(Activity activity)
    {
        activities.add(activity);
    }


    public Time getStart()
    {
        Time start = null;
        if (!activities.isEmpty())
        {
            return activities.first().getStart();
        }
        return start;
    }


    public Time getEnd()
    {
        Time end = null;
        if (!activities.isEmpty())
        {
            return activities.last().getStart();
        }
        return end;
    }


    public int getMonth()
    {
        return month;
    }


    public void setMonth(int month)
    {
        this.month = month;
    }


    public int getDay()
    {
        return day;
    }


    public void setDay(int day)
    {
        this.day = day;
    }


    int monthDay()
    {
        return month * 100 + day;
    }
}
