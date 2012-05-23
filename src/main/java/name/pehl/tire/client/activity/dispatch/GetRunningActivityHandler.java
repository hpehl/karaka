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

public class GetRunningActivityHandler extends
        TireActionHandler<Activity, GetRunningActivityAction, GetRunningActivityResult>
{
    @Inject
    protected GetRunningActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(GetRunningActivityAction.class, securityCookieName, securityCookieAccessor, activityReader);
    }


    @Override
    protected Method getMethod(GetRunningActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", "running");
        return new Resource(urlBuilder.toUrl()).get();
    }


    @Override
    protected GetRunningActivityResult extractResult(JSONObject jsonObject)
    {
        return null;
    }
}
