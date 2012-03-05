package name.pehl.tire.server.rest.security;

import org.restlet.Request;
import org.restlet.Response;

import com.google.inject.Inject;

/**
 * Reads the security token as template parameter from the url.
 * 
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-28 17:34:57 +0200 (Do, 28. Okt 2010) $ $Revision: 180
 *          $
 */
public class UrlSecurityTokenReader implements SecurityTokenReader
{
    private final String name;


    @Inject
    public UrlSecurityTokenReader(@SecurityToken final String name)
    {
        this.name = name;
    }


    /**
     * @param request
     * @param response
     * @return
     * @see name.pehl.taoki.security.SecurityTokenReader#readToken(org.restlet.Request,
     *      org.restlet.Response)
     */
    @Override
    public String readToken(Request request, Response response)
    {
        String token = (String) request.getAttributes().get(name);
        if (token == null)
        {
            throw new SecurityException("No security token found for " + request.getResourceRef().toUrl());
        }
        return token;
    }
}
