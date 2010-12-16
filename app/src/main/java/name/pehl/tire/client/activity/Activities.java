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
    @Json int year;
    @Json int yearDiff;
    @Json int month;
    @Json int monthDiff;
    @Json int week;
    @Json int weekDiff;
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


    public int size()
    {
        return days.size();
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
            // days are sorted in descending order!
            return days.get(days.size() - 1);
        }
        return null;
    }


    public Day getEnd()
    {
        if (!days.isEmpty())
        {
            // days are sorted in descending order!
            return days.get(0);
        }
        return null;
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


    public int getYearDiff()
    {
        return yearDiff;
    }


    public int getMonth()
    {
        return month;
    }


    public int getMonthDiff()
    {
        return monthDiff;
    }


    public int getWeek()
    {
        return week;
    }


    public int getWeekDiff()
    {
        return weekDiff;
    }
}
