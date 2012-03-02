package name.pehl.taoki.security;

/**
 * Interface for reading the security token from T.
 * 
 * @param <T>
 *            The input type
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-19 13:55:11 +0200 (Di, 19. Okt 2010) $ $Revision: 180
 *          $
 */
public interface SecurityTokenReader<T>
{
    /**
     * Reads the security token from the request and / or response.
     * 
     * @param request
     * @param response
     * @return the security token
     * @throws SecurityException
     *             if there's no token.
     */
    String readToken(T input) throws SecurityException;
}
