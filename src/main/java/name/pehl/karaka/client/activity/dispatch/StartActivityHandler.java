package name.pehl.karaka.client.activity.dispatch;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;
import name.pehl.karaka.client.activity.model.ActivityReader;
import name.pehl.karaka.client.activity.model.ActivityWriter;
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
public class StartActivityHandler extends KarakaActionHandler<StartActivityAction, StartActivityResult>
{
    private final ActivityReader activityReader;
    private final ActivityWriter activityWriter;


    @Inject
    protected StartActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader, ActivityWriter activityWriter)
    {
        super(StartActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
        this.activityWriter = activityWriter;
    }

    @Override
    protected Resource resourceFor(StartActivityAction action)
    {
        Activity activity = action.getActivity();
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities");
        if (activity.isTransient())
        {
            urlBuilder.path("start");
        }
        else
        {
            urlBuilder = urlBuilder.path(activity.getId(), "start");
        }
        return new Resource(urlBuilder.toUrl());
    }

    @Override
    protected Method methodFor(StartActivityAction action, Resource resource)
    {
        Activity activity = action.getActivity();
        Method method = new Method(resource, PUT.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        if (action.getActivity().isTransient())
        {
            method = method.text(activityWriter.toJson(activity));
        }
        return method;
    }

    @Override
    protected void executeMethod(final Method method, final AsyncCallback<StartActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, StartActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected StartActivityResult extractResult(final JsonReader<Activity> reader, final JSONObject json)
            {
                return new StartActivityResult(new HashSet<Activity>(reader.readList(json)));
            }
        });
    }
}
