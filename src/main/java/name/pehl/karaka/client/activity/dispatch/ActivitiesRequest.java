package name.pehl.karaka.client.activity.dispatch;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import name.pehl.karaka.client.rest.UrlBuilder;

import static name.pehl.karaka.client.NameTokens.dashboard;
import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.warn;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ActivitiesRequest
{
    public static final String PATH_PARAM = "path";
    public static final String ACTIVITIES = "/activities/";
    public static final char URL_SEPERATOR = '/';
    public static final char PLACE_REQUEST_SEPERATOR = '-';
    final String url;


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
        if (hasParameter(placeRequest, PATH_PARAM))
        {
            String path = placeRequest.getParameter(PATH_PARAM, null);
            for (String p : Splitter.on(PLACE_REQUEST_SEPERATOR).omitEmptyStrings().trimResults().split(path))
            {
                urlBuilder.path(p);
            }
        }
        else
        {
            warn(activity, "No valid path in " + placeRequest.getParameterNames() + ". Fall back to current week");
            urlBuilder = urlBuilder.path("currentWeek");
        }
        this.url = urlBuilder.toUrl();
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

    public String toUrl()
    {
        return url;
    }


    // ------------------------------------------------------ static helper methods

    /**
     * Returns a place request for the dashboard recognicing the following service urls:
     * <ul>
     * <li>/placeRequestFor/currentWeek: Find placeRequestFor</li>
     * <li>/placeRequestFor/currentMonth: Find placeRequestFor of the current month</li>
     * <li>/placeRequestFor/{year}/cw{week}: Find placeRequestFor by year and week</li>
     * <li>/placeRequestFor/relative/cw{week}: Find placeRequestFor by  week relative to the current week</li>
     * <li>/placeRequestFor/{year}/{month}: Find placeRequestFor by year and month</li>
     * <li>/placeRequestFor/relative/{month}: Find placeRequestFor by month relative to the current month</li>
     * </ul>
     *
     * @param url
     *
     * @return
     */
    public static PlaceRequest placeRequestFor(String url)
    {
        PlaceRequest placeRequest = new PlaceRequest(dashboard);

        if (Strings.emptyToNull(url) != null)
        {
            String path = extractPath(url, ACTIVITIES);
            path = CharMatcher.is(URL_SEPERATOR).replaceFrom(path, PLACE_REQUEST_SEPERATOR);
            placeRequest = placeRequest.with(PATH_PARAM, path);
        }
        return placeRequest;
    }

    private static String extractPath(String url, String after)
    {
        String path = url;
        int start = url.indexOf(after);
        if (start != -1)
        {
            path = url.substring(start + after.length());
        }
        int end = path.indexOf("?");
        if (end != -1)
        {
            path = path.substring(0, end);
        }
        return path;
    }
}
