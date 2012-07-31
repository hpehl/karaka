package name.pehl.tire.shared.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Durations
{
    Duration month;
    Duration week;
    Duration day;


    public Durations()
    {
        this(Duration.ZERO, Duration.ZERO, Duration.ZERO);
    }


    public Durations(Duration month, Duration week, Duration day)
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
        result = prime * result + (day == null ? 0 : day.hashCode());
        result = prime * result + (month == null ? 0 : month.hashCode());
        result = prime * result + (week == null ? 0 : week.hashCode());
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
        if (!(obj instanceof Durations))
        {
            return false;
        }
        Durations other = (Durations) obj;
        if (day == null)
        {
            if (other.day != null)
            {
                return false;
            }
        }
        else if (!day.equals(other.day))
        {
            return false;
        }
        if (month == null)
        {
            if (other.month != null)
            {
                return false;
            }
        }
        else if (!month.equals(other.month))
        {
            return false;
        }
        if (week == null)
        {
            if (other.week != null)
            {
                return false;
            }
        }
        else if (!week.equals(other.week))
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


    public Duration getMonth()
    {
        return month;
    }


    public void setMonth(Duration month)
    {
        this.month = month;
    }


    public Duration getWeek()
    {
        return week;
    }


    public void setWeek(Duration week)
    {
        this.week = week;
    }


    public Duration getDay()
    {
        return day;
    }


    public void setDay(Duration day)
    {
        this.day = day;
    }
}
