package name.pehl.karaka.server.security;

import static name.pehl.karaka.server.security.SecurityToken.TOKEN_NAME;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;

/**
 * Servlet filter to add the token cookie to the response.
 *
 * @author $Author: harald.pehl $
 * @version $Date: 2010-10-28 17:34:57 +0200 (Do, 28. Okt 2010) $ $Revision: 175
 *          $
 */
public class CookieFilter implements Filter {

    private String token;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SecureRandom secureRandom = new SecureRandom();
        this.token = new BigInteger(130, secureRandom).toString(32);
    }

    @Override
    public void destroy() {
        this.token = null;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            Cookie securityCookie = new Cookie(TOKEN_NAME, token);
            securityCookie.setMaxAge(-1);
            securityCookie.setPath("/");
            httpResponse.addCookie(securityCookie);
        }
        chain.doFilter(request, response);
    }
}
