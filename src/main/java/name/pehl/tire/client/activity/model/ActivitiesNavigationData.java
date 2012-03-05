package name.pehl.tire.client.activity.model;

import static name.pehl.tire.server.model.TimeUnit.MONTH;
import static name.pehl.tire.server.model.TimeUnit.WEEK;

import java.util.EnumSet;

import name.pehl.tire.server.model.TimeUnit;

/**
 * Simple value object for navigation over activities by year, month and week.
 * therefore special values for year, month and week the are used in the
 * following manner:
 * <dl>
 * <dt>0 for year, month and week</dt>
 * <dd>Navigation data for the current month / week</dd>
 * <dt>0 for year and -n for either month or week</dt>
 * <dd>Navigation data for the month / week relative to the current month / week
 * with offset n.</dd>
 * <dt>Absolute values for year and either month or week within valid ranges</dt>
 * <dd>Navigation data for the specified month / week</dd>
 * </dl>
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-20 15:50:23 +0100 (Mo, 20. Dez 2010) $ $Revision: 180
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
    private final TimeUnit unit;


    // ----------------------------------------------------------- constructors

    public ActivitiesNavigationData()
    {
        this(0, 0, 0, WEEK);
    }


    public ActivitiesNavigationData(TimeUnit unit)
    {
        this(0, 0, 0, unit);
    }


    public ActivitiesNavigationData(int year, int month, int week, TimeUnit unit)
    {
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


    public ActivitiesNavigationData relative(int offset)
    {
        if (offset > 0)
        {
            throw new IllegalArgumentException("Illegal offset " + offset + ": Offset must be <= 0");
        }
        if (unit == TimeUnit.DAY)
        {
            throw new IllegalArgumentException("Illegal unit " + unit + ": Unit must be one of "
                    + EnumSet.of(MONTH, WEEK));
        }
        if (offset == 0)
        {
            return new ActivitiesNavigationData(0, 0, 0, unit);
        }
        else
        {
            if (unit == TimeUnit.MONTH)
            {
                return new ActivitiesNavigationData(0, offset, 0, unit);
            }
            else
            {
                return new ActivitiesNavigationData(0, 0, offset, unit);
            }
        }
    }


    public ActivitiesNavigationData changeUnit(TimeUnit newUnit)
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


    public TimeUnit getUnit()
    {
        return unit;
    }
}
