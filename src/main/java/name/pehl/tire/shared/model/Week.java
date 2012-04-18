package name.pehl.tire.shared.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
        return ComparisonChain.start().compare(that.year, this.year).compare(that.week, this.week).result();
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Week [week=").append(week).append(", days=").append(days).append("]");
        return builder.toString();
    }


    // --------------------------------------------------- methods & properties

    public boolean add(Day day)
    {
        return days.add(day);
    }


    public boolean addAll(Collection<? extends Day> c)
    {
        return days.addAll(c);
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


    public long getMinutes()
    {
        long minutes = 0;
        for (Day day : this)
        {
            minutes += day.getMinutes();
        }
        return minutes;
    }


    public int getWeek()
    {
        return week;
    }


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public List<Activity> getActivities()
    {
        List<Activity> allActivities = new ArrayList<Activity>();
        for (Day day : days)
        {
            allActivities.addAll(day.getActivities());
        }
        return allActivities;
    }


    public Date getStart()
    {
        Date start = null;
        if (!days.isEmpty())
        {
            return days.first().getStart();
        }
        return start;
    }


    public Date getEnd()
    {
        Date end = null;
        if (!days.isEmpty())
        {
            return days.last().getStart();
        }
        return end;
    }
}
