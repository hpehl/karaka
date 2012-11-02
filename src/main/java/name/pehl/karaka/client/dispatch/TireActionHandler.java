package name.pehl.karaka.client.dispatch;

import static name.pehl.karaka.client.dispatch.TireActionHandler.HttpMethod.GET;
import static org.fusesource.restygwt.client.Resource.CONTENT_TYPE_JSON;
import static org.fusesource.restygwt.client.Resource.HEADER_CONTENT_TYPE;

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

    private final String securityCookieName;
    private final SecurityCookieAccessor securityCookieAccessor;


    protected TireActionHandler(final Class<A> actionType, final String securityCookieName,
            final SecurityCookieAccessor securityCookieAccessor)
    {
        super(actionType);
        this.securityCookieName = securityCookieName;
        this.securityCookieAccessor = securityCookieAccessor;
    }


    @Override
    public DispatchRequest execute(final A action, final AsyncCallback<R> resultCallback,
            final ExecuteCommand<A, R> executeCommand)
    {
        final Resource resource = resourceFor(action);
        Method method = methodFor(action, resource);
        applySecurity(action, resource, method);
        executeMethod(method, resultCallback);
        return new DispatchRequestRestletImpl(method);
    }


    protected abstract Resource resourceFor(A action);


    /**
     * Creates a new GET method with content type
     * {@value Resource#CONTENT_TYPE_JSON}. Overwrite if you nee another method
     * or content type.
     * 
     * @param action
     * @param resource
     * @return
     */
    protected Method methodFor(A action, Resource resource)
    {
        return new Method(resource, GET.name()).header(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
    }


    /**
     * Adds the security token if necessary
     * 
     * @param action
     * @param resource
     * @param method
     * @return
     */
    protected Method applySecurity(A action, Resource resource, Method method)
    {
        Method result = method;
        if (action.isSecured())
        {
            // Add the security token as header
            String cookieContent = securityCookieAccessor.getCookieContent();
            if (cookieContent != null)
            {
                result = result.header(securityCookieName, cookieContent);
            }
        }
        return result;
    }


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
