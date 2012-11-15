package name.pehl.karaka.server.activity.entity;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.ReadableInstant;

import javax.persistence.Transient;
import java.util.Date;

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
public class Time implements ReadableInstant
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

    // ------------------------------------------------------ delegate methods


    public boolean isAfter(final long instant) {return dateTime.isAfter(instant);}

    public boolean isAfterNow() {return dateTime.isAfterNow();}

    @Override
    public long getMillis() {return dateTime.getMillis();}

    @Override
    public Chronology getChronology() {return dateTime.getChronology();}

    @Override
    public DateTimeZone getZone() {return dateTime.getZone();}

    @Override
    public int get(final DateTimeFieldType type) {return dateTime.get(type);}

    @Override
    public boolean isSupported(final DateTimeFieldType field) {return dateTime.isSupported(field);}

    @Override
    public Instant toInstant() {return dateTime.toInstant();}

    public Date toDate() {return dateTime.toDate();}

    @Override
    public boolean isEqual(final ReadableInstant instant) {return dateTime.isEqual(instant);}

    public boolean isAfter(final ReadableInstant instant) {return dateTime.isAfter(instant);}

    public boolean isBefore(final long instant) {return dateTime.isBefore(instant);}

    public boolean isBeforeNow() {return dateTime.isBeforeNow();}

    public boolean isBefore(final ReadableInstant instant) {return dateTime.isBefore(instant);}

    @Override
    public int compareTo(final ReadableInstant readableInstant) {return dateTime.compareTo(readableInstant);}
}
