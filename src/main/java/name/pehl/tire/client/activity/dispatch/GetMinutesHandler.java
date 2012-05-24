package name.pehl.tire.client.activity.dispatch;

import name.pehl.tire.client.activity.model.ActivitiesReader;
import name.pehl.tire.client.dispatch.DispatchRequestRestletImpl;
import name.pehl.tire.client.dispatch.TireActionHandler;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.TextCallback;
import org.jboss.weld.exceptions.UnsupportedOperationException;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.gwtplatform.dispatch.shared.SecurityCookie;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class GetMinutesHandler extends TireActionHandler<Void, GetMinutesAction, GetMinutesResult>
{
    @Inject
    protected GetMinutesHandler(@SecurityCookie String securityCookieName,
            SecurityCookieAccessor securityCookieAccessor, ActivitiesReader activitiesReader)
    {
        super(GetMinutesAction.class, securityCookieName, securityCookieAccessor, null);
    }


    @Override
    public DispatchRequest execute(final GetMinutesAction action, final AsyncCallback<GetMinutesResult> resultCallback,
            final ExecuteCommand<GetMinutesAction, GetMinutesResult> executeCommand)
    {
        Method method = getMethod(action).header(Resource.HEADER_CONTENT_TYPE, Resource.CONTENT_TYPE_TEXT);
        if (action.isSecured())
        {
            // Add the security token as header
            String cookieContent = securityCookieAccessor.getCookieContent();
            if (cookieContent != null)
            {
                method = method.header(securityCookieName, cookieContent);
            }
        }
        method.text(securityCookieName).send(new TextCallback()
        {
            @Override
            public void onSuccess(Method method, String response)
            {
                int minutes = 0;
                try
                {
                    minutes = Integer.parseInt(response);
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
        return new DispatchRequestRestletImpl(method);
    }


    @Override
    protected Method getMethod(GetMinutesAction action)
    {
        return new Resource(action.getActivitiesRequest().toUrl()).get();
    }


    @Override
    protected GetMinutesResult extractResult(JSONObject jsonObject)
    {
        throw new UnsupportedOperationException();
    }
}
