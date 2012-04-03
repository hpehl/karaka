package name.pehl.tire.client.activity.event;

import com.google.inject.Inject;

import name.pehl.tire.client.activity.model.Activities;
import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.rest.UrlBuilder;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

import static name.pehl.tire.shared.model.TimeUnit.MONTH;
import static name.pehl.tire.shared.model.TimeUnit.WEEK;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetActivitiesHandler extends TireActionHandler<Activities, GetActivitiesAction, GetActivitiesResult>
{
    @Inject
    protected GetActivitiesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivitiesReader activitiesReader)
    {
        super(GetActivitiesAction.class, securityCookieName, securityCookieAccessor, activitiesReader);
    }


    @Override
    protected Method getMethod(GetActivitiesAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities");
        if (action.getAnd().getUnit() == WEEK)
        {
            if (action.getAnd().getYear() == 0 && action.getAnd().getWeek() == 0)
            {
                urlBuilder = urlBuilder.path("currentWeek");
            }
            else if (action.getAnd().getYear() == 0 && action.getAnd().getWeek() != 0)
            {
                urlBuilder = urlBuilder.path("relative", "cw" + action.getAnd().getWeek());
            }
            else
            {
                urlBuilder = urlBuilder.path(String.valueOf(action.getAnd().getYear()), "cw"
                        + action.getAnd().getWeek());
            }
        }
        else if (action.getAnd().getUnit() == MONTH)
        {
            if (action.getAnd().getYear() == 0 && action.getAnd().getMonth() == 0)
            {
                urlBuilder = urlBuilder.path("currentMonth");
            }
            else if (action.getAnd().getYear() == 0 && action.getAnd().getMonth() != 0)
            {
                urlBuilder = urlBuilder.path("relative", String.valueOf(action.getAnd().getMonth()));
            }
            else
            {
                urlBuilder = urlBuilder.path(String.valueOf(action.getAnd().getYear()),
                        String.valueOf(action.getAnd().getMonth()));
            }
        }
        return new Resource(urlBuilder.toUrl()).get();
    }


    @Override
    protected GetActivitiesResult extractResult(JSONObject jsonObject)
    {
        return new GetActivitiesResult(jsonReader.read(jsonObject));
    }
}
