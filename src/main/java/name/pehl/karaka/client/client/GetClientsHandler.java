package name.pehl.karaka.client.client;

import name.pehl.karaka.client.dispatch.KarakaActionHandler;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.client.dispatch.KarakaJsonCallback;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.Client;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class GetClientsHandler extends KarakaActionHandler<GetClientsAction, GetClientsResult>
{
    final ClientReader clientReader;


    @Inject
    protected GetClientsHandler(@SecurityCookie final String securityCookieName,
            final SecurityCookieAccessor securityCookieAccessor, final ClientReader projectReader)
    {
        super(GetClientsAction.class, securityCookieName, securityCookieAccessor);
        this.clientReader = projectReader;
    }


    @Override
    protected Resource resourceFor(final GetClientsAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("clients").toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetClientsResult> resultCallback)
    {
        method.send(new KarakaJsonCallback<Client, GetClientsResult>(clientReader, resultCallback)
        {
            @Override
            protected GetClientsResult extractResult(final Method method, final JsonReader<Client> reader, final JSONObject json)
            {
                return new GetClientsResult(reader.readList(json));
            }
        });
    }
}
