package name.pehl.tire.client.rest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author $Author: lfstad-pehl $
 * @version $Date: 2009-12-08 17:33:09 +0100 (Di, 08. Dez 2009) $ $Revision:
 *          77293 $
 */
public class UrlBuilderTest
{
    // ------------------------------------------------------ default url tests

    @Test(expected = IllegalArgumentException.class)
    public void nullDefaultUrl()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return null;
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void emptyDefaultUrl()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "";
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void invalidDefaultUrl()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "h://w";
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void noProtocolHostSeparatorInDefaultUrl()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "noprotocolhostseparator";
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void invalidHostInDefaultUrl()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "http://h:o:s:t";
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void invalidPortInDefaultUrl()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "http://host:4a5";
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void invalidHostInDefaultUrlWithContext()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "http://h:o:s:t/context";
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void invalidPortInDefaultUrlWithContext()
    {
        new UrlBuilder()
        {
            @Override
            protected String defaultUrl()
            {
                return "http://host:4a5/context";
            }
        };
    }


    // ----------------------------------------------------- new instance tests

    @Test(expected = IllegalStateException.class)
    public void testBuildUrlWithNoData()
    {
        new TestableUrlBuilder().toUrl();
    }


    // ----------------------------------------------------------- schema tests

    @Test
    public void testSetProtocol()
    {
        UrlBuilder underTest = new TestableUrlBuilder().protocol("http");
        assertEquals("http", underTest.protocol);

        underTest.protocol("http://");
        assertEquals("http", underTest.protocol);

        underTest.protocol("http:/");
        assertEquals("http", underTest.protocol);

        underTest.protocol("http:");
        assertEquals("http", underTest.protocol);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProtocol()
    {
        new TestableUrlBuilder().protocol(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyProtocol()
    {
        new TestableUrlBuilder().protocol("");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidProtocol()
    {
        new TestableUrlBuilder().protocol("so:nicht");
    }


    // ------------------------------------------------------------- host tests

    @Test
    public void testSetHost()
    {
        UrlBuilder underTest = new TestableUrlBuilder().host("www.foo.com");
        assertEquals("www.foo.com", underTest.host);

        underTest.host("www.foo.com:8080");
        assertEquals("www.foo.com", underTest.host);
        assertEquals(8080, underTest.port);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullHost()
    {
        new TestableUrlBuilder().host(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyHost()
    {
        new TestableUrlBuilder().host("");
    }


    // ------------------------------------------------------------- port tests

    @Test
    public void testSetPort()
    {
        UrlBuilder underTest = new TestableUrlBuilder();
        assertEquals(UrlBuilder.PORT_UNSPECIFIED, underTest.port);

        underTest.port(0);
        assertEquals(0, underTest.port);

        underTest.port(1234);
        assertEquals(1234, underTest.port);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidPortMin()
    {
        new TestableUrlBuilder().port(-12);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidPortMax()
    {
        new TestableUrlBuilder().port(UrlBuilder.MAX_PORT + 1);
    }


    // ---------------------------------------------------------- context tests

    @Test
    public void testSetContext()
    {
        UrlBuilder underTest = new TestableUrlBuilder();
        assertEquals("/", underTest.context);

        underTest.context("/");
        assertEquals("/", underTest.context);

        underTest.context("foo");
        assertEquals("/foo", underTest.context);

        underTest.context("/foo");
        assertEquals("/foo", underTest.context);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullContext()
    {
        new TestableUrlBuilder().context(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyContext()
    {
        new TestableUrlBuilder().context("");
    }


    // ----------------------------------------------------------- module tests

    @Test
    public void testSetModule()
    {
        UrlBuilder underTest = new TestableUrlBuilder();
        assertNull(underTest.module);

        underTest.module("foo");
        assertEquals("foo", underTest.module);

        underTest.module("/foo");
        assertEquals("foo", underTest.module);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullModule()
    {
        new TestableUrlBuilder().module(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyModule()
    {
        new TestableUrlBuilder().module("");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetSlashModule()
    {
        new TestableUrlBuilder().module("/");
    }


    // ------------------------------------------------------------------ paths

    @Test
    public void testAddPath()
    {
        UrlBuilder underTest = new TestableUrlBuilder();
        assertTrue(underTest.paths.isEmpty());

        underTest.path("eins", "/", null, "/zwei");
        assertEquals(4, underTest.paths.size());
        assertEquals("eins", underTest.paths.get(0));
        assertEquals("", underTest.paths.get(1));
        assertNull(underTest.paths.get(2));
        assertEquals("zwei", underTest.paths.get(3));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddNullPath()
    {
        new TestableUrlBuilder().path((String[]) null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyPath()
    {
        new TestableUrlBuilder().path(new String[0]);
    }


    // -------------------------------------------------- query parameter tests

    @Test
    public void testAddQueryParameter()
    {
        UrlBuilder underTest = new TestableUrlBuilder();
        assertTrue(underTest.query.isEmpty());

        underTest.query("nachname", "pehl").query("vorname", "harald", "willi");
        assertEquals(2, underTest.query.size());
        assertArrayEquals(new String[] {"pehl"}, underTest.query.get("nachname"));
        assertArrayEquals(new String[] {"harald", "willi"}, underTest.query.get("vorname"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddQueryParameterWithNullKey()
    {
        new TestableUrlBuilder().query(null, "egal");
    }
}
