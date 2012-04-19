package name.pehl.tire.server.activity.control;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Day;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Week;

import org.joda.time.DateMidnight;
import org.joda.time.Months;
import org.joda.time.Weeks;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

public class ActivitiesConverter
{
    @Inject
    ActivityConverter activityConverter;


    public Activities toModel(DateMidnight requested, DateMidnight now, TimeUnit timeunit, List<Activity> activities)
    {
        int year = requested.year().get();
        // Years.yearsBetween(now, requested).getYears() returns wrong
        // results!
        int yearDiff = requested.year().get() - now.year().get();
        int month = requested.monthOfYear().get();
        int monthDiff = Months.monthsBetween(now, requested).getMonths();
        int week = requested.weekOfWeekyear().get();
        int weekDiff = Weeks.weeksBetween(now, requested).getWeeks();
        Activities result = new Activities(year, yearDiff, month, monthDiff, week, weekDiff, timeunit);
        switch (timeunit)
        {
            case MONTH:
                result.getWeeks().addAll(groupByWeeks(activities));
                break;
            case WEEK:
                result.getDays().addAll(groupByDays(activities));
                break;
            case DAY:
                for (Activity activity : activities)
                {
                    result.getActivities().add(activityConverter.toModel(activity));
                }
                break;
            default:
                break;
        }
        return result;
    }


    SortedSet<Week> groupByWeeks(Collection<Activity> activities)
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
            SortedSet<Day> days = groupByDays(activitiesOfOneWeek);
            week.addAll(days);
            weeks.add(week);
        }
        return weeks;
    }


    SortedSet<Day> groupByDays(Collection<Activity> activities)
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
            SortedSet<Activity> activitiesOfOneDay = activitiesPerDay.get(day);
            for (Activity activity : activitiesOfOneDay)
            {
                day.addActivity(activityConverter.toModel(activity));
            }
            days.add(day);
        }
        return days;
    }
}
