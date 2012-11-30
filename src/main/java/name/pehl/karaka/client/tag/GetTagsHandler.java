package name.pehl.karaka.client.tag;

import name.pehl.karaka.client.dispatch.KarakaJsonCallback;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.karaka.client.dispatch.KarakaActionHandler;
import name.pehl.karaka.client.rest.UrlBuilder;
import name.pehl.karaka.shared.model.Tag;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

public class GetTagsHandler extends KarakaActionHandler<GetTagsAction, GetTagsResult>
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
        method.send(new KarakaJsonCallback<Tag, GetTagsResult>(tagReader, resultCallback)
        {
            @Override
            protected GetTagsResult extractResult(final Method method, JsonReader<Tag> reader, JSONObject json)
            {
                return new GetTagsResult(reader.readList(json));
            }
        });
    }
}
