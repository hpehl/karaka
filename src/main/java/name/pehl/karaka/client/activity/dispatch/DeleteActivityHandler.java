package name.pehl.karaka.client.activity.dispatch;

import name.pehl.karaka.client.dispatch.TireActionHandler;
import name.pehl.karaka.client.rest.UrlBuilder;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

import static name.pehl.karaka.client.dispatch.TireActionHandler.HttpMethod.DELETE;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class DeleteActivityHandler extends TireActionHandler<DeleteActivityAction, DeleteActivityResult>
{
    @Inject
    protected DeleteActivityHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor)
    {
        super(DeleteActivityAction.class, securityCookieName, securityCookieAccessor);
    }


    @Override
    protected Resource resourceFor(DeleteActivityAction action)
    {
        UrlBuilder urlBuilder = new UrlBuilder().module("rest").path("activities", action.getActivity().getId());
        return new Resource(urlBuilder.toUrl());
    }


    @Override
    protected Method methodFor(DeleteActivityAction action, Resource resource)
    {
        return new Method(resource, DELETE.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<DeleteActivityResult> resultCallback)
    {
        try
        {
            method.send(new RequestCallback()
            {
                @Override
                public void onResponseReceived(Request request, Response response)
                {
                    resultCallback.onSuccess(new DeleteActivityResult());
                }


                @Override
                public void onError(Request request, Throwable exception)
                {
                    resultCallback.onFailure(exception);
                }
            });
        }
        catch (RequestException e)
        {
            resultCallback.onFailure(e);
        }
    }
}
