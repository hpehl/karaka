package name.pehl.tire.client.dispatch;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.dispatch.client.DispatchRequest;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;

/**
 * Dispatch Actions using RequestBuilder. Use this DispatchAsync implementation
 * when the backend is not GWT-RPC.
 * 
 * @author Denis Labaye
 */
public class DispatchAsyncRequestBuilderImpl implements DispatchAsync
{
    private final Map<Class<Action<?>>, RequestBuilderActionHandler<?, ?>> handlersMap = new HashMap<Class<Action<?>>, RequestBuilderActionHandler<?, ?>>();


    @SuppressWarnings("unchecked")
    public <A extends Action<R>, R extends Result> void addHandler(final RequestBuilderActionHandler<A, R> handler)
    {
        handlersMap.put((Class<Action<?>>) handler.getActionType(), handler);
    }


    private <A extends Action<R>, R extends Result> RequestCallback createRequestCallback(
            final AsyncCallback<R> asynchCallback, final RequestBuilderActionHandler<A, R> handler)
    {

        return new RequestCallback()
        {

            @Override
            public void onError(final Request request, final Throwable exception)
            {
                asynchCallback.onFailure(exception);
            }


            @Override
            public void onResponseReceived(final Request request, final Response response)
            {
                asynchCallback.onSuccess(handler.extractResult(response));
            }
        };
    }


    /**
     * @return
     * @throws RequestRuntimeException
     *             see {@link RequestBuilder#send()}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <A extends Action<R>, R extends Result> DispatchRequest execute(final A action,
            final AsyncCallback<R> callback)
    {
        final Class<A> actionType = (Class<A>) action.getClass();

        final RequestBuilderActionHandler<A, R> handler = (RequestBuilderActionHandler<A, R>) handlersMap
                .get(actionType);

        if (handler == null)
        {
            throw new IllegalArgumentException("unregistered actionType:" + actionType);
        }

        final RequestBuilder requestBuilder = handler.getRequestBuilder(action);
        requestBuilder.setCallback(createRequestCallback(callback, handler));

        try
        {
            return new DispatchRequestRequestBuilderImpl(requestBuilder.send());
        }
        catch (final RequestException e)
        {
            throw new RequestRuntimeException(e);
        }
    }


    /**
     * Not supported.
     * 
     * @throws UnsupportedOperationException
     *             if called.
     */
    @Override
    public <A extends Action<R>, R extends Result> DispatchRequest undo(final A action, final R result,
            final AsyncCallback<Void> callback)
    {
        throw new UnsupportedOperationException();
    }
}
