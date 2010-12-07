package name.pehl.tire.rest.activity;

import java.util.SortedSet;

class JsonWeek
{
    final int year;
    final int month;
    final int week;
    final SortedSet<JsonDay> days;


    JsonWeek(int year, int month, int week, SortedSet<JsonDay> days)
    {
        this.year = year;
        this.month = month;
        this.week = week;
        this.days = days;
    }
}
