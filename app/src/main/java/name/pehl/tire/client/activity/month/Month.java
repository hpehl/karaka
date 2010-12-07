package name.pehl.tire.client.activity.month;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import name.pehl.piriti.client.json.Json;
import name.pehl.tire.client.activity.Activity;
import name.pehl.tire.client.activity.YearMonthWeek;
import name.pehl.tire.client.activity.day.Day;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Month extends YearMonthWeek implements Iterable<Day>
{
    // @formatter:off
    @Json(setter = MonthDaysSetter.class) private final List<Day> days;
    // @formatter:on

    /**
     * Construct a new instance of this class
     */
    public Month()
    {
        days = new ArrayList<Day>();
    }


    /**
     * Based on year and month.
     * 
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        return result;
    }


    /**
     * Based on year and month.
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
        Month other = (Month) obj;
        if (year != other.year)
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
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Week [").append(year).append("/").append(month).append(", ")
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


    public List<Activity> getActivities()
    {
        List<Activity> activities = new ArrayList<Activity>();
        for (Day day : this)
        {
            activities.addAll(day.getActivities());
        }
        return activities;
    }
}
