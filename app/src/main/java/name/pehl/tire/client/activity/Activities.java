package name.pehl.tire.client.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import name.pehl.piriti.client.json.Json;
import name.pehl.tire.client.activity.day.Day;
import name.pehl.tire.client.activity.week.Week;
import name.pehl.tire.model.TimeUnit;

/**
 * @author $Author$
 * @version $Date$ $Revision: 177
 *          $
 */
public class Activities
{
    // @formatter:off
    @Json int year;
    @Json int yearDiff;
    @Json int month;
    @Json int monthDiff;
    @Json int week;
    @Json int weekDiff;
    @Json TimeUnit unit;
    @Json List<Week> weeks;
    @Json List<Day> days;
    @Json List<Activity> activities;
    // @formatter:on

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
        StringBuilder builder = new StringBuilder("ActivitiesByDay [").append(year).append(", ").append(month)
                .append(", cw").append(week).append("]");
        return builder.toString();
    }


    public Date getStart()
    {
        Date start = null;
        switch (unit)
        {
            case MONTH:
                if (!weeks.isEmpty())
                {
                    start = weeks.get(weeks.size() - 1).getStart();
                }
                break;
            case WEEK:
                if (!days.isEmpty())
                {
                    start = days.get(days.size() - 1).getStart();
                }
                break;
            case DAY:
                if (!activities.isEmpty())
                {
                    start = activities.get(activities.size() - 1).getStart();
                }
                break;
            default:
                break;
        }
        return start;
    }


    public Date getEnd()
    {
        Date end = null;
        switch (unit)
        {
            case MONTH:
                if (!weeks.isEmpty())
                {
                    end = weeks.get(0).getEnd();
                }
                break;
            case WEEK:
                if (!days.isEmpty())
                {
                    end = days.get(0).getEnd();
                }
                break;
            case DAY:
                if (!activities.isEmpty())
                {
                    end = activities.get(0).getStart();
                }
                break;
            default:
                break;
        }
        return end;
    }


    public long getMinutes()
    {
        long minutes = 0;
        switch (unit)
        {
            case MONTH:
                for (Week week : weeks)
                {
                    minutes += week.getMinutes();
                }
                break;
            case WEEK:
                for (Day day : days)
                {
                    minutes += day.getMinutes();
                }
                break;
            case DAY:
                for (Activity activity : activities)
                {
                    minutes += activity.getMinutes();
                }
                break;
            default:
                break;
        }
        return minutes;
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


    public TimeUnit getUnit()
    {
        return unit;
    }


    public List<Week> getWeeks()
    {
        return weeks;
    }


    public List<Day> getDays()
    {
        return days;
    }


    public List<Activity> getActivities()
    {
        List<Activity> allActivities = new ArrayList<Activity>();
        switch (unit)
        {
            case MONTH:
                for (Week week : weeks)
                {
                    allActivities.addAll(week.getActivities());
                }
                break;
            case WEEK:
                for (Day day : days)
                {
                    allActivities.addAll(day.getActivities());
                }
                break;
            case DAY:
                allActivities = activities;
                break;
            default:
                break;
        }
        return allActivities;
    }


    public int days()
    {
        int result = 0;
        switch (unit)
        {
            case MONTH:
                for (Week week : weeks)
                {
                    result += week.getDays().size();
                }
                break;
            case WEEK:
                result = days.size();
                break;
            case DAY:
                result = 1;
                break;
            default:
                break;
        }
        return result;
    }
}
