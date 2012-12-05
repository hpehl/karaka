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

import static name.pehl.karaka.client.dispatch.KarakaActionHandler.HttpMethod.POST;
import static name.pehl.karaka.client.dispatch.KarakaActionHandler.HttpMethod.PUT;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class SaveActivityHandler extends KarakaActionHandler<SaveActivityAction, SaveActivityResult>
{
    private final ActivityReader activityReader;
    private final ActivityWriter activityWriter;


    @Inject
    protected SaveActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader,
            final ActivityWriter activityWriter)
    {
        super(SaveActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
        this.activityWriter = activityWriter;
    }

    @Override
    protected Resource resourceFor(SaveActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities");
        if (!action.getActivity().isTransient())
        {
            urlBuilder = urlBuilder.path(action.getActivity().getId());
        }
        return new Resource(urlBuilder.toUrl());
    }

    @Override
    protected Method methodFor(SaveActivityAction action, Resource resource)
    {
        Method method;
        if (action.getActivity().isTransient())
        {
            method = new Method(resource, POST.name());
        }
        else
        {
            method = new Method(resource, PUT.name());
        }
        method = method.header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON).text(activityWriter.toJson(action.getActivity()));
        return method;
    }

    @Override
    protected void executeMethod(final Method method, final AsyncCallback<SaveActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, SaveActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected SaveActivityResult extractResult(final Method method, JsonReader<Activity> reader, JSONObject json)
            {
                return new SaveActivityResult(reader.read(json));
            }
        });
    }
}
