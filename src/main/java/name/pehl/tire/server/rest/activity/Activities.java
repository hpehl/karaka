package name.pehl.tire.server.rest.activity;

import java.util.SortedSet;

import javax.xml.bind.annotation.XmlRootElement;

import name.pehl.tire.server.model.Activity;
import name.pehl.tire.shared.model.TimeUnit;

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


    public Activities(int year, int yearDiff, int month, int monthDiff, int week, int weekDiff, TimeUnit unit)
    {
        this.year = year;
        this.yearDiff = yearDiff;
        this.month = month;
        this.monthDiff = monthDiff;
        this.week = week;
        this.weekDiff = weekDiff;
        this.unit = unit;
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


    public SortedSet<Day> getDays()
    {
        return days;
    }


    public SortedSet<Activity> getActivities()
    {
        return activities;
    }
}
