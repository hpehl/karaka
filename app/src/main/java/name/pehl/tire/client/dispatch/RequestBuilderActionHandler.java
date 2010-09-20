package name.pehl.tire.client.dispatch;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;

/**
 * Actions Handlers when using a {@link DispatchAsyncRequestBuilderImpl}.
 * 
 * @author Denis Labaye
 */
public interface RequestBuilderActionHandler<A extends Action<R>, R extends Result>
{
    /**
     * Extract the result from the response.
     */
    R extractResult(Response response);


    Class<A> getActionType();


    /**
     * Prepare a RequestBuilder.
     */
    RequestBuilder getRequestBuilder(A action);
}
