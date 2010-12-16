package name.pehl.tire.client.activity;

import static name.pehl.tire.client.activity.Unit.MONTH;
import static name.pehl.tire.client.activity.Unit.WEEK;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.client.NameTokens;

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
    public static final String VALUE_CURRENT = "current";

    private static Logger logger = Logger.getLogger(ActivitiesNavigationDataAdapter.class.getName());


    public ActivitiesNavigationData fromEvent(ActivitiesLoadedEvent event)
    {
        if (event != null)
        {
            Activities activities = event.getActivities();
            if (activities != null)
            {
                Unit unit = event.getUnit();
                if (unit == MONTH)
                {
                    return new ActivitiesNavigationData(activities.getYear(), activities.getMonth(), 0, unit);
                }
                else if (unit == WEEK)
                {
                    return new ActivitiesNavigationData(activities.getYear(), 0, activities.getWeek(), unit);
                }
            }
            return new ActivitiesNavigationData();
        }
        return new ActivitiesNavigationData();
    }


    public ActivitiesNavigationData fromPlaceRequest(PlaceRequest placeRequest)
    {
        if (placeRequest != null)
        {
            Set<String> names = placeRequest.getParameterNames();
            if (names.contains(PARAM_MONTH) && VALUE_CURRENT.equals(placeRequest.getParameter(PARAM_MONTH, null)))
            {
                return new ActivitiesNavigationData(0, 0, 0, MONTH);
            }
            else if (names.contains(PARAM_WEEK) && VALUE_CURRENT.equals(placeRequest.getParameter(PARAM_WEEK, null)))
            {
                return new ActivitiesNavigationData(0, 0, 0, WEEK);
            }
            else
            {
                Unit unit = Unit.WEEK;
                int year = parseInt(placeRequest.getParameter(PARAM_YEAR, "0"));
                int month = parseInt(placeRequest.getParameter(PARAM_MONTH, "0"));
                int week = parseInt(placeRequest.getParameter(PARAM_WEEK, "0"));

                if (names.contains(PARAM_YEAR) && names.contains(PARAM_MONTH) && !names.contains(PARAM_WEEK))
                {
                    unit = Unit.MONTH;
                }
                return new ActivitiesNavigationData(year, month, week, unit);
            }
        }
        return new ActivitiesNavigationData();
    }


    public PlaceRequest toPlaceRequest(ActivitiesNavigationData and)
    {
        PlaceRequest placeRequest = new PlaceRequest(NameTokens.dashboard);
        if (and != null)
        {
            if (and.getUnit() == Unit.MONTH)
            {
                if (and.getYear() > 0 && and.getMonth() > 0)
                {
                    placeRequest = placeRequest.with(PARAM_YEAR, String.valueOf(and.getYear())).with(PARAM_MONTH,
                            String.valueOf(and.getMonth()));
                }
                else
                {
                    placeRequest = placeRequest.with(PARAM_MONTH, VALUE_CURRENT);
                }
            }
            else if (and.getUnit() == Unit.WEEK)
            {
                if (and.getYear() > 0 && and.getWeek() > 0)
                {
                    placeRequest = placeRequest.with(PARAM_YEAR, String.valueOf(and.getYear())).with(PARAM_WEEK,
                            String.valueOf(and.getWeek()));
                }
                else
                {
                    placeRequest = placeRequest.with(PARAM_WEEK, VALUE_CURRENT);
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
