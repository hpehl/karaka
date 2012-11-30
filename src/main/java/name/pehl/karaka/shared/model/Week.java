package name.pehl.karaka.shared.model;

import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

import javax.annotation.Nullable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Week implements Comparable<Week>, Iterable<Day>
{
    // ------------------------------------------------------- member variables

    int year;
    int week;
    SortedSet<Day> days;


    // ------------------------------------------------------------ constructor


    /**
     * Required for JSON (de)serialization - please don't call directly.
     */
    public Week()
    {
        this(0, 0);
    }


    public Week(int year, int week)
    {
        this.year = year;
        this.week = week;
        this.days = new TreeSet<Day>();
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
        result = prime * result + weight();
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Week other = (Week) obj;
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
    public int compareTo(Week that)
    {
        return ComparisonChain.start().compare(this.weight(), that.weight()).result();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Week [cw").append(week).append("/").append(year).append(", days=").append(days).append("]");
        return builder.toString();
    }


    // ------------------------------------------------------- business methods

    public boolean add(Activity activity)
    {
        boolean added = false;
        if (activity != null)
        {
            Time start = activity.getStart();
            Day matchinDay = findDay(activity);
            if (matchinDay == null)
            {
                matchinDay = new Day(start.getYear(), start.getMonth(), start.getDay());
                added = days.add(matchinDay);
            }
            matchinDay.add(activity);
        }
        return added;
    }

    public boolean remove(Activity activity)
    {
        boolean removed = false;
        if (activity != null)
        {
            for (Iterator<Day> iter = days.iterator(); iter.hasNext(); )
            {
                Day day = iter.next();
                removed = day.remove(activity);
            }
        }
        return removed;
    }

    public boolean contains(Activity activity)
    {
        boolean result = false;
        if (activity != null)
        {
            for (Day day : days)
            {
                result = day.contains(activity);
                if (result)
                {
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Iterator<Day> iterator()
    {
        return days.iterator();
    }

    public SortedSet<Day> noneEmptyDays()
    {
        return Sets.filter(days, new Predicate<Day>()
        {
            @Override
            public boolean apply(@Nullable final Day input)
            {
                return input != null && !input.isEmpty();
            }
        });
    }

    public boolean isEmpty()
    {
        if (!days.isEmpty())
        {
            for (Day day : days)
            {
                if (!day.isEmpty())
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return a sorted set (ascending) of all activities managed by this
     *         instance.
     */
    public SortedSet<Activity> getActivities()
    {
        SortedSet<Activity> ordered = new TreeSet<Activity>();
        for (Day day : days)
        {
            ordered.addAll(day.getActivities());
        }
        return ordered;
    }

    public Time getStart()
    {
        Day first = noneEmptyDays().first();
        return first != null ? first.getStart() : null;
    }

    public Time getEnd()
    {
        Day last = noneEmptyDays().last();
        return last != null ? last.getEnd() : null;
    }

    public Duration getMinutes()
    {
        long minutes = 0;
        for (Day day : this)
        {
            minutes += day.getDuration().getTotalMinutes();
        }
        return new Duration(minutes);
    }

    public boolean add(Day day)
    {
        return days.add(day);
    }


    // ------------------------------------------------------------- properties

    public SortedSet<Day> getDays()
    {
        return days;
    }

    /**
     * Required for JSON (de)serialization - please don't call directly. Use
     * {@link #add(Day)} and/or {@link #add(Activity)} instead.
     *
     * @param days
     */
    public void setDays(SortedSet<Day> days)
    {
        this.days = days;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getWeek()
    {
        return week;
    }

    public void setWeek(int week)
    {
        this.week = week;
    }


    // --------------------------------------------------------- helper methods

    Day findDay(Activity activity)
    {
        Time start = activity.getStart();
        for (Day day : days)
        {
            if (day.getDay() == start.getDay())
            {
                return day;
            }
        }
        return null;
    }

    int weight()
    {
        return year * 100 + week;
    }
}
