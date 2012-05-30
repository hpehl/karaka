package name.pehl.tire.shared.model;

import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Weeks and days are sorted ascending, activities are sorted descending.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2011-08-31 22:05:44 +0200 (Mi, 31. Aug 2011) $ $Revision: 177
 *          $
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Activities
{
    // ------------------------------------------------------- member variables

    int year;
    int yearDiff;
    int month;
    int monthDiff;
    int week;
    int weekDiff;
    int day;
    int dayDiff;
    TimeUnit unit;
    SortedSet<Week> weeks;
    SortedSet<Day> days;
    SortedSet<Activity> activities;


    // ------------------------------------------------------------ constructor

    public Activities()
    {
        this(0, 0, 0, 0, 0, 0, 0, 0, WEEK);
    }


    public Activities(int year, int yearDiff, int month, int monthDiff, int week, int weekDiff, int day, int dayDiff,
            TimeUnit unit)
    {
        super();
        this.year = year;
        this.yearDiff = yearDiff;
        this.month = month;
        this.monthDiff = monthDiff;
        this.week = week;
        this.weekDiff = weekDiff;
        this.day = day;
        this.dayDiff = dayDiff;
        this.unit = unit;
        this.weeks = new TreeSet<Week>();
        this.days = new TreeSet<Day>();
        this.activities = new TreeSet<Activity>();
    }


    // --------------------------------------------------------- object methods

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        if (unit == TimeUnit.MONTH)
        {
            builder.append(month);
        }
        else if (unit == WEEK)
        {
            builder.append("CW ").append(week);
        }
        else
        {
            builder.append("undefined");
        }
        builder.append(" / ").append(year);
        return builder.toString();
    }


    // --------------------------------------------------- methods & properties

    public boolean contains(Activity activity)
    {
        boolean result = false;
        if (activity != null)
        {
            switch (unit)
            {
                case MONTH:
                    for (Week week : weeks)
                    {
                        result = week.contains(activity);
                        if (result)
                        {
                            break;
                        }
                    }
                    break;
                case WEEK:
                    for (Day day : days)
                    {
                        result = day.contains(activity);
                        if (result)
                        {
                            break;
                        }
                    }
                    break;
                case DAY:
                    result = activities.contains(activity);
                    break;
                default:
                    break;
            }
        }
        return result;
    }


    public void remove(Activity activity)
    {
        if (activity != null)
        {
            switch (unit)
            {
                case MONTH:
                    for (Week week : weeks)
                    {
                        week.remove(activity);
                    }
                    break;
                case WEEK:
                    for (Day day : days)
                    {
                        day.remove(activity);
                    }
                    break;
                case DAY:
                    activities.remove(activity);
                    break;
                default:
                    break;
            }
        }
    }


    public boolean matchingRange(Activity activity)
    {
        boolean match = false;
        if (activity != null)
        {
            Time start = activity.getStart();
            switch (unit)
            {
                case MONTH:
                    match = start.getMonth() == month;
                    break;
                case WEEK:
                    match = start.getWeek() == week;
                    break;
                case DAY:
                    match = start.getDay() == day;
                    break;
                default:
                    break;
            }
        }
        return match;
    }


    public Activity getRunningActivity()
    {
        for (Activity activity : getActivities())
        {
            if (activity.isRunning())
            {
                return activity;
            }
        }
        return null;
    }


    public Time getStart()
    {
        Time start = null;
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


    public Time getEnd()
    {
        Time end = null;
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


    public int getDay()
    {
        return day;
    }


    public void setDay(int day)
    {
        this.day = day;
    }


    public int getDayDiff()
    {
        return dayDiff;
    }


    public void setDayDiff(int dayDiff)
    {
        this.dayDiff = dayDiff;
    }


    public TimeUnit getUnit()
    {
        return unit;
    }


    public void setUnit(TimeUnit unit)
    {
        this.unit = unit;
    }


    public SortedSet<Week> getWeeks()
    {
        return weeks;
    }


    public void setWeeks(SortedSet<Week> weeks)
    {
        this.weeks = weeks;
    }


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public void setDays(SortedSet<Day> days)
    {
        this.days = days;
    }


    public SortedSet<Activity> getActivities()
    {
        SortedSet<Activity> allActivities = new TreeSet<Activity>();
        switch (unit)
        {
            case MONTH:
                for (Week week : weeks)
                {
                    // TODO Fix order
                    allActivities.addAll(week.getActivities());
                }
                break;
            case WEEK:
                for (Day day : days)
                {
                    // TODO Fix order
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


    public void setActivities(SortedSet<Activity> activities)
    {
        this.activities = activities;
    }


    public void add(Activity activity)
    {
        if (activity != null)
        {
            Time start = activity.getStart();
            switch (unit)
            {
                case MONTH:
                    Week matchingWeek = null;
                    if (start.getYear() == year && start.getMonth() == month)
                    {
                        matchingWeek = findWeek(activity);
                    }
                    if (matchingWeek == null)
                    {
                        matchingWeek = new Week(start.getYear(), start.getMonth());
                        weeks.add(matchingWeek);
                    }
                    matchingWeek.add(activity);
                    break;
                case WEEK:
                    Day matchinDay = null;
                    if (start.getYear() == year && start.getWeek() == week)
                    {
                        matchinDay = findDay(activity);
                    }
                    if (matchinDay == null)
                    {
                        matchinDay = new Day(start.getDay());
                        days.add(matchinDay);
                    }
                    matchinDay.add(activity);
                    break;
                case DAY:
                    activities.add(activity);
                    break;
                default:
                    break;
            }
        }
    }


    private Week findWeek(Activity activity)
    {
        Time start = activity.getStart();
        for (Week week : weeks)
        {
            if (week.getWeek() == start.getWeek() && week.getYear() == start.getYear())
            {
                return week;
            }
        }
        return null;
    }


    private Day findDay(Activity activity)
    {
        Time start = activity.getStart();
        for (Day day : days)
        {
            if (day.getDay() == start.getDay())
            {
                return day;
            }
        }
        return null;
    }
}
