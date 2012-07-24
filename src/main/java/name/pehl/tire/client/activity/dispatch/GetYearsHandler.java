package name.pehl.tire.client.activity.dispatch;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.activity.model.YearsReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Years;

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
public class GetYearsHandler extends TireActionHandler<GetYearsAction, GetYearsResult>
{
    final YearsReader yearsReader;


    @Inject
    protected GetYearsHandler(@SecurityCookie String securityCookieName, SecurityCookieAccessor securityCookieAccessor,
            YearsReader yearsReader)
    {
        super(GetYearsAction.class, securityCookieName, securityCookieAccessor);
        this.yearsReader = yearsReader;
    }


    @Override
    protected Resource resourceFor(GetYearsAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("activities", "years").toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetYearsResult> resultCallback)
    {
        method.send(new TireJsonCallback<Years, GetYearsResult>(yearsReader, resultCallback)
        {
            @Override
            protected GetYearsResult extractResult(JsonReader<Years> reader, JSONObject json)
            {
                return new GetYearsResult(reader.read(json));
            }
        });
    }
}
