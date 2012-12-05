package name.pehl.karaka.client.activity.dispatch;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;
import name.pehl.karaka.client.activity.model.ActivityReader;
import name.pehl.karaka.client.dispatch.KarakaActionHandler;
import name.pehl.karaka.client.dispatch.KarakaJsonCallback;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.Activity;
import name.pehl.piriti.json.client.JsonReader;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

public class GetLatestActivityHandler extends KarakaActionHandler<GetLatestActivityAction, GetLatestActivityResult>
{
    private final ActivityReader activityReader;


    @Inject
    protected GetLatestActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(GetLatestActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
    }


    @Override
    protected Resource resourceFor(GetLatestActivityAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("activities", "latest").toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetLatestActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, GetLatestActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected GetLatestActivityResult extractResult(final Method method, JsonReader<Activity> reader, JSONObject json)
            {
                return new GetLatestActivityResult(reader.read(json));
            }
        });
    }
}
