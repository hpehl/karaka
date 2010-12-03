package name.pehl.tire.rest.activity;

import java.util.SortedSet;

class JsonWeek
{
    final int year;
    final int week;
    final SortedSet<JsonDay> days;


    JsonWeek(int year, int week, SortedSet<JsonDay> days)
    {
        this.year = year;
        this.week = week;
        this.days = days;
    }
}
