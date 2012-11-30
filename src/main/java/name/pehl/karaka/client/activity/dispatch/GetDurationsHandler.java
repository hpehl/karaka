package name.pehl.karaka.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.client.activity.model.DurationsReader;
import name.pehl.karaka.client.dispatch.KarakaActionHandler;
import name.pehl.karaka.client.dispatch.KarakaJsonCallback;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.Durations;

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
public class GetDurationsHandler extends KarakaActionHandler<GetDurationsAction, GetDurationsResult>
{
    private final DurationsReader minutesReader;


    @Inject
    protected GetDurationsHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, DurationsReader minutesReader)
    {
        super(GetDurationsAction.class, securityCookieName, securityCookieAccessor);
        this.minutesReader = minutesReader;
    }


    @Override
    protected Resource resourceFor(GetDurationsAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("activities", "current", "durations").toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetDurationsResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Durations, GetDurationsResult>(minutesReader, resultCallback)
        {
            @Override
            protected GetDurationsResult extractResult(final Method method, JsonReader<Durations> reader, JSONObject json)
            {
                Durations durations = reader.read(json);
                durations.setLinks(readLinks(method));
                return new GetDurationsResult(durations);
            }
        });
    }
}
