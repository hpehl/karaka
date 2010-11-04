package name.pehl.tire.model;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class Time
{
    @Unindexed
    private final Date date;
    private final int year;
    private final int month;
    private final int week;
    private final int day;


    public Time()
    {
        this(DateTimeZone.getDefault());
    }


    public Time(String timeZoneId)
    {
        this(DateTimeZone.forID(timeZoneId));
    }


    public Time(DateTimeZone timeZone)
    {
        DateTime dt = new DateTime(timeZone);
        this.date = dt.toDate();
        this.year = dt.year().get();
        this.month = dt.monthOfYear().get();
        this.week = dt.weekyear().get();
        this.day = dt.dayOfMonth().get();
    }


    @Override
    public String toString()
    {
        return date.toString();
    }


    public Date getDate()
    {
        return date;
    }


    public int getYear()
    {
        return year;
    }


    public int getMonth()
    {
        return month;
    }


    public int getWeek()
    {
        return week;
    }


    public int getDay()
    {
        return day;
    }
}
