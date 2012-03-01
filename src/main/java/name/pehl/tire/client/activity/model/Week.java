package name.pehl.tire.client.activity.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class Week implements Iterable<Day>
{
    int week;
    List<Day> days;


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
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Week [week=").append(week).append(", days=").append(days).append("]");
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


    public List<Day> getDays()
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
            return days.get(days.size() - 1).getStart();
        }
        return start;
    }


    public Date getEnd()
    {
        Date end = null;
        if (!days.isEmpty())
        {
            return days.get(0).getStart();
        }
        return end;
    }
}
