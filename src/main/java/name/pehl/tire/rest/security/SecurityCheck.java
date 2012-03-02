package name.pehl.taoki.security;

/**
 * Interface for a security check for a resource. The security check need some
 * input to read the security token from.
 * 
 * @param <T>
 *            The input type
 * @author $LastChangedBy:$
 * @version $LastChangedRevision:$
 */
public interface SecurityCheck<T>
{
    /**
     * Checks whether the resource is available. All information must be
     * provided in the {@link Request} and {@link Response} objects.
     * 
     * @param request
     * @param response
     * @throws SecurityException
     *             if the check failed.
     */
    void check(T input) throws SecurityException;
}
