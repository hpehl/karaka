package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Activity;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class GetActiveActivityHandler extends
        TireActionHandler<Activity, GetActiveActivityAction, GetActiveActivityResult>
{
    @Inject
    protected GetActiveActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(GetActiveActivityAction.class, securityCookieName, securityCookieAccessor, activityReader);
    }


    @Override
    protected Method getMethod(GetActiveActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", "active");
        return new Resource(urlBuilder.toUrl()).get();
    }


    @Override
    protected GetActiveActivityResult extractResult(JSONObject jsonObject)
    {
        return null;
    }
}
