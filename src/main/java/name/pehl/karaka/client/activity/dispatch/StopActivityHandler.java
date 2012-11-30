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

import static name.pehl.karaka.client.dispatch.KarakaActionHandler.HttpMethod.PUT;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class StopActivityHandler extends KarakaActionHandler<StopActivityAction, StopActivityResult>
{
    private final ActivityReader activityReader;

    @Inject
    protected StopActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(StopActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
    }


    @Override
    protected Resource resourceFor(StopActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", action.getActivity().getId(), "stop");
        return new Resource(urlBuilder.toUrl());
    }


    @Override
    protected Method methodFor(StopActivityAction action, Resource resource)
    {
        return new Method(resource, PUT.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<StopActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, StopActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected StopActivityResult extractResult(final Method method, final JsonReader<Activity> reader, final JSONObject json)
            {
                return new StopActivityResult(reader.read(json));
            }
        });
    }
}
