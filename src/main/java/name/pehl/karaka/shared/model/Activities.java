package name.pehl.karaka.shared.model;

import static name.pehl.karaka.shared.model.TimeUnit.WEEK;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Main model class for managing activities. Weeks and days are sorted
 * ascending, activities are sorted descending.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2011-08-31 22:05:44 +0200 (Mi, 31. Aug 2011) $ $Revision: 177
 *          $
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Activities extends HasLinks
{
    // ------------------------------------------------------- member variables

    int year;
    int month;
    int week;
    int day;
    TimeUnit unit;
    SortedSet<Week> weeks;
    SortedSet<Day> days;
    SortedSet<Activity> activities;


    // ------------------------------------------------------------ constructor

    /**
     * Required for JSON (de)serialization - please don't call directly.
     */
    public Activities()
    {
        this(0, 0, 0, 0, WEEK);
    }


    public Activities(int year, int month, int week, int day, TimeUnit unit)
    {
        super();
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
        this.unit = unit;
        this.weeks = new TreeSet<Week>();
        this.days = new TreeSet<Day>();
        this.activities = new TreeSet<Activity>();
    }


    // --------------------------------------------------------- object methods

    /**
     * Based on year, month, week and day
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + day;
        result = prime * result + month;
        result = prime * result + (unit == null ? 0 : unit.hashCode());
        result = prime * result + week;
        result = prime * result + year;
        return result;
    }


    /**
     * Based on year, month, week and day
     * 
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
        if (!(obj instanceof Activities))
        {
            return false;
        }
        Activities other = (Activities) obj;
        if (day != other.day)
        {
            return false;
        }
        if (month != other.month)
        {
            return false;
        }
        if (unit != other.unit)
        {
            return false;
        }
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
        StringBuilder builder = new StringBuilder();
        switch (unit)
        {
            case MONTH:
                builder.append(month).append(" / ");
                break;
            case WEEK:
                builder.append("CW ").append(week).append(" / ");
                break;
            case DAY:
                builder.append(day).append(".").append(month).append(".");
                break;
            default:
                break;
        }
        builder.append(year);
        return builder.toString();
    }


    // ------------------------------------------------------- business methods

    public boolean add(Activity activity)
    {
        boolean added = false;
        if (activity != null)
        {
            Time start = activity.getStart();
            switch (unit)
            {
                case MONTH:
                    Week matchingWeek = findWeek(activity);
                    if (matchingWeek == null)
                    {
                        matchingWeek = new Week(start.getYear(), start.getWeek());
                        added = weeks.add(matchingWeek);
                    }
                    matchingWeek.add(activity);
                    break;
                case WEEK:
                    Day matchinDay = findDay(activity);
                    if (matchinDay == null)
                    {
                        matchinDay = new Day(start.getYear(), start.getMonth(), start.getDay());
                        added = days.add(matchinDay);
                    }
                    matchinDay.add(activity);
                    break;
                case DAY:
                    added = activities.add(activity);
                    break;
                default:
                    break;
            }
        }
        return added;
    }


    public boolean remove(Activity activity)
    {
        boolean removed = false;
        if (activity != null)
        {
            switch (unit)
            {
                case MONTH:
                    for (Iterator<Week> iter = weeks.iterator(); iter.hasNext();)
                    {
                        Week week = iter.next();
                        removed = week.remove(activity);
                        if (week.isEmpty())
                        {
                            iter.remove();
                        }
                    }
                    break;
                case WEEK:
                    for (Iterator<Day> iter = days.iterator(); iter.hasNext();)
                    {
                        Day day = iter.next();
                        removed = day.remove(activity);
                        if (day.isEmpty())
                        {
                            iter.remove();
                        }
                    }
                    break;
                case DAY:
                    removed = activities.remove(activity);
                    break;
                default:
                    break;
            }
        }
        return removed;
    }


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


    /**
     * Returns week the specified activity belongs to, <code>null</code>
     * otherwise.
     * 
     * @param activity
     * @return
     */
    public Week weekOf(Activity activity)
    {
        Week match = null;
        if (activity != null)
        {
            // find the week this activity belongs to
            for (Iterator<Week> iter = weeks.iterator(); iter.hasNext() && match == null;)
            {
                Week w = iter.next();
                if (w.contains(activity))
                {
                    match = w;
                }
            }
        }
        return match;
    }


    /**
     * Returns the day the specified activity belongs to, <code>null</code>
     * otherwise.
     * 
     * @param activity
     * @return
     */
    public Day dayOf(Activity activity)
    {
        Day match = null;
        if (activity != null)
        {
            // find the day this activity belongs to
            for (Iterator<Day> iter = days.iterator(); iter.hasNext() && match == null;)
            {
                Day d = iter.next();
                if (d.contains(activity))
                {
                    match = d;
                }
            }
        }
        return match;
    }


    /**
     * @return a sorted set (ascending) of all activities managed by this
     *         instance.
     */
    public SortedSet<Activity> activities()
    {
        SortedSet<Activity> ordered = new TreeSet<Activity>();
        switch (unit)
        {
            case MONTH:
                for (Week week : weeks)
                {
                    ordered.addAll(week.getActivities());
                }
                break;
            case WEEK:
                for (Day day : days)
                {
                    ordered.addAll(day.getActivities());
                }
                break;
            case DAY:
                ordered.addAll(activities);
                break;
            default:
                break;
        }
        return ordered;
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
                    match = start.getYear() == year && start.getMonth() == month;
                    break;
                case WEEK:
                    match = start.getYear() == year && start.getWeek() == week;
                    break;
                case DAY:
                    match = start.getYear() == year && start.getMonth() == month && start.getDay() == day;
                    break;
                default:
                    break;
            }
        }
        return match;
    }


    public Activity getRunningActivity()
    {
        for (Activity activity : activities())
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
                SortedSet<Activity> orderedActivities = activities();
                if (!orderedActivities.isEmpty())
                {
                    // Activities are sorted descending!
                    start = orderedActivities.last().getStart();
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
                SortedSet<Activity> orderedActivities = activities();
                if (!orderedActivities.isEmpty())
                {
                    // Activities are sorted descending!
                    end = orderedActivities.first().getEnd();
                }
                break;
            default:
                break;
        }
        return end;
    }


    public Duration getDuration()
    {
        long minutes = 0;
        switch (unit)
        {
            case MONTH:
                for (Week week : weeks)
                {
                    minutes += week.getMinutes().getTotalMinutes();
                }
                break;
            case WEEK:
                for (Day day : days)
                {
                    minutes += day.getDuration().getTotalMinutes();
                }
                break;
            case DAY:
                for (Activity activity : activities())
                {
                    minutes += activity.getDuration().getTotalMinutes();
                }
                break;
            default:
                break;
        }
        return new Duration(minutes);
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


    public int getDay()
    {
        return day;
    }


    public void setDay(int day)
    {
        this.day = day;
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


    /**
     * Required for JSON (de)serialization - please don't call directly. Use
     * {@link #activities()} instead.
     * 
     * @return
     */
    public SortedSet<Activity> getActivities()
    {
        return activities;
    }


    /**
     * Required for JSON (de)serialization - please don't call directly. Use
     * {@link #add(Activity)} instead.
     * 
     * @param activities
     */
    public void setActivities(SortedSet<Activity> activities)
    {
        this.activities = activities;
    }


    // --------------------------------------------------------- helper methods

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
