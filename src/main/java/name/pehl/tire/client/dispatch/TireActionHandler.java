package name.pehl.tire.client.dispatch;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.client.actionhandler.AbstractClientActionHandler;
import com.gwtplatform.dispatch.client.actionhandler.ExecuteCommand;
import com.gwtplatform.dispatch.client.actionhandler.UndoCommand;
import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.DispatchRequest;
import com.gwtplatform.dispatch.shared.Result;
import com.gwtplatform.dispatch.shared.SecurityCookieAccessor;

/**
 * Implementation of the command pattern using RestyGWT instead of GWT-RPC.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public abstract class TireActionHandler<A extends Action<R>, R extends Result> extends
        AbstractClientActionHandler<A, R>
{
    public enum HttpMethod
    {
        HEAD,
        GET,
        PUT,
        POST,
        DELETE,
        OPTIONS;
    }

    private final HttpMethod httpMethod;
    private final String contentType;
    private final String securityCookieName;
    private final SecurityCookieAccessor securityCookieAccessor;


    protected TireActionHandler(final Class<A> actionType, final HttpMethod httpMethod, final String contentType,
            final String securityCookieName, final SecurityCookieAccessor securityCookieAccessor)
    {
        super(actionType);
        this.httpMethod = httpMethod;
        this.contentType = contentType;
        this.securityCookieName = securityCookieName;
        this.securityCookieAccessor = securityCookieAccessor;
    }


    @Override
    public DispatchRequest execute(final A action, final AsyncCallback<R> resultCallback,
            final ExecuteCommand<A, R> executeCommand)
    {
        final Resource resource = resourceFor(action);
        Method method = new Method(resource, httpMethod.name()).header(Resource.HEADER_CONTENT_TYPE, contentType);
        if (action.isSecured())
        {
            // Add the security token as header
            String cookieContent = securityCookieAccessor.getCookieContent();
            if (cookieContent != null)
            {
                method = method.header(securityCookieName, cookieContent);
            }
        }
        executeMethod(method, resultCallback);
        return new DispatchRequestRestletImpl(method);
    }


    protected abstract Resource resourceFor(A action);


    protected abstract void executeMethod(final Method method, final AsyncCallback<R> resultCallback);


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
