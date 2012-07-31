package name.pehl.tire.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.activity.model.MinutesReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Durations;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetMinutesHandler extends TireActionHandler<GetMinutesAction, GetMinutesResult>
{
    private final MinutesReader minutesReader;


    @Inject
    protected GetMinutesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, MinutesReader minutesReader)
    {
        super(GetMinutesAction.class, securityCookieName, securityCookieAccessor);
        this.minutesReader = minutesReader;
    }


    @Override
    protected Resource resourceFor(GetMinutesAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("activities", "current", "durations").toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetMinutesResult> resultCallback)
    {
        method.send(new TireJsonCallback<Durations, GetMinutesResult>(minutesReader, resultCallback)
        {
            @Override
            protected GetMinutesResult extractResult(JsonReader<Durations> reader, JSONObject json)
            {
                return new GetMinutesResult(reader.read(json));
            }
        });
    }
}
