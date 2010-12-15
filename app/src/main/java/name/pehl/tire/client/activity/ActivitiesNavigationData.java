package name.pehl.tire.client.activity;

import static name.pehl.tire.client.activity.Unit.MONTH;
import static name.pehl.tire.client.activity.Unit.WEEK;

/**
 * Simple value object for navigation over activities by year, month and week.
 * 
 * @author $Author$
 * @version $Date$ $Revision: 180
 *          $
 */
public class ActivitiesNavigationData
{
    // -------------------------------------------------------------- constants

    public static final int MIN_YEAR = 1900;

    /**
     * Developers need something to do for the next millenium change ;-)
     */
    public static final int MAX_YEAR = 2999;

    // ---------------------------------------------------------- inner classes

    private final int year;
    private final int month;
    private final int week;
    private final Unit unit;


    // ----------------------------------------------------------- constructors

    public ActivitiesNavigationData()
    {
        this(0, 0, 0, WEEK);
    }


    public ActivitiesNavigationData(int year, int month, int week, Unit unit)
    {
        super();
        this.year = year;
        this.month = month;
        this.week = week;
        this.unit = unit;
    }


    // ---------------------------------------------------- overwritten methods

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + year;
        result = prime * result + month;
        result = prime * result + week;
        result = prime * result + (unit == null ? 0 : unit.hashCode());
        return result;
    }


    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        ActivitiesNavigationData other = (ActivitiesNavigationData) obj;
        if (year != other.year)
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
        if (unit != other.unit)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ActivitiesNavigationData [year=").append(year).append(", month=").append(month)
                .append(", week=").append(week).append(", unit=").append(unit).append("]");
        return builder.toString();
    }


    // --------------------------------------------------------- change methods

    public ActivitiesNavigationData current()
    {
        return new ActivitiesNavigationData(0, 0, 0, unit);
    }


    public ActivitiesNavigationData changeUnit(Unit newUnit)
    {
        return new ActivitiesNavigationData(year, month, week, newUnit);
    }


    public ActivitiesNavigationData increase()
    {
        int newYear = year;
        int newMonth = month;
        int newWeek = week;
        if (unit == MONTH)
        {
            newMonth++;
            if (newMonth > 12)
            {
                newMonth = 1;
                newYear = internalIncreaseYear();
            }
        }
        else if (unit == WEEK)
        {
            newWeek++;
            if (newWeek > 52)
            {
                newWeek = 1;
                newYear = internalIncreaseYear();
            }
        }
        return new ActivitiesNavigationData(newYear, newMonth, newWeek, unit);
    }


    public ActivitiesNavigationData decrease()
    {
        int newYear = year;
        int newMonth = month;
        int newWeek = week;
        if (unit == MONTH)
        {
            newMonth--;
            if (newMonth < 1)
            {
                newMonth = 12;
                newYear = internalDecreaseYear();
            }
        }
        else if (unit == WEEK)
        {
            newWeek--;
            if (newWeek < 1)
            {
                newWeek = 52;
                newYear = internalDecreaseYear();
            }
        }
        return new ActivitiesNavigationData(newYear, newMonth, newWeek, unit);
    }


    private int internalIncreaseYear()
    {
        if (year < MAX_YEAR)
        {
            return year + 1;
        }
        return year;
    }


    private int internalDecreaseYear()
    {
        if (year > MIN_YEAR)
        {
            return year - 1;
        }
        return year;
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


    public Unit getUnit()
    {
        return unit;
    }
}
