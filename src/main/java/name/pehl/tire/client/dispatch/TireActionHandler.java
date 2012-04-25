package name.pehl.tire.client.dispatch;

import name.pehl.piriti.json.client.JsonReader;

import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.ResponseFormatException;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.AbstractClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.dispatch.client.actionhandler.UndoCommand;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * Implementation of the command pattern using Restlet instead of GWT-RPC.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class TireActionHandler<T, A extends Action<R>, R extends Result> extends
        AbstractClientActionHandler<A, R>
{
    protected final String securityCookieName;
    protected final SecurityCookieAccessor securityCookieAccessor;
    protected final JsonReader<T> jsonReader;


    protected TireActionHandler(final Class<A> actionType, final String securityCookieName,
            final SecurityCookieAccessor securityCookieAccessor, final JsonReader<T> jsonReader)
    {
        super(actionType);
        this.securityCookieName = securityCookieName;
        this.securityCookieAccessor = securityCookieAccessor;
        this.jsonReader = jsonReader;
    }


    @Override
    public DispatchRequest execute(final A action, final AsyncCallback<R> resultCallback,
            final ExecuteCommand<A, R> executeCommand)
    {
        Method method = getMethod(action).header(Resource.HEADER_CONTENT_TYPE, Resource.CONTENT_TYPE_JSON);
        if (action.isSecured())
        {
            // Add the security token as header
            String cookieContent = securityCookieAccessor.getCookieContent();
            if (cookieContent != null)
            {
                method = method.header(securityCookieName, cookieContent);
            }
        }
        method.text(securityCookieName).send(new JsonCallback()
        {
            @Override
            public void onSuccess(Method method, JSONValue response)
            {
                JSONObject jsonObject = response.isObject();
                if (jsonObject != null)
                {
                    resultCallback.onSuccess(extractResult(jsonObject));
                }
                else
                {
                    resultCallback.onFailure(new ResponseFormatException("Response was NOT a valid JSON object"));
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


    protected abstract Method getMethod(A action);


    protected abstract R extractResult(JSONObject jsonObject);


    /**
     * Not supported yet.
     * 
     * @throws UnsupportedOperationException
     *             if called
     */
    @Override
    public DispatchRequest undo(final A action, final R result, final AsyncCallback<Void> callback,
            final UndoCommand<A, R> undoCommand)
    {
        throw new UnsupportedOperationException();
    }
}
