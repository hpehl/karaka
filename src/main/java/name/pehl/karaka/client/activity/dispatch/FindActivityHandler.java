package name.pehl.karaka.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.client.activity.model.ActivityReader;
import name.pehl.karaka.client.dispatch.KarakaActionHandler;
import name.pehl.karaka.client.dispatch.KarakaJsonCallback;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.Activity;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class FindActivityHandler extends KarakaActionHandler<FindActivityAction, FindActivityResult>
{
    final ActivityReader activityReader;


    @Inject
    public FindActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(FindActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
    }


    @Override
    protected Resource resourceFor(FindActivityAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("activities").query("q", action.getQuery()).toUrl());
    }


    @Override
    protected void executeMethod(Method method, AsyncCallback<FindActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, FindActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected FindActivityResult extractResult(final Method method, JsonReader<Activity> reader, JSONObject json)
            {
                return new FindActivityResult(reader.readList(json));
            }
        });
    }
}
