package name.pehl.tire.client.activity.event;

import static name.pehl.tire.model.TimeUnit.MONTH;
import static name.pehl.tire.model.TimeUnit.WEEK;

import java.io.IOException;

import name.pehl.piriti.restlet.client.UrlBuilder;
import name.pehl.piriti.restlet.client.json.PiritiJsonRepresentation;
import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesReader;
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
        if (action.getAnd().getUnit() == WEEK)
        {
            if (action.getAnd().getYear() == 0 && action.getAnd().getWeek() == 0)
            {
                urlBuilder.addResourcePath("currentWeek");
            }
            else if (action.getAnd().getYear() == 0 && action.getAnd().getWeek() != 0)
            {
                urlBuilder.addResourcePath("relative", "cw" + action.getAnd().getWeek());
            }
            else
            {
                urlBuilder.addResourcePath(String.valueOf(action.getAnd().getYear()), "cw" + action.getAnd().getWeek());
            }
        }
        else if (action.getAnd().getUnit() == MONTH)
        {
            if (action.getAnd().getYear() == 0 && action.getAnd().getMonth() == 0)
            {
                urlBuilder.addResourcePath("currentMonth");
            }
            else if (action.getAnd().getYear() == 0 && action.getAnd().getMonth() != 0)
            {
                urlBuilder.addResourcePath("relative", String.valueOf(action.getAnd().getMonth()));
            }
            else
            {
                urlBuilder.addResourcePath(String.valueOf(action.getAnd().getYear()),
                        String.valueOf(action.getAnd().getMonth()));
            }
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
