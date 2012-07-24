package name.pehl.tire.client.tag;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.tire.client.dispatch.TireActionHandler;
import name.pehl.tire.client.dispatch.TireJsonCallback;
import name.pehl.tire.client.rest.UrlBuilder;
import name.pehl.tire.shared.model.Tag;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class GetTagsHandler extends TireActionHandler<GetTagsAction, GetTagsResult>
{
    final TagReader tagReader;


    @Inject
    protected GetTagsHandler(@SecurityCookie String securityCookieName, SecurityCookieAccessor securityCookieAccessor,
            TagReader tagReader)
    {
        super(GetTagsAction.class, securityCookieName, securityCookieAccessor);
        this.tagReader = tagReader;
    }


    @Override
    protected Resource resourceFor(GetTagsAction action)
    {
        return new Resource(new UrlBuilder().module("rest").path("tags").toUrl());
    }


    @Override
    protected void executeMethod(Method method, AsyncCallback<GetTagsResult> resultCallback)
    {
        method.send(new TireJsonCallback<Tag, GetTagsResult>(tagReader, resultCallback)
        {
            @Override
            protected GetTagsResult extractResult(JsonReader<Tag> reader, JSONObject json)
            {
                return new GetTagsResult(reader.readList(json));
            }
        });
    }
}
