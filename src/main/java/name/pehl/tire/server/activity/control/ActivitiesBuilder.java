package name.pehl.tire.server.activity.control;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import name.pehl.tire.server.activity.entity.Activity;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.Day;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.Week;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Months;
import org.joda.time.Weeks;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

public class ActivitiesBuilder
{
    private DateMidnight now;
    private final DateMidnight date;
    private final TimeUnit unit;
    private final List<Activity> activities;


    public ActivitiesBuilder(DateMidnight date, DateTimeZone timeZone, TimeUnit unit, List<Activity> activities)
    {
        this.now = new DateMidnight(timeZone);
        this.date = date;
        this.unit = unit;
        if (activities == null)
        {
            this.activities = Collections.emptyList();
        }
        else
        {
            this.activities = activities;
        }
    }


    public ActivitiesBuilder now(DateMidnight now)
    {
        this.now = now;
        return this;
    }


    public Activities build()
    {
        int year = date.year().get();
        // Years.yearsBetween(now, requested).getYears() returns wrong
        // results!
        int yearDiff = date.year().get() - now.year().get();
        int month = date.monthOfYear().get();
        int monthDiff = Months.monthsBetween(now, date).getMonths();
        int week = date.weekOfWeekyear().get();
        int weekDiff = Weeks.weeksBetween(now, date).getWeeks();
        Activities result = new Activities(year, yearDiff, month, monthDiff, week, weekDiff, unit);
        switch (unit)
        {
            case MONTH:
                result.getWeeks().addAll(groupByWeeks(activities));
                break;
            case WEEK:
                result.getDays().addAll(groupByDays(activities));
                break;
            case DAY:
                ActivityConverter converter = new ActivityConverter();
                for (Activity activity : activities)
                {
                    result.getActivities().add(converter.entityToTransfer(activity));
                }
                break;
            default:
                break;
        }
        return result;
    }


    private SortedSet<Week> groupByWeeks(Collection<Activity> activities)
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


    private SortedSet<Day> groupByDays(Collection<Activity> activities)
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
