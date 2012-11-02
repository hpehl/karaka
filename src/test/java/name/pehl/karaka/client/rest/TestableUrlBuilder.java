package name.pehl.karaka.client.rest;

/**
 * Standalone {@link UrlBuilder} with no dependencies to GWT.
 * 
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class TestableUrlBuilder extends UrlBuilder
{
    static final String PROTOCOL = "http";
    static final String HOST = "localhost";
    static final String DEFAULT_URL = PROTOCOL + PROTOCOL_HOSTNAME_SEPARATOR + HOST;


    /**
     * @return {@value #DEFAULT_URL}
     * @see name.pehl.piriti.restlet.client.UrlBuilder#defaultUrl()
     */
    @Override
    protected String defaultUrl()
    {
        return DEFAULT_URL;
    }


    @Override
    protected String encode(String url)
    {
        return url;
    }
}
