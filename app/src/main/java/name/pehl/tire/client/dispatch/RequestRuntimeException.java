package name.pehl.tire.client.dispatch;

/**
 * Wrap {@link com.google.gwt.http.client.RequestException} in a
 * RuntimeException.
 * 
 * @author Denis Labaye
 */
@SuppressWarnings("serial")
public class RequestRuntimeException extends RuntimeException
{
    public RequestRuntimeException()
    {
    }


    public RequestRuntimeException(final String message)
    {
        super(message);
    }


    public RequestRuntimeException(final String message, final Throwable cause)
    {
        super(message, cause);
    }


    public RequestRuntimeException(final Throwable cause)
    {
        super(cause);
    }
}
