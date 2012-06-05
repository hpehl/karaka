package name.pehl.tire.shared.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.google.common.collect.ComparisonChain;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Time implements Comparable<Time>
{
    // ------------------------------------------------------- member variables

    Date date;
    int year;
    int month;
    int week;
    int day;


    // ------------------------------------------------------------ constructor

    public Time()
    {
        this(new Date(), 0, 0, 0, 0);
    }


    public Time(Date date)
    {
        this(date, 0, 0, 0, 0);
    }


    public Time(Date date, int year, int month, int week, int day)
    {
        super();
        this.date = date;
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
    }


    // --------------------------------------------------------- object methods

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
        if (!(obj instanceof Time))
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
        return String.valueOf(date);
    }


    // ----------------------------------------------------------- date methods

    public boolean before(Date when)
    {
        if (date == null || when == null)
        {
            return false;
        }
        return date.before(when);
    }


    public boolean after(Date when)
    {
        if (date == null || when == null)
        {
            return false;
        }
        return date.after(when);
    }


    // ------------------------------------------------------------- properties

    public Date getDate()
    {
        return date;
    }


    public void setDate(Date date)
    {
        this.date = date;
    }


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


    public int getDay()
    {
        return day;
    }


    public void setDay(int day)
    {
        this.day = day;
    }
}
