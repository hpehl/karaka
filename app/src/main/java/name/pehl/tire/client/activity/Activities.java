package name.pehl.tire.client.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import name.pehl.piriti.client.json.Json;
import name.pehl.tire.client.activity.day.Day;

/**
 * @author $Author$
 * @version $Date$ $Revision: 177
 *          $
 */
public class Activities implements Iterable<Day>
{
    // @formatter:off
    @Json protected int year;
    @Json protected int month;
    @Json protected int week;
    @Json(setter = DaysSetter.class) private final List<Day> days;
    // @formatter:on

    public Activities()
    {
        days = new ArrayList<Day>();
    }


    /**
     * Based on year, month and week
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
        result = prime * result + week;
        return result;
    }


    /**
     * Based on year, month and week
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
        Activities other = (Activities) obj;
        if (year != other.year)
        {
            return false;
        }
        if (month != other.month)
        {
            return false;
        }
        if (week != other.week)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("ActivitiesByDay [").append(year).append("/").append(month)
                .append("/cw").append(week).append("]");
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


    public double getHours()
    {
        return getMinutes() / 60.0;
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


    public int getWeek()
    {
        return week;
    }


    public void setWeek(int week)
    {
        this.week = week;
    }
}
