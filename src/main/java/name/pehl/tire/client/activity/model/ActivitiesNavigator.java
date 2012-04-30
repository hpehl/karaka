package name.pehl.tire.client.activity.model;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.TimeUnit;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * Simple value object for navigation over activities by year, month and week.
 * Due to the lack of reasonable date/time classes in GWT/JRE, special values
 * for year, month and week are used in the following manner:
 * <dl>
 * <dt>0 for year, month and week</dt>
 * <dd>Navigation data for the current month / week</dd>
 * <dt>0 for year and [-|+]n for either month or week</dt>
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
public class ActivitiesNavigator
{
    // -------------------------------------------------------------- constants

    public static final int MIN_YEAR = 1900;

    /**
     * Developers need something to do for the next millenium change ;-)
     */
    public static final int MAX_YEAR = 2999;

    public static final String PARAM_YEAR = "year";
    public static final String PARAM_MONTH = "month";
    public static final String PARAM_WEEK = "week";
    public static final String PARAM_OFFSET = "offset";
    public static final String VALUE_CURRENT = "current";
    public static final String VALUE_RELATIVE = "relative";

    private static final Logger logger = Logger.getLogger(ActivitiesNavigator.class.getName());

    // ---------------------------------------------------------- inner classes

    private final int year;
    private final int month;
    private final int week;
    private final TimeUnit unit;


    // ----------------------------------------------------------- constructors

    public ActivitiesNavigator()
    {
        this(0, 0, 0, WEEK);
    }


    ActivitiesNavigator(int year, int month, int week, TimeUnit unit)
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
        ActivitiesNavigator other = (ActivitiesNavigator) obj;
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


    // ----------------------------------------------------- navigation methods

    public ActivitiesNavigator current()
    {
        return new ActivitiesNavigator(0, 0, 0, unit);
    }


    public ActivitiesNavigator relative(int offset)
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
            return new ActivitiesNavigator(0, 0, 0, unit);
        }
        else
        {
            if (unit == TimeUnit.MONTH)
            {
                return new ActivitiesNavigator(0, offset, 0, unit);
            }
            else
            {
                return new ActivitiesNavigator(0, 0, offset, unit);
            }
        }
    }


    public ActivitiesNavigator changeUnit(TimeUnit newUnit)
    {
        return new ActivitiesNavigator(year, month, week, newUnit);
    }


    public ActivitiesNavigator increase()
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
        return new ActivitiesNavigator(newYear, newMonth, newWeek, unit);
    }


    public ActivitiesNavigator decrease()
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
        return new ActivitiesNavigator(newYear, newMonth, newWeek, unit);
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


    // ----------------------------------------------------- conversion methods

    public static ActivitiesNavigator fromEvent(ActivitiesLoadedEvent event)
    {
        Activities activities = event.getActivities();
        if (activities != null)
        {
            TimeUnit unit = event.getUnit();
            if (unit == MONTH)
            {
                return new ActivitiesNavigator(activities.getYear(), activities.getMonth(), 0, MONTH);
            }
            else if (unit == WEEK)
            {
                return new ActivitiesNavigator(activities.getYear(), 0, activities.getWeek(), WEEK);
            }
        }
        return new ActivitiesNavigator();
    }


    public static ActivitiesNavigator fromPlaceRequest(PlaceRequest request)

    {
        Set<String> names = request.getParameterNames();
        String year = request.getParameter(ActivitiesNavigator.PARAM_YEAR, "0");
        String month = request.getParameter(ActivitiesNavigator.PARAM_MONTH, "0");
        String week = request.getParameter(ActivitiesNavigator.PARAM_WEEK, "0");

        if (names.contains(ActivitiesNavigator.PARAM_MONTH) && ActivitiesNavigator.VALUE_CURRENT.equals(month))
        {
            return new ActivitiesNavigator(0, 0, 0, MONTH);
        }
        else if (names.contains(ActivitiesNavigator.PARAM_MONTH) && ActivitiesNavigator.VALUE_RELATIVE.equals(month))
        {
            int offsetValue = parseInt(request.getParameter(PARAM_OFFSET, "0"));
            return new ActivitiesNavigator(0, offsetValue, 0, MONTH);
        }
        else if (names.contains(PARAM_WEEK) && VALUE_CURRENT.equals(week))
        {
            return new ActivitiesNavigator(0, 0, 0, WEEK);
        }
        else if (names.contains(PARAM_WEEK) && VALUE_RELATIVE.equals(week))
        {
            int offsetValue = parseInt(request.getParameter(PARAM_OFFSET, "0"));
            return new ActivitiesNavigator(0, 0, offsetValue, WEEK);
        }
        else
        {
            TimeUnit unit = TimeUnit.WEEK;
            int yearValue = parseInt(year);
            int monthValue = parseInt(month);
            int weekValue = parseInt(week);
            if (names.contains(PARAM_YEAR) && names.contains(PARAM_MONTH) && !names.contains(PARAM_WEEK))
            {
                unit = TimeUnit.MONTH;
            }
            return new ActivitiesNavigator(yearValue, monthValue, weekValue, unit);
        }
    }


    private static int parseInt(String value)
    {
        int result = 0;
        try
        {
            result = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            logger.log(Level.WARNING, "Cannot parse \"" + value + "\" as integer");
        }
        return result;
    }


    public PlaceRequest toPlaceRequest()
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard);
        if (unit == TimeUnit.MONTH)
        {
            if (year == 0 && month == 0)
            {
                placeRequest = placeRequest.with(PARAM_MONTH, VALUE_CURRENT);
            }
            else if (year == 0 && month != 0)
            {
                placeRequest = placeRequest.with(PARAM_MONTH, VALUE_RELATIVE).with(PARAM_OFFSET, String.valueOf(month));
            }
            else
            {
                placeRequest = placeRequest.with(PARAM_YEAR, String.valueOf(year)).with(PARAM_MONTH,
                        String.valueOf(month));
            }
        }
        else if (unit == TimeUnit.WEEK)
        {
            if (year == 0 && week == 0)
            {
                placeRequest = placeRequest.with(PARAM_WEEK, VALUE_CURRENT);
            }
            else if (year == 0 && week != 0)
            {
                placeRequest = placeRequest.with(PARAM_WEEK, VALUE_RELATIVE).with(PARAM_OFFSET, String.valueOf(week));
            }
            else
            {
                placeRequest = placeRequest.with(PARAM_YEAR, String.valueOf(year)).with(PARAM_WEEK,
                        String.valueOf(week));
            }
        }
        return placeRequest;
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
