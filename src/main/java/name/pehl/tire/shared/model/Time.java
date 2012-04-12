package name.pehl.tire.shared.model;

import java.util.Date;

import com.google.common.collect.ComparisonChain;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-17 16:10:26 +0100 (Fr, 17. Dez 2010) $ $Revision: 136
 *          $
 */
public class Time implements Comparable<Time>
{
    // -------------------------------------------------------- private members

    private final Date date;
    private final int year;
    private final int month;
    private final int week;
    private final int day;


    // ----------------------------------------------------------- constructors

    public Time(int day, int month, int year, int week, Date date)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.week = week;
        this.date = date;
    }


    // --------------------------------------------------------- public methods

    @Override
    public int compareTo(Time that)
    {
        return ComparisonChain.start().compare(this.date, that.date).result();
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (date == null ? 0 : date.hashCode());
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
        if (date == null)
        {
            if (other.date != null)
            {
                return false;
            }
        }
        else if (!date.equals(other.date))
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return date.toString();
    }


    // ------------------------------------------------------------- properties

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
