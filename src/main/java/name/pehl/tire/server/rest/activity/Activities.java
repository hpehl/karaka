package name.pehl.tire.server.rest.activity;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import name.pehl.tire.server.model.Activity;
import name.pehl.tire.shared.model.TimeUnit;

import org.joda.time.DateMidnight;
import org.joda.time.DateTimeZone;
import org.joda.time.Months;
import org.joda.time.Weeks;

import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

/**
 * Data holder used for the json representations of activities. This class is
 * used for month, week and day based representations:
 * <ul>
 * <li>Month based: weeks collection must not be null
 * <li>Week based: days collection must not be null
 * <li>Day based: activities collection must not be null
 * </ul>
 * 
 * @author $LastChangedBy: harald.pehl $
 * @version $LastChangedRevision: 190 $
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Activities
{
    final int year;
    final int yearDiff;
    final int month;
    final int monthDiff;
    final int week;
    final int weekDiff;
    final TimeUnit unit;
    SortedSet<Week> weeks;
    SortedSet<Day> days;
    SortedSet<Activity> activities;


    Activities(int year, int yearDiff, int month, int monthDiff, int week, int weekDiff, TimeUnit unit)
    {
        this.year = year;
        this.yearDiff = yearDiff;
        this.month = month;
        this.monthDiff = monthDiff;
        this.week = week;
        this.weekDiff = weekDiff;
        this.unit = unit;
    }

    public static class Builder
    {
        private DateMidnight now;
        private final DateMidnight date;
        private final TimeUnit unit;
        private final List<Activity> activities;


        public Builder(DateMidnight date, DateTimeZone timeZone, TimeUnit unit, List<Activity> activities)
        {
            this.now = new DateMidnight(timeZone);
            this.date = date;
            this.unit = unit;
            this.activities = activities;
        }


        public Builder now(DateMidnight now)
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
}
