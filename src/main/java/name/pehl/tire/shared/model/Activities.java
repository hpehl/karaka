package name.pehl.tire.shared.model;

import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2011-08-31 22:05:44 +0200 (Mi, 31. Aug 2011) $ $Revision: 177
 *          $
 */
@XmlRootElement
public class Activities extends BaseModel
{
    // ------------------------------------------------------- member variables

    final private int year;
    final private int yearDiff;
    final private int month;
    final private int monthDiff;
    final private int week;
    final private int weekDiff;
    final private TimeUnit unit;
    final private SortedSet<Week> weeks;
    final private SortedSet<Day> days;
    final private SortedSet<Activity> activities;


    // ------------------------------------------------------------ constructor

    public Activities(int year, int yearDiff, int month, int monthDiff, int week, int weekDiff, TimeUnit unit,
            Collection<Activity> activities)
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
        this.activities = new TreeSet<Activity>(activities);
        switch (unit)
        {
            case MONTH:
                this.weeks.addAll(groupByWeek(activities));
                break;
            case WEEK:
                this.days.addAll(groupByDay(activities));
                break;
            case DAY:
                // no grouping necessary
                break;
            default:
                break;
        }
    }


    private SortedSet<Week> groupByWeek(Collection<Activity> activities)
    {
        SortedSet<Week> weeks = new TreeSet<Week>();
        SortedSetMultimap<Week, Activity> activitiesPerWeek = TreeMultimap.create();
        for (Activity activity : activities)
        {
            Week week = new Week(activity.getStart().getYear(), activity.getStart().getWeek());
            activitiesPerWeek.put(week, activity);
        }

        for (Week week : activitiesPerWeek.keySet())
        {
            SortedSet<Activity> activitiesOfOneWeek = activitiesPerWeek.get(week);
            SortedSet<Day> days = groupByDay(activitiesOfOneWeek);
            week.addAll(days);
            weeks.add(week);

        }
        return weeks;
    }


    private SortedSet<Day> groupByDay(Collection<Activity> activities)
    {
        SortedSet<Day> days = new TreeSet<Day>();
        SortedSetMultimap<Day, Activity> activitiesPerDay = TreeMultimap.create();
        for (Activity activity : activities)
        {
            Day day = new Day(activity.getStart().getDay());
            activitiesPerDay.put(day, activity);
        }
        for (Day day : activitiesPerDay.keySet())
        {
            day.addAll(activitiesPerDay.get(day));
            days.add(day);
        }
        return days;
    }


    // --------------------------------------------------------- object methods

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


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public SortedSet<Activity> getActivities()
    {
        return activities;
    }
}
