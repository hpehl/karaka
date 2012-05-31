package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.dispatch.TireActionHandler;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.TextCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_TEXT;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetMinutesHandler extends TireActionHandler<GetMinutesAction, GetMinutesResult>
{
    @Inject
    protected GetMinutesHandler(@SecurityCookie String securityCookieName, SecurityCookieAccessor securityCookieAccessor)
    {
        super(GetMinutesAction.class, securityCookieName, securityCookieAccessor);
    }


    @Override
    protected Resource resourceFor(GetMinutesAction action)
    {
        return new Resource(action.getUrl());
    }


    @Override
    protected Method methodFor(GetMinutesAction action, Resource resource)
    {
        return new Method(resource, HttpMethod.GET.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_TEXT);
    }


    @Override
    protected void executeMethod(final Method method, final AsyncCallback<GetMinutesResult> resultCallback)
    {
        method.send(new TextCallback()
        {
            @Override
            public void onSuccess(Method method, String response)
            {
                long minutes = 0;
                try
                {
                    minutes = Long.parseLong(response);
                    resultCallback.onSuccess(new GetMinutesResult(minutes));
                }
                catch (NumberFormatException e)
                {
                    // TODO: handle exception
                }
            }


            @Override
            public void onFailure(Method method, Throwable exception)
            {
                resultCallback.onFailure(exception);

            }
        });
    }
}
