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
public class CopyActivityHandler extends KarakaActionHandler<CopyActivityAction, CopyActivityResult>
{
    private final ActivityReader activityReader;


    @Inject
    protected CopyActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivityReader activityReader)
    {
        super(CopyActivityAction.class, securityCookieName, securityCookieAccessor);
        this.activityReader = activityReader;
    }

    @Override
    protected Resource resourceFor(CopyActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest")
                .path("activities", action.getActivity().getId(), "copy", action.getPeriod());
        return new Resource(urlBuilder.toUrl());
    }

    @Override
    protected Method methodFor(CopyActivityAction action, Resource resource)
    {
        return new Method(resource, PUT.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    }

    @Override
    protected void executeMethod(final Method method, final AsyncCallback<CopyActivityResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Activity, CopyActivityResult>(activityReader, resultCallback)
        {
            @Override
            protected CopyActivityResult extractResult(final Method method, final JsonReader<Activity> reader, final JSONObject json)
            {
                return new CopyActivityResult(reader.read(json));
            }
        });
    }
}
