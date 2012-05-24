package name.pehl.tire.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Activity;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

import static name.pehl.tire.client.dispatch.TireActionHandler.HttpMethod.GET;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;

public class GetRunningActivityHandler extends TireActionHandler<GetRunningActivityAction, GetRunningActivityResult>
{
    private final ActivityReader activityReader;


    @Inject
    protected GetRunningActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(GetRunningActivityAction.class, GET, CONTENT_TYPE_JSON, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
    }


    @Override
    protected Resource resourceFor(GetRunningActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", "running");
        return new Resource(urlBuilder.toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetRunningActivityResult> resultCallback)
    {
        method.send(new TireJsonCallback<Activity, GetRunningActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected GetRunningActivityResult extractResult(JsonReader<Activity> reader, JSONObject json)
            {
                return new GetRunningActivityResult(reader.read(json));
            }
        });
    }
}
