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
public class Week implements Comparable<Week>, Iterable<Day>
{
    // ------------------------------------------------------- member variables

    int year;
    int week;
    SortedSet<Day> days;


    // ------------------------------------------------------------ constructor

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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + week;
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
        Week other = (Week) obj;
        if (week != other.week)
        {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(Week that)
    {
        return ComparisonChain.start().compare(this.year, that.year).compare(this.week, that.week).result();
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Week [week=").append(week).append(", days=").append(days).append("]");
        return builder.toString();
    }


    // --------------------------------------------------- methods & properties

    public void add(Activity activity)
    {
        if (activity != null)
        {
            Time start = activity.getStart();
            Day matchinDay = null;
            if (start.getYear() == year && start.getWeek() == week)
            {
                matchinDay = findDay(activity);
            }
            if (matchinDay == null)
            {
                matchinDay = new Day(start.getYear(), start.getMonth(), start.getDay());
                days.add(matchinDay);
            }
            matchinDay.add(activity);
        }
    }


    private Day findDay(Activity activity)
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


    public void remove(Activity activity)
    {
        if (activity != null)
        {
            for (Day day : days)
            {
                day.remove(activity);
            }
        }
    }


    public SortedSet<Activity> getActivities()
    {
        SortedSet<Activity> activities = new TreeSet<Activity>();
        for (Day day : days)
        {
            activities.addAll(day.getActivities());
        }
        return activities;
    }


    public Time getStart()
    {
        Time start = null;
        if (!days.isEmpty())
        {
            return days.first().getStart();
        }
        return start;
    }


    public Time getEnd()
    {
        Time end = null;
        if (!days.isEmpty())
        {
            return days.last().getEnd();
        }
        return end;
    }


    public long getMinutes()
    {
        long minutes = 0;
        for (Day day : this)
        {
            minutes += day.getMinutes();
        }
        return minutes;
    }


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public boolean add(Day day)
    {
        return days.add(day);
    }


    public void setDays(SortedSet<Day> days)
    {
        this.days = days;
    }


    @Override
    public Iterator<Day> iterator()
    {
        return days.iterator();
    }


    public boolean isEmpty()
    {
        return days.isEmpty();
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
}
