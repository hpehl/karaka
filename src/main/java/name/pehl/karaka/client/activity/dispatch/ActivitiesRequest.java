package name.pehl.karaka.client.activity.dispatch;

import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.TimeUnit;

import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.warn;
import static name.pehl.karaka.shared.model.TimeUnit.WEEK;

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
    final String url;
    final String text;


    public ActivitiesRequest(String url)
    {
        this.url = url;
        this.text = "n/a";
    }


    public ActivitiesRequest(PlaceRequest placeRequest)
    {
        this(placeRequest, new UrlBuilder().module("rest").path("activities"));
    }


    /**
     * Extra constructor to make it testable outside GWT
     *
     * @param placeRequest
     * @param urlBuilder
     */
    protected ActivitiesRequest(PlaceRequest placeRequest, UrlBuilder urlBuilder)
    {
        String text = "n/a";
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
            warn(activity, "No valid parameters in " + placeRequest.getParameterNames() + ". Fall back to current week");
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
     *
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
            catch (IllegalArgumentException e)
            {
                warn(activity, "Cannot parse \"" + value + "\" as time unit");
            }
        }
        return result;
    }

    /**
     * Based on url
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (url == null ? 0 : url.hashCode());
        return result;
    }

    /**
     * Based on url
     *
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
        if (!(obj instanceof ActivitiesRequest))
        {
            return false;
        }
        ActivitiesRequest other = (ActivitiesRequest) obj;
        if (url == null)
        {
            if (other.url != null)
            {
                return false;
            }
        }
        else if (!url.equals(other.url))
        {
            return false;
        }
        return true;
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
