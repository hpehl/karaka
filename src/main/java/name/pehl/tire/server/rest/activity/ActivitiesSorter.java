package name.pehl.tire.server.rest.activity;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.server.model.Activity;
import name.pehl.tire.shared.model.TimeUnit;

import org.joda.time.DateMidnight;
import org.joda.time.Months;
import org.joda.time.Weeks;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

/**
 * Sort the specified activities into {@link Week} or {@link Day} instances
 * according to the specified unit.
 * 
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */

public class ActivitiesSorter
{
    public Activities sort(DateMidnight requested, DateMidnight now, TimeUnit unit, List<Activity> activities)
    {
        int year = requested.year().get();
        // Years.yearsBetween(now, requested).getYears() returns wrong results!
        int yearDiff = requested.year().get() - now.year().get();
        int month = requested.monthOfYear().get();
        int monthDiff = Months.monthsBetween(now, requested).getMonths();
        int week = requested.weekOfWeekyear().get();
        int weekDiff = Weeks.weeksBetween(now, requested).getWeeks();
        Activities result = new Activities(year, yearDiff, month, monthDiff, week, weekDiff, unit);

        switch (unit)
        {
            case MONTH:
                result.weeks = createWeeks(activities);
                break;
            case WEEK:
                result.days = createDays(activities);
                break;
            case DAY:
                result.activities = new TreeSet<Activity>(activities);
                break;
            default:
                break;
        }
        return result;
    }


    private SortedSet<Week> createWeeks(Collection<Activity> activities)
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
            SortedSet<Day> days = createDays(activitiesOfOneWeek);
            week.addAll(days);
            weeks.add(week);

        }
        return weeks;
    }


    private SortedSet<Day> createDays(Collection<Activity> activities)
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
}
