package name.pehl.tire.client.activity;

import name.pehl.piriti.client.json.Json;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class YearMonthWeek
{
    // @formatter:off
    @Json protected int year;
    @Json protected int month;
    @Json protected int week;
    // @formatter:on

    public int getYear()
    {
        return year;
    }


    public void setYear(int year)
    {
        this.year = year;
    }


    public int getMonth()
    {
        return month;
    }


    public void setMonth(int month)
    {
        this.month = month;
    }


    public int getWeek()
    {
        return week;
    }


    public void setWeek(int week)
    {
        this.week = week;
    }
}
