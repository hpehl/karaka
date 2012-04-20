package name.pehl.tire.server.activity.entity;

import java.util.Date;

import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.common.collect.ComparisonChain;

/**
 * Value object which is used in {@link Activity} to store the moment at which
 * an activity was started / stopped. The class contains the searchable data for
 * that moment as extra properties:
 * <ul>
 * <li> {@link #getYear()}
 * <li> {@link #getMonth()}
 * <li> {@link #getWeek()}
 * <li> {@link #getDay()}
 * </ul>
 * The class internally uses <a href="http://joda-time.sourceforge.net/">Joda
 * Time</a> and a distinct time zone to compute the values. If no time zone is
 * given the default time zone of the JVM is used (which is not recommended!).
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 16:10:26 +0100 (Fr, 17. Dez 2010) $ $Revision: 136
 *          $
 */
public class Time implements Comparable<Time>
{
    // -------------------------------------------------------- private members

    Date date;

    @Transient DateTime dateTime;
    int year;
    int month;
    int week;
    int day;


    // ----------------------------------------------------------- constructors

    Time()
    {
        this(new Date(), DateTimeZone.getDefault());
    }


    public Time(Date date, DateTimeZone timeZone)
    {
        init(date, timeZone);
    }


    void init(Date date, DateTimeZone timeZone)
    {
        if (date == null)
        {
            this.dateTime = new DateTime(timeZone);
        }
        else
        {
            this.dateTime = new DateTime(date.getTime(), timeZone);
        }
        this.date = dateTime.toDate();
        this.year = dateTime.year().get();
        this.month = dateTime.monthOfYear().get();
        this.week = dateTime.weekOfWeekyear().get();
        this.day = dateTime.dayOfMonth().get();
    }


    // --------------------------------------------------------- public methods

    @Override
    public int compareTo(Time that)
    {
        return ComparisonChain.start().compare(this.dateTime, that.dateTime).result();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (dateTime == null ? 0 : dateTime.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Time other = (Time) obj;
        if (dateTime == null)
        {
            if (other.dateTime != null)
            {
                return false;
            }
        }
        else if (!dateTime.equals(other.dateTime))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return dateTime.toString();
    }


    // ------------------------------------------------------------- properties

    public DateTime getDateTime()
    {
        return dateTime;
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
