package name.pehl.tire.shared.model;

import static java.lang.Math.max;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Value class for a duration.
 * 
 * @author $Author$
 * @version $Revision$
 */
@XmlJavaTypeAdapter(DurationAdapter.class)
public class Duration
{
    public static final Duration EMPTY = new Duration(0);

    private final long hours;
    private final long minutes;


    /**
     * Negative minutes are converted to 0. Minutes &gt; 59 are converterd to
     * corresponding hours / minutes
     * 
     * @param minutes
     */
    public Duration(long minutes)
    {
        this(0, minutes);
    }


    public Duration(Date start, Date end)
    {
        this(start != null && end != null ? (end.getTime() - start.getTime()) / 60000 : 0);
    }


    /**
     * Negative values are converted to 0. Minutes &gt; 59 are converterd to
     * corresponding hours / minutes
     * 
     * @param hours
     * @param minutes
     */
    public Duration(long hours, long minutes)
    {
        super();
        long h = max(0, hours);
        long m = max(0, minutes);
        if (m > 59)
        {
            h += m / 60;
            m = m % 60;
        }
        this.hours = h;
        this.minutes = m;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (hours ^ hours >>> 32);
        result = prime * result + (int) (minutes ^ minutes >>> 32);
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
        if (!(obj instanceof Duration))
        {
            return false;
        }
        Duration other = (Duration) obj;
        if (hours != other.hours)
        {
            return false;
        }
        if (minutes != other.minutes)
        {
            return false;
        }
        return true;
    }


    /**
     * Returns <code>String.valueOf(getTotalMinutes())</code>. Please do not
     * change since {@link DurationConverter} depends on this implementation!
     */
    @Override
    public String toString()
    {
        return String.valueOf(getTotalMinutes());
    }


    public boolean isEmpty()
    {
        return this.equals(EMPTY);
    }


    public Duration plus(Duration duration)
    {
        if (duration != null)
        {
            long m = getTotalMinutes() + duration.getTotalMinutes();
            return new Duration(m);
        }
        return this;
    }


    public Duration minus(Duration duration)
    {
        if (duration != null)
        {
            long m = getTotalMinutes() - duration.getTotalMinutes();
            return new Duration(m);
        }
        return this;
    }


    public long getHours()
    {
        return hours;
    }


    public long getMinutes()
    {
        return minutes;
    }


    public long getTotalMinutes()
    {
        return hours * 60 + minutes;
    }
}
