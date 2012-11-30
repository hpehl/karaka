package name.pehl.karaka.client.dispatch;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.shared.Result;
import name.pehl.piriti.json.client.JsonReader;
import org.fusesource.restygwt.client.FailedStatusCodeException;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;

public abstract class KarakaJsonCallback<T, R extends Result> implements JsonCallback
{
    private final JsonReader<T> reader;
    private final AsyncCallback<R> resultCallback;


    public KarakaJsonCallback(JsonReader<T> reader, AsyncCallback<R> resultCallback)
    {
        super();
        this.reader = reader;
        this.resultCallback = resultCallback;
    }


    @Override
    public void onSuccess(final Method method, final JSONValue response)
    {
        if (response.isObject() != null)
        {
            resultCallback.onSuccess(extractResult(method, reader, response.isObject()));
        }
        else if (response.isArray() != null)
        {
            // Arrays are supported as return values. In order to have one
            // single extractResult() method build a helper JSONObject which
            // contains the array.
            JSONObject root = new JSONObject();
            root.put("list", response.isArray());
            resultCallback.onSuccess(extractResult(method, reader, root));
        }
        else
        {
            resultCallback.onFailure(new RestException(method, "Response was NOT a valid JSON object"));
        }
    }


    @Override
    public void onFailure(Method method, Throwable exception)
    {
        int statusCode = -1;
        if (exception instanceof FailedStatusCodeException)
        {
            statusCode = ((FailedStatusCodeException)exception).getStatusCode();
        }
        resultCallback.onFailure(new RestException(method, exception.getMessage(), statusCode));
    }


    protected abstract R extractResult(Method method, JsonReader<T> reader, JSONObject json);
}
