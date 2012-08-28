package name.pehl.tire.shared.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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

    int year;
    int month;
    int day;
    Set<Activity> activities;


    // ------------------------------------------------------------ constructor

    /**
     * Required for JSON (de)serialization - please don't call directly.
     */
    public Day()
    {
        this(0, 0, 0);
    }


    public Day(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
        this.activities = new HashSet<Activity>();
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on {@link #weight()}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * weight();
        return result;
    }


    /**
     * Based on {@link #weight()}
     * 
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
        if (weight() != other.weight())
        {
            return false;
        }
        return true;
    }


    /**
     * Based on {@link #weight()}
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Day that)
    {
        return ComparisonChain.start().compare(this.weight(), that.weight()).result();
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Day [").append(day).append(".").append(month).append(".").append(year).append(", activities=")
                .append(activities).append("]");
        return builder.toString();
    }


    // ------------------------------------------------------- business methods

    public void add(Activity activity)
    {
        if (activity != null)
        {
            activities.add(activity);
        }
    }


    public void remove(Activity activity)
    {
        if (activity != null)
        {
            activities.remove(activity);
        }
    }


    public boolean contains(Activity activity)
    {
        if (activity != null)
        {
            return activities.contains(activity);
        }
        return false;
    }


    @Override
    public Iterator<Activity> iterator()
    {
        return activities().iterator();
    }


    public boolean isEmpty()
    {
        return activities.isEmpty();
    }


    /**
     * @return a sorted set (ascending) of all activities managed by this
     *         instance.
     */
    public SortedSet<Activity> activities()
    {
        TreeSet<Activity> ordered = new TreeSet<Activity>();
        ordered.addAll(activities);
        return ordered;
    }


    public Time getStart()
    {
        Time start = null;
        if (!activities.isEmpty())
        {
            // Activities are sorted descending!
            return activities().last().getStart();
        }
        return start;
    }


    public Time getEnd()
    {
        Time end = null;
        if (!activities.isEmpty())
        {
            // Activities are sorted descending!
            return activities().first().getEnd();
        }
        return end;
    }


    public Duration getDuration()
    {
        long minutes = 0;
        for (Activity activity : this)
        {
            minutes += activity.getDuration().getTotalMinutes();
        }
        return new Duration(minutes);
    }


    // ------------------------------------------------------------- properties

    /**
     * Required for JSON (de)serialization - please don't call directly. Use
     * {@link #activities()} instead.
     * 
     * @return
     */
    public Set<Activity> getActivities()
    {
        return activities;
    }


    /**
     * Required for JSON (de)serialization - please don't call directly. Use
     * {@link #add(Activity)} instead.
     * 
     * @param activities
     */
    public void setActivities(Set<Activity> activities)
    {
        this.activities = activities;
    }


    public int getYear()
    {
        return year;
    }


    public void setYear(int year)
    {
        this.year = year;
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


    // --------------------------------------------------------- helper methods

    int weight()
    {
        return year * 10000 + month * 100 + day;
    }
}
