package name.pehl.tire.client.activity.model;

import static name.pehl.tire.model.TimeUnit.MONTH;
import static name.pehl.tire.model.TimeUnit.WEEK;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;
import name.pehl.tire.client.activity.event.ActivitiesLoadedEvent;
import name.pehl.tire.model.TimeUnit;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public class ActivitiesNavigationDataAdapter
{
    public static final String PARAM_YEAR = "year";
    public static final String PARAM_MONTH = "month";
    public static final String PARAM_WEEK = "week";
    public static final String PARAM_OFFSET = "offset";
    public static final String VALUE_CURRENT = "current";
    public static final String VALUE_RELATIVE = "relative";

    private static Logger logger = Logger.getLogger(ActivitiesNavigationDataAdapter.class.getName());


    public ActivitiesNavigationData fromEvent(ActivitiesLoadedEvent event)
    {
        if (event != null)
        {
            Activities activities = event.getActivities();
            if (activities != null)
            {
                TimeUnit unit = event.getUnit();
                if (unit == MONTH)
                {
                    return new ActivitiesNavigationData(activities.getYear(), activities.getMonth(), 0, unit);
                }
                else if (unit == WEEK)
                {
                    return new ActivitiesNavigationData(activities.getYear(), 0, activities.getWeek(), unit);
                }
            }
            return new ActivitiesNavigationData(activities.getUnit());
        }
        return new ActivitiesNavigationData();
    }


    public ActivitiesNavigationData fromPlaceRequest(PlaceRequest placeRequest)
    {
        if (placeRequest != null)
        {
            Set<String> names = placeRequest.getParameterNames();
            String year = placeRequest.getParameter(PARAM_YEAR, "0");
            String month = placeRequest.getParameter(PARAM_MONTH, "0");
            String week = placeRequest.getParameter(PARAM_WEEK, "0");

            if (names.contains(PARAM_MONTH) && VALUE_CURRENT.equals(month))
            {
                return new ActivitiesNavigationData(MONTH);
            }
            else if (names.contains(PARAM_MONTH) && VALUE_RELATIVE.equals(month))
            {
                int offsetValue = parseInt(placeRequest.getParameter(PARAM_OFFSET, "0"));
                return new ActivitiesNavigationData(0, offsetValue, 0, MONTH);
            }
            else if (names.contains(PARAM_WEEK) && VALUE_CURRENT.equals(week))
            {
                return new ActivitiesNavigationData(WEEK);
            }
            else if (names.contains(PARAM_WEEK) && VALUE_RELATIVE.equals(week))
            {
                int offsetValue = parseInt(placeRequest.getParameter(PARAM_OFFSET, "0"));
                return new ActivitiesNavigationData(0, 0, offsetValue, WEEK);
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
                return new ActivitiesNavigationData(yearValue, monthValue, weekValue, unit);
            }
        }
        return new ActivitiesNavigationData(TimeUnit.WEEK);
    }


    public PlaceRequest toPlaceRequest(ActivitiesNavigationData and)
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard);
        if (and != null)
        {
            if (and.getUnit() == TimeUnit.MONTH)
            {
                if (and.getYear() == 0 && and.getMonth() == 0)
                {
                    placeRequest = placeRequest.with(PARAM_MONTH, VALUE_CURRENT);
                }
                else if (and.getYear() == 0 && and.getMonth() != 0)
                {
                    placeRequest = placeRequest.with(PARAM_MONTH, VALUE_RELATIVE).with(PARAM_OFFSET,
                            String.valueOf(and.getMonth()));
                }
                else
                {
                    placeRequest = placeRequest.with(PARAM_YEAR, String.valueOf(and.getYear())).with(PARAM_MONTH,
                            String.valueOf(and.getMonth()));
                }
            }
            else if (and.getUnit() == TimeUnit.WEEK)
            {
                if (and.getYear() == 0 && and.getWeek() == 0)
                {
                    placeRequest = placeRequest.with(PARAM_WEEK, VALUE_CURRENT);
                }
                else if (and.getYear() == 0 && and.getWeek() != 0)
                {
                    placeRequest = placeRequest.with(PARAM_WEEK, VALUE_RELATIVE).with(PARAM_OFFSET,
                            String.valueOf(and.getWeek()));
                }
                else
                {
                    placeRequest = placeRequest.with(PARAM_YEAR, String.valueOf(and.getYear())).with(PARAM_WEEK,
                            String.valueOf(and.getWeek()));
                }
            }
        }
        return placeRequest;
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
}
