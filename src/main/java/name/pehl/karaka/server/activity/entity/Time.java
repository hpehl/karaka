package name.pehl.karaka.server.activity.entity;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Ignore;
import org.joda.time.Chronology;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.ReadableDateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePeriod;

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
@Embed
public class Time implements ReadableInstant
{
    // -------------------------------------------------------- private members

    Date date;
    @Ignore DateTime dateTime;
    int year;
    int month;
    int week;
    int day;


    // ----------------------------------------------------------- constructors


    public Time()
    {
        init(null);
    }

    public Time(Time time)
    {
        init(time == null ? null : time.dateTime);
    }

    public Time(ReadableDateTime dateTime)
    {
        init(dateTime);
    }

    public Time(DateTime dateTime)
    {
        init(dateTime);
    }

    public Time(Date date, DateTimeZone timeZone)
    {
        init(new DateTime(date, timeZone));
    }

    void init(ReadableDateTime dateTime)
    {
        this.dateTime = dateTime == null ? new DateTime(DateTimeZone.getDefault()) : new DateTime(dateTime);
        this.date = this.dateTime.toDate();
        this.year = this.dateTime.year().get();
        this.month = this.dateTime.monthOfYear().get();
        this.week = this.dateTime.weekOfWeekyear().get();
        this.day = this.dateTime.dayOfMonth().get();
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

    public DateMidnight toDateMidnight() {return dateTime.toDateMidnight();}

    public DateTime plusMinutes(final int minutes) {return dateTime.plusMinutes(minutes);}

    public DateTime plus(final ReadablePeriod period) {return dateTime.plus(period);}
}
