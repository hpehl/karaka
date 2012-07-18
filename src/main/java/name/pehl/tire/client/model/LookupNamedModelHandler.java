package name.pehl.tire.client.model;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.NamedModel;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class LookupNamedModelHandler<T extends NamedModel> extends
        TireActionHandler<LookupNamedModelAction<T>, LookupNamedModelResult<T>>
{
    final String resource;
    final JsonReader<T> reader;


    @SuppressWarnings({"unchecked", "rawtypes"})
    protected LookupNamedModelHandler(String securityCookieName, SecurityCookieAccessor securityCookieAccessor,
            String resource, JsonReader<T> reader)
    {
        super((Class) LookupNamedModelAction.class, securityCookieName, securityCookieAccessor);
        this.resource = resource;
        this.reader = reader;
    }


    @Override
    protected Resource resourceFor(LookupNamedModelAction<T> action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path(resource);
        if (action.getQuery() != null)
        {
            urlBuilder = urlBuilder.query("q", action.getQuery());
        }
        return new Resource(urlBuilder.toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<LookupNamedModelResult<T>> resultCallback)
    {
        method.send(new TireJsonCallback<T, LookupNamedModelResult<T>>(reader, resultCallback)
        {
            @Override
            protected LookupNamedModelResult<T> extractResult(JsonReader<T> reader, JSONObject json)
            {
                return new LookupNamedModelResult<T>(reader.readList(json));
            }
        });
    }
}
