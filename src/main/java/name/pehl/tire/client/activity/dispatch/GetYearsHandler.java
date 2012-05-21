package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.activity.model.YearsReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Years;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetYearsHandler extends TireActionHandler<Years, GetYearsAction, GetYearsResult>
{
    @Inject
    protected GetYearsHandler(@SecurityCookie String securityCookieName, SecurityCookieAccessor securityCookieAccessor,
            YearsReader yearsReader)
    {
        super(GetYearsAction.class, securityCookieName, securityCookieAccessor, yearsReader);
    }


    @Override
    protected Method getMethod(GetYearsAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", "years");
        return new Resource(urlBuilder.toUrl()).get();
    }


    @Override
    protected GetYearsResult extractResult(JSONObject jsonObject)
    {
        return new GetYearsResult(jsonReader.read(jsonObject));
    }
}
