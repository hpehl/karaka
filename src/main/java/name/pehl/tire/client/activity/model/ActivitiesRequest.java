package name.pehl.tire.client.activity.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * TODO Tests, tests, tests
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivitiesRequest
{
    public static final String PARAM_CURRENT = "current";
    public static final String PARAM_PREVIOUS = "previous";
    public static final String PARAM_NEXT = "next";
    public static final String PARAM_YEAR = "year";
    public static final String PARAM_MONTH = "month";
    public static final String PARAM_WEEK = "week";

    private static final Logger logger = Logger.getLogger(ActivitiesRequest.class.getName());

    private final YearAndMonthOrWeek yearAndMonthOrWeek;


    public ActivitiesRequest(PlaceRequest placeRequest, Activities activities)
    {
        this.yearAndMonthOrWeek = new YearAndMonthOrWeek();
        if (hasParameter(placeRequest, PARAM_CURRENT))
        {
            TimeUnit unit = parseTimeUnit(placeRequest.getParameter(PARAM_CURRENT, null));
            this.yearAndMonthOrWeek.setUnit(unit);
        }
        else if (hasParameter(placeRequest, PARAM_PREVIOUS))
        {
            TimeUnit unit = parseTimeUnit(placeRequest.getParameter(PARAM_PREVIOUS, null));
            this.yearAndMonthOrWeek.setUnit(unit);
            if (activities != null)
            {
                populatePreviousYearAndMonthOrWeek(activities, unit);
            }
            else
            {
                logger.log(Level.WARNING, "No activities available for calculating previous "
                        + unit.name().toLowerCase() + ". Fall back to " + this.yearAndMonthOrWeek);
            }
        }
        else if (hasParameter(placeRequest, PARAM_NEXT))
        {
            TimeUnit unit = parseTimeUnit(placeRequest.getParameter(PARAM_NEXT, null));
            this.yearAndMonthOrWeek.setUnit(unit);
            if (activities != null)
            {
                populateNextYearAndMonthOrWeek(activities, unit);
            }
            else
            {
                logger.log(Level.WARNING, "No activities available for calculating next " + unit.name().toLowerCase()
                        + ". Fall back to " + this.yearAndMonthOrWeek);
            }

        }
        else if (hasParameter(placeRequest, PARAM_YEAR)
                && (hasParameter(placeRequest, PARAM_MONTH) || hasParameter(placeRequest, PARAM_WEEK)))
        {
            int year = parseInt(placeRequest.getParameter(PARAM_YEAR, "0"));
            int month = parseInt(placeRequest.getParameter(PARAM_MONTH, "0"));
            int week = parseInt(placeRequest.getParameter(PARAM_WEEK, "0"));
            this.yearAndMonthOrWeek.setYear(year);
            if (month != 0)
            {
                this.yearAndMonthOrWeek.setUnit(MONTH);
                this.yearAndMonthOrWeek.setMonthOrWeek(month);

            }
            else if (week != 0)
            {
                this.yearAndMonthOrWeek.setUnit(WEEK);
                this.yearAndMonthOrWeek.setMonthOrWeek(week);
            }
        }
        else
        {
            logger.log(Level.WARNING, "No valid parameters in " + placeRequest.getParameterNames() + ". Fall back to "
                    + this.yearAndMonthOrWeek);
        }
    }


    /**
     * For better readability
     * 
     * @param placeRequest
     * @param parameter
     * @return
     */
    private boolean hasParameter(PlaceRequest placeRequest, String parameter)
    {
        return placeRequest.getParameter(parameter, null) != null;
    }


    private TimeUnit parseTimeUnit(String value)
    {
        TimeUnit result = WEEK;
        if (value != null)
        {
            try
            {
                result = TimeUnit.valueOf(value.toUpperCase());
            }
            catch (NumberFormatException e)
            {
                logger.log(Level.WARNING, "Cannot parse \"" + value + "\" as time unit");
            }
        }
        return result;
    }


    private void populatePreviousYearAndMonthOrWeek(Activities activities, TimeUnit unit)
    {
        int newYear = activities.getYear();
        if (unit == MONTH)
        {
            int newMonth = activities.getMonth();
            newMonth--;
            if (newMonth < 1)
            {
                newMonth = 12;
                newYear--;
            }
            this.yearAndMonthOrWeek.setMonthOrWeek(newMonth);
        }
        else if (unit == WEEK)
        {
            int newWeek = activities.getWeek();
            newWeek--;
            if (newWeek < 1)
            {
                newWeek = 52;
                newYear--;
            }
            this.yearAndMonthOrWeek.setMonthOrWeek(newWeek);
        }
        this.yearAndMonthOrWeek.setYear(newYear);
    }


    private void populateNextYearAndMonthOrWeek(Activities activities, TimeUnit unit)
    {
        int newYear = activities.getYear();
        if (unit == MONTH)
        {
            int newMonth = activities.getMonth();
            newMonth++;
            if (newMonth > 12)
            {
                newMonth = 1;
                newYear++;
            }
            this.yearAndMonthOrWeek.setMonthOrWeek(newMonth);
        }
        else if (unit == WEEK)
        {
            int newWeek = activities.getWeek();
            newWeek++;
            if (newWeek > 52)
            {
                newWeek = 1;
                newYear++;
            }
            this.yearAndMonthOrWeek.setMonthOrWeek(newWeek);
        }
        this.yearAndMonthOrWeek.setYear(newYear);
    }


    private int parseInt(String value)
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


    public YearAndMonthOrWeek getYearAndMonthOrWeek()
    {
        return yearAndMonthOrWeek;
    }
}
