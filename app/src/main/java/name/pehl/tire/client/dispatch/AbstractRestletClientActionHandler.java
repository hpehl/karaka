package name.pehl.tire.client.dispatch;

import java.io.IOException;

import org.restlet.client.Request;
import org.restlet.client.Response;
import org.restlet.client.Uniform;
import org.restlet.client.data.Form;
import org.restlet.client.data.MediaType;
import org.restlet.client.data.Method;
import org.restlet.client.data.Preference;
import org.restlet.client.data.Status;
import org.restlet.client.engine.header.HeaderConstants;
import org.restlet.client.resource.ClientResource;
import org.restlet.client.resource.ResourceException;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.DispatchRequest;
import com.gwtplatform.dispatch.client.SecurityCookieAccessor;
import com.gwtplatform.dispatch.client.actionhandler.AbstractClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.dispatch.client.actionhandler.UndoCommand;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;

/**
 * Implementation of the command pattern using Restlet instead of GWT-RPC.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class AbstractRestletClientActionHandler<A extends Action<R>, R extends Result> extends
        AbstractClientActionHandler<A, R>
{
    protected final Method method;
    protected final MediaType mediaType;
    protected final String securityCookieName;
    protected final SecurityCookieAccessor securityCookieAccessor;


    protected AbstractRestletClientActionHandler(final Class<A> actionType, final Method method,
            final MediaType mediaType, final String securityCookieName,
            final SecurityCookieAccessor securityCookieAccessor)
    {
        super(actionType);
        this.method = method;
        this.mediaType = mediaType;
        this.securityCookieName = securityCookieName;
        this.securityCookieAccessor = securityCookieAccessor;
    }


    @Override
    public DispatchRequest execute(final A action, final AsyncCallback<R> resultCallback,
            final ExecuteCommand<A, R> executeCommand)
    {
        final ClientResource clientResource = new ClientResource(method, getUrl(action));
        if (action.isSecured())
        {
            // Add the security token as header
            String header = securityCookieName + "=" + securityCookieAccessor.getCookieContent();
            clientResource.getRequest().getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, new Form(header));
        }
        if (mediaType != null)
        {
            clientResource.getClientInfo().getAcceptedMediaTypes().add(new Preference<MediaType>(mediaType));
        }
        clientResource.setOnResponse(new Uniform()
        {
            @Override
            public void handle(Request request, Response response)
            {
                if (response.getStatus().isError())
                {
                    resultCallback.onFailure(new ResourceException(response.getStatus()));
                }
                else
                {
                    try
                    {
                        resultCallback.onSuccess(extractResult(response));
                    }
                    catch (IOException e)
                    {
                        Throwable cause = e.getCause() != null ? e.getCause() : e;
                        resultCallback
                                .onFailure(new ResourceException(Status.CLIENT_ERROR_UNPROCESSABLE_ENTITY, cause));
                    }
                }
            }
        });
        clientResource.handle();
        return new DispatchRequestRestletImpl(clientResource);
    }


    protected abstract String getUrl(A action);


    protected abstract R extractResult(Response response) throws IOException;


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
