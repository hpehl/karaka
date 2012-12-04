package name.pehl.karaka.client.activity.dispatch;

import com.google.common.base.Splitter;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import name.pehl.karaka.client.rest.UrlBuilder;

import static name.pehl.karaka.client.logging.Logger.Category.activity;
import static name.pehl.karaka.client.logging.Logger.warn;

/**
 * @author $Author$
 * @version $Revision$
 */
public class ActivitiesRequest
{
    public static final String ACTIVITIES_PARAM = "activities";
    public static final char SEPERATOR = '-';
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
        if (hasParameter(placeRequest, ACTIVITIES_PARAM))
        {
            String path = placeRequest.getParameter(ACTIVITIES_PARAM, null);
            for (String p : Splitter.on(SEPERATOR).omitEmptyStrings().trimResults().split(path))
            {
                urlBuilder.path(p);
            }
        }
        else
        {
            warn(activity, "No valid parameter in " + placeRequest.getParameterNames() + ". Fall back to current week");
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
}
