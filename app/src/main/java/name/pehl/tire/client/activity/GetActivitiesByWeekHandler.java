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
public class GetActivitiesByWeekHandler extends
        AbstractRestletClientActionHandler<GetActivitiesByWeekAction, GetActivitiesByWeekResult>
{
    @Inject
    protected GetActivitiesByWeekHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor)
    {
        super(GetActivitiesByWeekAction.class, Method.GET, MediaType.APPLICATION_JSON, securityCookieName,
                securityCookieAccessor);
    }


    @Override
    protected String getUrl(GetActivitiesByWeekAction action)
    {
        String url = new UrlBuilder().setModule("rest").setVersion("v1")
                .addResourcePath("activities", "cw" + action.getCw()).toUrl();
        return url;
    }


    @Override
    protected GetActivitiesByWeekResult extractResult(Response response) throws IOException
    {
        PiritiJsonRepresentation<Week> representation = new PiritiJsonRepresentation<Week>(Week.JSON_READER,
                response.getEntity());
        return new GetActivitiesByWeekResult(representation.getModel());
    }
}
