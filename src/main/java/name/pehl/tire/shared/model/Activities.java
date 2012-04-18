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

    int year;
    int yearDiff;
    int month;
    int monthDiff;
    int week;
    int weekDiff;
    TimeUnit unit;
    SortedSet<Week> weeks;
    SortedSet<Day> days;
    SortedSet<Activity> activities;


    // ------------------------------------------------------------ constructor

    public Activities(int year, int yearDiff, int month, int monthDiff, int week, int weekDiff, TimeUnit unit)
    {
        super();
        this.year = year;
        this.yearDiff = yearDiff;
        this.month = month;
        this.monthDiff = monthDiff;
        this.week = week;
        this.weekDiff = weekDiff;
        this.unit = unit;
        this.weeks = new TreeSet<Week>();
        this.days = new TreeSet<Day>();
        this.activities = new TreeSet<Activity>();
    }


    // --------------------------------------------------------- object methods

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("Activities [").append(year).append(", ").append(month)
                .append(", cw").append(week).append("]");
        return builder.toString();
    }


    // --------------------------------------------------- methods & properties

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


    public SortedSet<Week> getWeeks()
    {
        return weeks;
    }


    public void addWeek(Week week)
    {
        weeks.add(week);
    }


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public void addDay(Day day)
    {
        days.add(day);
    }


    public SortedSet<Activity> getActivities()
    {
        return activities;
    }


    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }
}
