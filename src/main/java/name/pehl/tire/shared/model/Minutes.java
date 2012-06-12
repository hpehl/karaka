package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Minutes
{
    long month;
    long week;
    long day;


    public Minutes()
    {
        this(0, 0, 0);
    }


    public Minutes(long month, long week, long day)
    {
        super();
        this.month = month;
        this.week = week;
        this.day = day;
    }


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (day ^ (day >>> 32));
        result = prime * result + (int) (month ^ (month >>> 32));
        result = prime * result + (int) (week ^ (week >>> 32));
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
        if (!(obj instanceof Minutes))
        {
            return false;
        }
        Minutes other = (Minutes) obj;
        if (day != other.day)
        {
            return false;
        }
        if (month != other.month)
        {
            return false;
        }
        if (week != other.week)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return "Minutes [month=" + month + ", week=" + week + ", day=" + day + "]";
    }


    public long getMonth()
    {
        return month;
    }


    public void setMonth(long month)
    {
        this.month = month;
    }


    public long getWeek()
    {
        return week;
    }


    public void setWeek(long week)
    {
        this.week = week;
    }


    public long getDay()
    {
        return day;
    }


    public void setDay(long day)
    {
        this.day = day;
    }
}
