package name.pehl.tire.client.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.http.client.URL;

/**
 * Class for creating resource urls. A resource url can contain the following
 * sections:<br/>
 * <code>&lt;protocol&gt;://&lt;host&gt;[:&lt;port&gt;]&lt;context&gt;/&lt;module&gt;/&lt;version&gt;/&lt;authentication&gt;&lt;resourcePaths&gt;[?&lt;queryParameter&gt;]</code>
 * <p>
 * <table cellspacing="0" cellpadding="3" border="1">
 * <tr>
 * <th align="left">Section</th>
 * <th align="left">Description</th>
 * <th align="left">Default Value</th>
 * <th align="left">Example</th>
 * </tr>
 * <tr>
 * <td>protocol</td>
 * <td>The protocol. If specified, the value must not be empty / null.</td>
 * <td>http</td>
 * <td>http, https</td>
 * </tr>
 * <tr>
 * <td>host</td>
 * <td>The hostname. Can contain a port nnumber. If specified, the value must
 * not be empty / null.</td>
 * <td>tire-d8.appspot.com</td>
 * <td>127.0.0.1, www.somehost.com:8080</td>
 * </tr>
 * <tr>
 * <td>port</td>
 * <td>The port number. If specified, the value must be a positive number.</td>
 * <td>-/-</td>
 * <td>80, 8080</td>
 * </tr>
 * <tr>
 * <td>context</td>
 * <td>The context path. If specified, the value must not be empty / null. If
 * the first character of the context path is '/', that won't lead to double '/'
 * in the resulting URL.</td>
 * <td>/</td>
 * <td>/, /foo</td>
 * </tr>
 * <tr>
 * <td>module</td>
 * <td>The rest module. If specified, the value must not be empty / null.</td>
 * <td>rest</td>
 * <td>administration, common, print</td>
 * </tr>
 * <tr>
 * <td>version</td>
 * <td>The version number of the resource. If specified, the value must not be
 * empty / null.</td>
 * <td>v1</td>
 * <td>r123, v4.2, 56</td>
 * </tr>
 * <tr>
 * <td>authentication</td>
 * <td>The authentication part of the URL. If specified, the value must not be
 * empty / null.</td>
 * <td>-/-</td>
 * <td>978979834523</td>
 * </tr>
 * <tr>
 * <td>resourcePaths</td>
 * <td>The paths of the resource. At least one path has to be specified. If the
 * first character of the context path is '/', that won't lead to double '/' in
 * the resulting URL.</td>
 * <td>-/-</td>
 * <td>[projects], [/activities, 2010, /01, 21]</td>
 * </tr>
 * <tr>
 * <td>queryParameter</td>
 * <td>Optional query parameter for the resource. Each query parameter can be
 * assigned to multiple values. If specified the name and values must not be
 * empty / null.</td>
 * <td>-/-</td>
 * <td>[surname=[Pehl], firstnames=[Harald, Willi]]</td>
 * </tr>
 * </table>
 * <p>
 * A complete URL with all sections could look like this:
 * <code>http://tire-d8.appspot.com/rest/v1/184829045689389283/activities/2010/01/22?tag=foo</code>
 * 
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-12-08 17:33:09 +0100 (Di, 08. Dez 2009) $ $Revision:
 *          77293 $
 */
public class UrlBuilder
{
    // -------------------------------------------------------------- constants

    public static final int PORT_UNSPECIFIED = Integer.MIN_VALUE;
    public static final String DEFAULT_PROTOCOL = "http";
    public static final String DEFAULT_HOST = "tire-d8.appspot.com/";
    public static final String DEFAULT_CONTEXT = "/";
    public static final String DEFAULT_MODULE = "rest";
    public static final String DEFAULT_VERSION = "v1";

    // -------------------------------------------------------- private members

    private String protocol = DEFAULT_PROTOCOL;
    private String host = DEFAULT_HOST;
    private int port = PORT_UNSPECIFIED;
    private String context = DEFAULT_CONTEXT;
    private String module = DEFAULT_MODULE;
    private String version = DEFAULT_VERSION;
    private String authentication = null;
    private List<String> resourcePaths = new ArrayList<String>();
    private Map<String, String[]> queryParams = new HashMap<String, String[]>();


    // --------------------------------------------------------- public methods

    /**
     * Build the URL and return it as an encoded string.
     * 
     * @return the encoded URL string
     */
    public final String buildUrl()
    {
        if (resourcePaths.isEmpty())
        {
            throw new IllegalStateException("No resource paths given. Please call addResourcePath(String) first!");
        }
        return internalBuildUrl();
    }


    @Override
    public String toString()
    {
        return internalBuildUrl();
    }


    private String internalBuildUrl()
    {
        // http://
        StringBuilder url = new StringBuilder();
        url.append(protocol).append("://");

        // http://some.host.com
        url.append(host);

        // http://some.host.com:8080
        if (port != PORT_UNSPECIFIED)
        {
            url.append(":").append(port);
        }

        // http://some.host.com:8080/
        url.append(context);

        // http://some.host.com:8080/rest
        url.append("/").append(module);

        // http://some.host.com:8080/rest/v1
        url.append("/").append(version);

        // http://some.host.com:8080/rest/v1/6785647564782657
        if (authentication != null)
        {
            url.append("/").append(authentication);
        }

        // http://some.host.com:8080/rest/v1/6785647564782657/activities/2010/01/21
        for (String path : resourcePaths)
        {
            url.append("/").append(path);
        }

        // http://some.host.com:8080/rest/v1/6785647564782657/activities/2010/01/21?k0=v0&k1=v1
        char prefix = '?';
        for (Map.Entry<String, String[]> entry : queryParams.entrySet())
        {
            for (String val : entry.getValue())
            {
                url.append(prefix).append(entry.getKey()).append('=');
                if (val != null)
                {
                    url.append(val);
                }
                prefix = '&';
            }
        }

        return URL.encode(url.toString());
    }


    public UrlBuilder setProtocol(String protocol)
    {
        assertValid(protocol, "Protocol must not be null");
        if (protocol.endsWith("://"))
        {
            protocol = protocol.substring(0, protocol.length() - 3);
        }
        else if (protocol.endsWith(":/"))
        {
            protocol = protocol.substring(0, protocol.length() - 2);
        }
        else if (protocol.endsWith(":"))
        {
            protocol = protocol.substring(0, protocol.length() - 1);
        }
        if (protocol.contains(":"))
        {
            throw new IllegalArgumentException("Illegal protocol: " + protocol);
        }
        assertValid(protocol, "Protokoll must not be null");
        this.protocol = protocol;
        return this;
    }


    public String getProtocol()
    {
        return protocol;
    }


    public UrlBuilder setHost(String host)
    {
        assertValid(host, "Host must not be null");
        if (host.contains(":"))
        {
            String[] parts = host.split(":");
            if (parts.length > 2)
            {
                throw new IllegalArgumentException("Host contains more than one colon: " + host);
            }
            try
            {
                setPort(Integer.parseInt(parts[1]));
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Cannot read port from host: " + host);
            }
            host = parts[0];
        }
        this.host = host;
        return this;
    }


    public String getHost()
    {
        return host;
    }


    public UrlBuilder setPort(int port)
    {
        if (port < 0)
        {
            throw new IllegalArgumentException("Port must not be negative");
        }
        this.port = port;
        return this;
    }


    public int getPort()
    {
        return port;
    }


    public UrlBuilder setContext(String context)
    {
        assertValid(context, "Context must not be null");
        if (context.startsWith("/"))
        {
            this.context = context;
        }
        else
        {
            this.context = "/" + context;
        }
        return this;
    }


    public String getContext()
    {
        return context;
    }


    public UrlBuilder setModule(String module)
    {
        assertValid(module, "Module must not be null");
        if (module.startsWith("/"))
        {
            module = module.substring(1);
        }
        this.module = module;
        return this;
    }


    public String getModule()
    {
        return module;
    }


    public UrlBuilder setVersion(String version)
    {
        assertValid(version, "Version must not be null");
        if (version.startsWith("/"))
        {
            version = version.substring(1);
        }
        this.version = version;
        return this;
    }


    public String getVersion()
    {
        return version;
    }


    public UrlBuilder setAuthentication(String authentication)
    {
        assertValid(authentication, "Authentication must not be null");
        if (authentication.startsWith("/"))
        {
            authentication = authentication.substring(1);
        }
        this.authentication = authentication;
        return this;
    }


    public String getAuthentication()
    {
        return authentication;
    }


    public UrlBuilder addResourcePath(String... paths)
    {
        assertNotEmpty(paths, "Resource paths must not be empty");
        for (String path : paths)
        {
            if (path != null && path.startsWith("/"))
            {
                path = path.substring(1);
            }
            resourcePaths.add(path);
        }
        return this;
    }


    public UrlBuilder clearResourcePaths()
    {
        resourcePaths.clear();
        return this;
    }


    public List<String> getResourcePaths()
    {
        return resourcePaths;
    }


    public UrlBuilder addQueryParameter(String name, String... values)
    {
        assertValid(name, "Parameter name must not be null");
        assertNotEmpty(values, "Values must not be empty");
        queryParams.put(name, values);
        return this;
    }


    public Map<String, String[]> getQueryParams()
    {
        return queryParams;
    }


    // --------------------------------------------------------- helper methods

    private void assertValid(String value, String message) throws IllegalArgumentException
    {
        if (value == null)
        {
            throw new IllegalArgumentException(message);
        }
        if (value.length() == 0)
        {
            throw new IllegalArgumentException(message);
        }
    }


    private void assertNotEmpty(Object[] values, String message) throws IllegalArgumentException
    {
        if (values == null)
        {
            throw new IllegalArgumentException(message);
        }
        if (values.length == 0)
        {
            throw new IllegalArgumentException(message);
        }
    }
}
