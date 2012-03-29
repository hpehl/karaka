package name.pehl.tire.client.dispatch;

import org.fusesource.restygwt.client.Method;

import com.google.gwt.http.client.Request;
import com.gwtplatform.dispatch.shared.DispatchRequest;

/**
 * Implementation of {@link DispatchRequest} used by {@link TireActionHandler}
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class DispatchRequestRestletImpl implements DispatchRequest
{
    private final Method method;


    public DispatchRequestRestletImpl(final Method method)
    {
        this.method = method;
    }


    @Override
    public void cancel()
    {
        Request request = method.getRequest();
        if (request != null)
        {
            request.cancel();
        }
    }


    @Override
    public boolean isPending()
    {
        return method.getResponse() == null;
    }
}
