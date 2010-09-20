package name.pehl.tire.client.dispatch;

import com.google.gwt.http.client.Request;
import com.gwtplatform.dispatch.client.DispatchRequest;

public class DispatchRequestRequestBuilderImpl implements DispatchRequest
{
    private final Request request;


    public DispatchRequestRequestBuilderImpl(final Request request)
    {
        super();
        this.request = request;
    }


    @Override
    public void cancel()
    {
        request.cancel();
    }


    @Override
    public boolean isPending()
    {
        return request.isPending();
    }
}
