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

import java.util.HashSet;

import static name.pehl.karaka.client.dispatch.KarakaActionHandler.HttpMethod.PUT;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class TickActivityHandler extends KarakaActionHandler<TickActivityAction, TickActivityResult>
{
    private final ActivityReader activityReader;

    @Inject
    protected TickActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(TickActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
    }


    @Override
    protected Resource resourceFor(TickActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", action.getActivity().getId(), "tick");
        return new Resource(urlBuilder.toUrl());
    }


    @Override
    protected Method methodFor(TickActivityAction action, Resource resource)
    {
        return new Method(resource, PUT.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<TickActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, TickActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected TickActivityResult extractResult(final JsonReader<Activity> reader, final JSONObject json)
            {
                return new TickActivityResult(new HashSet<Activity>(reader.readList(json)));
            }
        });
    }
}
