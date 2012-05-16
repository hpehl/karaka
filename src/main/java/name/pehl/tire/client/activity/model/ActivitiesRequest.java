package name.pehl.tire.client.activity.model;

import static name.pehl.tire.shared.model.TimeUnit.WEEK;

import java.util.logging.Level;
import java.util.logging.Logger;

import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.TimeUnit;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * TODO Tests, tests, tests
 * 
 * @author $Author$
 * @version $Revision$
 */
public class ActivitiesRequest
{
    public static final String PARAM_CURRENT = "current";
    public static final String PARAM_YEAR = "year";
    public static final String PARAM_MONTH = "month";
    public static final String PARAM_WEEK = "week";

    private static final Logger logger = Logger.getLogger(ActivitiesRequest.class.getName());

    private final String url;
    private final String text;


    public ActivitiesRequest(String url)
    {
        this.url = url;
        this.text = "n/a";
    }


    public ActivitiesRequest(PlaceRequest placeRequest)
    {
        String text = "n/a";
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities");
        if (hasParameter(placeRequest, PARAM_CURRENT))
        {
            TimeUnit unit = parseTimeUnit(placeRequest.getParameter(PARAM_CURRENT, null));
            switch (unit)
            {
                case MONTH:
                    urlBuilder = urlBuilder.path("currentMonth");
                    text = "current month";
                    break;
                case WEEK:
                    urlBuilder = urlBuilder.path("currentWeek");
                    text = "current week";
                    break;
                case DAY:
                    urlBuilder = urlBuilder.path("today");
                    text = "today";
                    break;
            }
        }
        else if (hasParameter(placeRequest, PARAM_YEAR)
                && (hasParameter(placeRequest, PARAM_MONTH) || hasParameter(placeRequest, PARAM_WEEK)))
        {
            String year = placeRequest.getParameter(PARAM_YEAR, null);
            String month = placeRequest.getParameter(PARAM_MONTH, null);
            String week = placeRequest.getParameter(PARAM_WEEK, null);
            if (month != null)
            {
                urlBuilder = urlBuilder.path(year, month);
                text = month + " / " + year;

            }
            else if (week != null)
            {
                urlBuilder = urlBuilder.path(year, "cw" + week);
                text = "CW " + week + " / " + year;
            }
        }
        else
        {
            logger.log(Level.WARNING, "No valid parameters in " + placeRequest.getParameterNames()
                    + ". Fall back to current week");
            urlBuilder = urlBuilder.path("currentWeek");
            text = "current week";
        }
        this.url = urlBuilder.toUrl();
        this.text = text;
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


    @Override
    public String toString()
    {
        return text;
    }


    public String toUrl()
    {
        return url;
    }
}
