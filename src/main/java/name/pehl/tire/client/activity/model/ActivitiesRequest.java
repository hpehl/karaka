package name.pehl.tire.client.activity.model;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.TimeUnit;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

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


    public ActivitiesRequest(PlaceRequest request, Activities activities)
    {
        this.yearAndMonthOrWeek = new YearAndMonthOrWeek();
        Set<String> names = request.getParameterNames();
        if (names.contains(PARAM_CURRENT) || names.contains(PARAM_PREVIOUS) || names.contains(PARAM_NEXT))
        {
            String value = null;
            TimeUnit unit = WEEK;
            if (names.contains(PARAM_CURRENT))
            {
                value = request.getParameter(PARAM_CURRENT, null);
                unit = parseTimeUnit(value);
            }
            else if (names.contains(PARAM_PREVIOUS))
            {
                value = request.getParameter(PARAM_PREVIOUS, null);
                unit = parseTimeUnit(value);
                int[] previous = previous(activities, unit);
                this.yearAndMonthOrWeek.setUnit(unit);
                if (unit == MONTH)
                {
                    this.yearAndMonthOrWeek.setYear(previous[0]);
                }
                else if (unit == WEEK)
                {

                }
            }
            else if (names.contains(PARAM_NEXT))
            {
                value = request.getParameter(PARAM_NEXT, null);
                unit = parseTimeUnit(value);
                int[] next = next(activities, unit);
                this.yearAndMonthOrWeek.setUnit(unit);
                if (unit == MONTH)
                {
                    this.yearAndMonthOrWeek.setYear(next[0]);
                }
                else if (unit == WEEK)
                {

                }
            }
            this.yearAndMonthOrWeek.setUnit(unit);
        }
        else if (names.contains(PARAM_YEAR) && (names.contains(PARAM_MONTH) || names.contains(PARAM_WEEK)))
        {
            int year = parseInt(request.getParameter(PARAM_YEAR, "0"));
            int month = parseInt(request.getParameter(PARAM_MONTH, "0"));
            int week = parseInt(request.getParameter(PARAM_WEEK, "0"));
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
    }


    private int[] previous(Activities activities, TimeUnit unit)
    {
        int newYear = activities.getYear();
        int newMonth = activities.getMonth();
        int newWeek = activities.getWeek();
        if (unit == MONTH)
        {
            newMonth++;
            if (newMonth > 12)
            {
                newMonth = 1;
                newYear++;
            }
        }
        else if (unit == WEEK)
        {
            newWeek++;
            if (newWeek > 52)
            {
                newWeek = 1;
                newYear++;
            }
        }
        return new int[] {newYear, newMonth, newWeek};
    }


    public int[] next(Activities activities, TimeUnit unit)
    {
        int newYear = activities.getYear();
        int newMonth = activities.getMonth();
        int newWeek = activities.getWeek();
        if (unit == MONTH)
        {
            newMonth--;
            if (newMonth < 1)
            {
                newMonth = 12;
                newYear--;
            }
        }
        else if (unit == WEEK)
        {
            newWeek--;
            if (newWeek < 1)
            {
                newWeek = 52;
                newYear--;
            }
        }
        return new int[] {newYear, newMonth, newWeek};
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
