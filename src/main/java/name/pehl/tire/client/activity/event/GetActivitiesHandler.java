package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.ActivitiesNavigator;
import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Activities;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
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
        ActivitiesNavigator activitiesNavigator = action.getActivitiesNavigator();
        if (activitiesNavigator.getUnit() == WEEK)
        {
            if (activitiesNavigator.getYear() == 0 && activitiesNavigator.getWeek() == 0)
            {
                urlBuilder = urlBuilder.path("currentWeek");
            }
            else if (activitiesNavigator.getYear() == 0 && activitiesNavigator.getWeek() != 0)
            {
                urlBuilder = urlBuilder.path("relative", "cw" + activitiesNavigator.getWeek());
            }
            else
            {
                urlBuilder = urlBuilder.path(String.valueOf(activitiesNavigator.getYear()),
                        "cw" + activitiesNavigator.getWeek());
            }
        }
        else if (activitiesNavigator.getUnit() == MONTH)
        {
            if (activitiesNavigator.getYear() == 0 && activitiesNavigator.getMonth() == 0)
            {
                urlBuilder = urlBuilder.path("currentMonth");
            }
            else if (activitiesNavigator.getYear() == 0 && activitiesNavigator.getMonth() != 0)
            {
                urlBuilder = urlBuilder.path("relative", String.valueOf(activitiesNavigator.getMonth()));
            }
            else
            {
                urlBuilder = urlBuilder.path(String.valueOf(activitiesNavigator.getYear()),
                        String.valueOf(activitiesNavigator.getMonth()));
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
