package name.pehl.tire.client.dispatch;

import org.restlet.client.Request;
import org.restlet.client.resource.ClientResource;

import com.gwtplatform.dispatch.client.DispatchRequest;

/**
 * Implementation of {@link DispatchRequest} used by
 * {@link AbstractRestletClientActionHandler}
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class DispatchRequestRestletImpl implements DispatchRequest
{
    private final ClientResource clientResource;


    public DispatchRequestRestletImpl(final ClientResource clientResource)
    {
        this.clientResource = clientResource;
    }


    @Override
    public void cancel()
    {
        Request request = clientResource.getRequest();
        if (request != null)
        {
            request.abort();
        }
    }


    @Override
    public boolean isPending()
    {
        return clientResource.getResponse() == null;
    }
}
