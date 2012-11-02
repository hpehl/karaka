package name.pehl.karaka.client.settings;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.client.dispatch.TireActionHandler;
import name.pehl.karaka.client.dispatch.TireJsonCallback;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.Settings;

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
public class GetSettingsHandler extends TireActionHandler<GetSettingsAction, GetSettingsResult>
{
    private final SettingsReader settingsReader;


    @Inject
    protected GetSettingsHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, SettingsReader settingsReader)
    {
        super(GetSettingsAction.class, securityCookieName, securityCookieAccessor);
        this.settingsReader = settingsReader;
    }


    @Override
    protected Resource resourceFor(GetSettingsAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("settings").toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetSettingsResult> resultCallback)
    {
        method.send(new TireJsonCallback<Settings, GetSettingsResult>(settingsReader, resultCallback)
        {
            @Override
            protected GetSettingsResult extractResult(JsonReader<Settings> reader, JSONObject json)
            {
                return new GetSettingsResult(settingsReader.read(json));
            }
        });
    }
}
