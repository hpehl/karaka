package name.pehl.tire.shared.model;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-08-31 22:05:44 +0200 (Mi, 31. Aug 2011) $ $Revision: 177
 *          $
 */
@XmlRootElement
public class Activities
{
    // ------------------------------------------------------- member variables

    private int year;
    private int yearDiff;
    private int month;
    private int monthDiff;
    private int week;
    private int weekDiff;
    private TimeUnit unit;
    private SortedSet<Day> days;
    private SortedSet<Week> weeks;
    private SortedSet<Activity> activities;
    private Link prev;
    private Link self;
    private Link next;


    // ------------------------------------------------------------ constructor

    public Activities()
    {
        this.unit = TimeUnit.DAY;
        this.weeks = new TreeSet<Week>();
        this.days = new TreeSet<Day>();
        this.activities = new TreeSet<Activity>();
    }


    // --------------------------------------------------------- object methods

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
        StringBuilder builder = new StringBuilder("Activities [").append(year).append(", ").append(month)
                .append(", cw").append(week).append("]");
        return builder.toString();
    }


    // -------------------------------------------------- calculated properties

    public Date getStart()
    {
        Date start = null;
        switch (unit)
        {
            case MONTH:
                if (!weeks.isEmpty())
                {
                    start = weeks.first().getStart();
                }
                break;
            case WEEK:
                if (!days.isEmpty())
                {
                    start = days.first().getStart();
                }
                break;
            case DAY:
                if (!activities.isEmpty())
                {
                    start = activities.first().getStart();
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
                    end = weeks.last().getEnd();
                }
                break;
            case WEEK:
                if (!days.isEmpty())
                {
                    end = days.last().getEnd();
                }
                break;
            case DAY:
                if (!activities.isEmpty())
                {
                    end = activities.last().getEnd();
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


    public SortedSet<Activity> getActivities()
    {
        SortedSet<Activity> allActivities = new TreeSet<Activity>();
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
                allActivities.addAll(activities);
                break;
            default:
                break;
        }
        return allActivities;
    }


    public int getNumberOfDays()
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


    // ------------------------------------------------------------- properties

    public int getYear()
    {
        return year;
    }


    public void setYear(int year)
    {
        this.year = year;
    }


    public int getYearDiff()
    {
        return yearDiff;
    }


    public void setYearDiff(int yearDiff)
    {
        this.yearDiff = yearDiff;
    }


    public int getMonth()
    {
        return month;
    }


    public void setMonth(int month)
    {
        this.month = month;
    }


    public int getMonthDiff()
    {
        return monthDiff;
    }


    public void setMonthDiff(int monthDiff)
    {
        this.monthDiff = monthDiff;
    }


    public int getWeek()
    {
        return week;
    }


    public void setWeek(int week)
    {
        this.week = week;
    }


    public int getWeekDiff()
    {
        return weekDiff;
    }


    public void setWeekDiff(int weekDiff)
    {
        this.weekDiff = weekDiff;
    }


    public TimeUnit getUnit()
    {
        return unit;
    }


    public void setUnit(TimeUnit unit)
    {
        this.unit = unit;
    }


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public void setDays(SortedSet<Day> days)
    {
        this.days = days;
    }


    public SortedSet<Week> getWeeks()
    {
        return weeks;
    }


    public void setWeeks(SortedSet<Week> weeks)
    {
        this.weeks = weeks;
    }


    public Link getPrev()
    {
        return prev;
    }


    public void setPrev(Link prev)
    {
        this.prev = prev;
    }


    public Link getSelf()
    {
        return self;
    }


    public void setSelf(Link self)
    {
        this.self = self;
    }


    public Link getNext()
    {
        return next;
    }


    public void setNext(Link next)
    {
        this.next = next;
    }


    public void setActivities(SortedSet<Activity> activities)
    {
        this.activities = activities;
    }
}
