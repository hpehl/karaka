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
public class GetWeekHandler extends AbstractRestletClientActionHandler<GetWeekAction, GetWeekResult>
{
    private final WeekReader weekReader;


    @Inject
    protected GetWeekHandler(@SecurityCookie String securityCookieName, SecurityCookieAccessor securityCookieAccessor,
            WeekReader weekReader)
    {
        super(GetWeekAction.class, Method.GET, MediaType.APPLICATION_JSON, securityCookieName, securityCookieAccessor);
        this.weekReader = weekReader;
    }


    @Override
    protected String getUrl(GetWeekAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().setModule("rest").setVersion("v1").addResourcePath("activities");
        if (action.getYear() == 0 && action.getWeekNumber() == 0)
        {
            urlBuilder.addResourcePath("currentWeek");
        }
        else
        {
            urlBuilder.addResourcePath(String.valueOf(action.getYear()), "cw" + action.getWeekNumber());
        }
        return urlBuilder.toUrl();
    }


    @Override
    protected GetWeekResult extractResult(Response response) throws IOException
    {
        PiritiJsonRepresentation<Week> representation = new PiritiJsonRepresentation<Week>(weekReader,
                response.getEntity());
        return new GetWeekResult(representation.getModel());
    }
}
