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
public abstract class FindNamedModelHandler<T extends NamedModel> extends
        TireActionHandler<FindNamedModelAction<T>, FindNamedModelResult<T>>
{
    private final String resource;
    private final JsonReader<T> reader;


    @SuppressWarnings({"unchecked", "rawtypes"})
    protected FindNamedModelHandler(String securityCookieName, SecurityCookieAccessor securityCookieAccessor,
            String resource, JsonReader<T> reader)
    {
        super((Class) FindNamedModelAction.class, securityCookieName, securityCookieAccessor);
        this.resource = resource;
        this.reader = reader;
    }


    @Override
    protected Resource resourceFor(FindNamedModelAction<T> action)
    {
        return new Resource(new UrlBuilder().module("rest").path(resource).query("q", action.getQuery()).toUrl());
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<FindNamedModelResult<T>> resultCallback)
    {
        method.send(new TireJsonCallback<T, FindNamedModelResult<T>>(reader, resultCallback)
        {
            @Override
            protected FindNamedModelResult<T> extractResult(JsonReader<T> reader, JSONObject json)
            {
                return new FindNamedModelResult<T>(reader.readList(json));
            }
        });
    }
}
