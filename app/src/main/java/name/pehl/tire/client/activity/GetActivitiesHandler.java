package name.pehl.tire.client.activity;

import java.io.IOException;

import name.pehl.piriti.restlet.client.UrlBuilder;
import name.pehl.piriti.restlet.client.json.PiritiJsonRepresentation;
import name.pehl.tire.client.dispatch.AbstractRestletClientActionHandler;

import org.restlet.client.Response;
import org.restlet.client.data.MediaType;
import org.restlet.client.data.Method;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.SecurityCookieAccessor;
import com.gwtplatform.dispatch.shared.SecurityCookie;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetActivitiesHandler extends
        AbstractRestletClientActionHandler<Activities, GetActivitiesAction, GetActivitiesResult>
{
    @Inject
    protected GetActivitiesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivitiesReader activitiesReader)
    {
        super(GetActivitiesAction.class, Method.GET, MediaType.APPLICATION_JSON, securityCookieName,
                securityCookieAccessor, activitiesReader);
    }


    @Override
    protected String getUrl(GetActivitiesAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().setModule("rest").setVersion("v1").addResourcePath("activities");
        if (action.getAnd().getYear() == 0 && action.getAnd().getWeek() == 0)
        {
            urlBuilder.addResourcePath("currentWeek");
        }
        else
        {
            urlBuilder.addResourcePath(String.valueOf(action.getAnd().getYear()), "cw" + action.getAnd().getWeek());
        }
        return urlBuilder.toUrl();
    }


    @Override
    protected GetActivitiesResult extractResult(Response response, PiritiJsonRepresentation<Activities> representation)
            throws IOException
    {
        return new GetActivitiesResult(representation.getModel());
    }
}
