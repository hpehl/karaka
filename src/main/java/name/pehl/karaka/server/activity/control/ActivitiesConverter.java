package name.pehl.karaka.server.activity.control;

import name.pehl.karaka.server.activity.entity.Activity;
import name.pehl.karaka.server.activity.entity.Time;
import name.pehl.karaka.shared.model.Activities;
import name.pehl.karaka.shared.model.Day;
import name.pehl.karaka.shared.model.TimeUnit;
import name.pehl.karaka.shared.model.Week;
import org.joda.time.DateMidnight;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ActivitiesConverter
{
    @Inject ActivityConverter activityConverter;

    public Activities toModel(DateMidnight date, TimeUnit timeunit, List<Activity> activities)
    {
        Activities result = new Activities(date.getYear(), date.getMonthOfYear(), date.getWeekOfWeekyear(),
                date.getDayOfMonth(), timeunit);
        switch (timeunit)
        {
            case MONTH:
                result.getWeeks().addAll(groupByWeeks(date, activities));
                break;
            case WEEK:
                result.getDays().addAll(groupByDays(date, activities));
                break;
            case DAY:
                for (Activity activity : activities)
                {
                    result.add(activityConverter.toModel(activity));
                }
                break;
            default:
                break;
        }
        return result;
    }

    SortedSet<Week> groupByWeeks(final DateMidnight date, Collection<Activity> activities)
    {
        // generate *all* days for *all* weeks and fill in the placeRequestFor
        SortedSet<Week> weeks = new TreeSet<Week>();
        DateMidnight firstDayInMonth = date.monthOfYear().roundFloorCopy();
        DateMidnight lastDayInMonth = firstDayInMonth.plusMonths(1);
        for (DateMidnight currentWeek = firstDayInMonth; currentWeek.isBefore(lastDayInMonth);
                currentWeek = currentWeek.plusWeeks(1))
        {
            Week week = new Week(currentWeek.getYear(), currentWeek.getWeekOfWeekyear());
            weeks.add(week);
            DateMidnight startOfWeek = currentWeek.weekOfWeekyear().roundFloorCopy();
            DateMidnight endOfWeek = startOfWeek.plusWeeks(1);
            for (DateMidnight currentDate = startOfWeek; currentDate.isBefore(endOfWeek);
                    currentDate = currentDate.plusDays(1))
            {
                Day day = new Day(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth());
                week.add(day);
                for (Activity activity : activities)
                {
                    Time start = activity.getStart();
                    if (currentDate.equals(start.toDateMidnight()) && date.getMonthOfYear() == start.getMonth())
                    {
                        day.add(activityConverter.toModel(activity));
                    }
                }
            }
        }
        return weeks;
    }

    SortedSet<Day> groupByDays(final DateMidnight date, Collection<Activity> activities)
    {
        // generate *all* days and fill in the placeRequestFor
        SortedSet<Day> days = new TreeSet<Day>();
        DateMidnight.Property weekProp = date.weekOfWeekyear();
        DateMidnight startOfWeek = date.weekOfWeekyear().roundFloorCopy();
        DateMidnight endOfWeek = startOfWeek.plusWeeks(1);
        for (DateMidnight currentDate = startOfWeek; currentDate.isBefore(endOfWeek);
                currentDate = currentDate.plusDays(1))
        {
            Day day = new Day(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth());
            days.add(day);
            for (Activity activity : activities)
            {
                Time start = activity.getStart();
                if (currentDate.equals(start.toDateMidnight()))
                {
                    day.add(activityConverter.toModel(activity));
                }
            }
        }
        return days;
    }
}
