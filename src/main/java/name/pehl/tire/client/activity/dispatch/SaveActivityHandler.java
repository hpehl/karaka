package name.pehl.tire.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.activity.model.ActivityReader;
import name.pehl.tire.client.activity.model.ActivityWriter;
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

import static name.pehl.tire.client.dispatch.TireActionHandler.HttpMethod.POST;
import static name.pehl.tire.client.dispatch.TireActionHandler.HttpMethod.PUT;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class SaveActivityHandler extends TireActionHandler<SaveActivityAction, SaveActivityResult>
{
    private final ActivityReader activityReader;
    private final ActivityWriter activityWriter;


    @Inject
    protected SaveActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader, ActivityWriter activityWriter)
    {
        super(SaveActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
        this.activityWriter = activityWriter;
    }


    @Override
    protected Resource resourceFor(SaveActivityAction action)
    {
        UrlBuilder urlBuilder = null;
        Activity newOrModifiedActivity = action.getNewOrModifiedActivity();
        if (newOrModifiedActivity.isTransient())
        {
            urlBuilder = new UrlBuilder().module("rest").path("activities");
        }
        else
        {
            urlBuilder = new UrlBuilder().module("rest").path("activities", newOrModifiedActivity.getId());
        }
        return new Resource(urlBuilder.toUrl());
    }


    @Override
    protected Method methodFor(SaveActivityAction action, Resource resource)
    {
        Method method = null;
        Activity newOrModifiedActivity = action.getNewOrModifiedActivity();
        if (newOrModifiedActivity.isTransient())
        {
            method = new Method(resource, POST.name());
        }
        else
        {
            method = new Method(resource, PUT.name());
        }
        return method.header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON).text(activityWriter.toJson(newOrModifiedActivity));
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<SaveActivityResult> resultCallback)
    {
        method.send(new TireJsonCallback<Activity, SaveActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected SaveActivityResult extractResult(JsonReader<Activity> reader, JSONObject json)
            {
                return new SaveActivityResult(reader.read(json));
            }
        });
    }
}
