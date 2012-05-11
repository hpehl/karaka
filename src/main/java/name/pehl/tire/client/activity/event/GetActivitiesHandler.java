package name.pehl.tire.client.activity.event;

import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Activities;
import name.pehl.tire.shared.model.YearAndMonthOrWeek;

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
        YearAndMonthOrWeek yearAndMonthOrWeek = action.getActivitiesRequest().getYearAndMonthOrWeek();
        if (yearAndMonthOrWeek.getUnit() == WEEK)
        {
            if (yearAndMonthOrWeek.getYear() == 0 && yearAndMonthOrWeek.getMonthOrWeek() == 0)
            {
                urlBuilder = urlBuilder.path("currentWeek");
            }
            else
            {
                urlBuilder = urlBuilder.path(String.valueOf(yearAndMonthOrWeek.getYear()),
                        "cw" + yearAndMonthOrWeek.getMonthOrWeek());
            }
        }
        else if (yearAndMonthOrWeek.getUnit() == MONTH)
        {
            if (yearAndMonthOrWeek.getYear() == 0 && yearAndMonthOrWeek.getMonthOrWeek() == 0)
            {
                urlBuilder = urlBuilder.path("currentMonth");
            }
            else
            {
                urlBuilder = urlBuilder.path(String.valueOf(yearAndMonthOrWeek.getYear()),
                        String.valueOf(yearAndMonthOrWeek.getMonthOrWeek()));
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
