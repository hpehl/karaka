package name.pehl.tire.client.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import name.pehl.piriti.client.json.Json;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Week implements Iterable<Day>
{
    // @formatter:off
    @Json private int year;
    @Json private int week;
    @Json(setter = WeekDaysSetter.class) private final List<Day> days;
    // @formatter:on

    /**
     * Construct a new instance of this class
     */
    public Week()
    {
        days = new ArrayList<Day>();
    }


    /**
     * Based on year and calendarWeek.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + week;
        result = prime * result + year;
        return result;
    }


    /**
     * Based on year and calendarWeek.
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Week other = (Week) obj;
        if (week != other.week)
        {
            return false;
        }
        if (year != other.year)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Week [").append(year).append("/").append(week).append(", ")
                .append(days).append("]");
        return builder.toString();
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


    public void clearDays()
    {
        days.clear();
    }


    public void addDay(Day day)
    {
        days.add(day);
    }


    public Day getStart()
    {
        if (!days.isEmpty())
        {
            return days.get(0);
        }
        return null;
    }


    public Day getEnd()
    {
        if (!days.isEmpty())
        {
            return days.get(days.size() - 1);
        }
        return null;
    }


    public int getMinutes()
    {
        int minutes = 0;
        for (Day day : this)
        {
            minutes += day.getMinutes();
        }
        return minutes;
    }


    public void setYear(int year)
    {
        this.year = year;
    }


    public int getYear()
    {
        return year;
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
