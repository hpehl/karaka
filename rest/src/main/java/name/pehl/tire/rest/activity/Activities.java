package name.pehl.tire.rest.activity;

import java.util.SortedSet;

class Activities
{
    final int year;
    final int yearDiff;
    final int month;
    final int monthDiff;
    final int week;
    final int weekDiff;
    final SortedSet<Day> days;


    Activities(int year, int yearDiff, int month, int monthDiff, int week, int weekDiff, SortedSet<Day> days)
    {
        this.year = year;
        this.yearDiff = yearDiff;
        this.month = month;
        this.monthDiff = monthDiff;
        this.week = week;
        this.weekDiff = weekDiff;
        this.days = days;
    }
}
