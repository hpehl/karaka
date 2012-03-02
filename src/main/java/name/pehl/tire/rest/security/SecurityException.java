package name.pehl.taoki.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Exception thrown by {@link SecurityCheck}.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-19 13:55:11 +0200 (Di, 19. Okt 2010) $ $Revision: 149
 *          $
 */
public class SecurityException extends WebApplicationException
{
    /**
     * Creates a new exception with {@link Status#CLIENT_ERROR_FORBIDDEN} and
     * the specified reason.
     * 
     * @param reason
     */
    public SecurityException()
    {
        super(Response.Status.FORBIDDEN);
    }
}
