package name.pehl.tire.rest.activity;

import java.util.SortedSet;

class Activities
{
    final int year;
    final int month;
    final int week;
    final SortedSet<Day> days;


    Activities(int year, int month, int week, SortedSet<Day> days)
    {
        this.year = year;
        this.month = month;
        this.week = week;
        this.days = days;
    }
}
