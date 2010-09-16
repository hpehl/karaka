package name.pehl.tire.client.rest;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author $Author$
 * @version $Date$ $Revision:
 *          77293 $
 */
public class UrlBuilderTest
{
    private UrlBuilder underTest;


    // ----------------------------------------------------- new instance tests

    @Test(expected = IllegalStateException.class)
    public void testBuildUrlWithNoData()
    {
        underTest = new UrlBuilder();
        underTest.buildUrl();
    }


    // ----------------------------------------------------------- schema tests

    @Test
    public void testSetProtocol()
    {
        underTest = new UrlBuilder().setProtocol("http");
        assertEquals("http", underTest.getProtocol());

        underTest.setProtocol("http://");
        assertEquals("http", underTest.getProtocol());

        underTest.setProtocol("http:/");
        assertEquals("http", underTest.getProtocol());

        underTest.setProtocol("http:");
        assertEquals("http", underTest.getProtocol());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullProtocol()
    {
        underTest = new UrlBuilder().setProtocol(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyProtocol()
    {
        underTest = new UrlBuilder().setProtocol("");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidProtocol()
    {
        underTest = new UrlBuilder().setProtocol("wont:work");
    }


    // ------------------------------------------------------------- host tests

    @Test
    public void testSetHost()
    {
        underTest = new UrlBuilder();
        assertEquals(UrlBuilder.DEFAULT_HOST, underTest.getHost());

        underTest.setHost("www.pehl.name");
        assertEquals("www.pehl.name", underTest.getHost());

        underTest.setHost("www.pehl.name:8080");
        assertEquals("www.pehl.name", underTest.getHost());
        assertEquals(8080, underTest.getPort());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullHost()
    {
        underTest = new UrlBuilder().setHost(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyHost()
    {
        underTest = new UrlBuilder().setHost("");
    }


    // ------------------------------------------------------------- port tests

    @Test
    public void testSetPort()
    {
        underTest = new UrlBuilder();
        assertEquals(UrlBuilder.PORT_UNSPECIFIED, underTest.getPort());

        underTest.setPort(0);
        assertEquals(0, underTest.getPort());

        underTest.setPort(1234);
        assertEquals(1234, underTest.getPort());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetInvalidPort()
    {
        underTest = new UrlBuilder().setPort(-12);
    }


    // ---------------------------------------------------------- context tests

    @Test
    public void testSetContext()
    {
        underTest = new UrlBuilder();
        assertEquals(UrlBuilder.DEFAULT_CONTEXT, underTest.getContext());

        underTest.setContext("/");
        assertEquals(UrlBuilder.DEFAULT_CONTEXT, underTest.getContext());

        underTest.setContext("/tire");
        assertEquals("/tire", underTest.getContext());

        underTest.setContext("tire");
        assertEquals("/tire", underTest.getContext());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullContext()
    {
        underTest = new UrlBuilder().setContext(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyContext()
    {
        underTest = new UrlBuilder().setContext("");
    }


    // ----------------------------------------------------------- module tests

    @Test
    public void testSetModule()
    {
        underTest = new UrlBuilder();
        assertEquals(UrlBuilder.DEFAULT_MODULE, underTest.getModule());

        underTest.setModule("/rest");
        assertEquals("rest", underTest.getModule());

        underTest.setModule("rest");
        assertEquals("rest", underTest.getModule());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullModule()
    {
        underTest = new UrlBuilder().setModule(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyModule()
    {
        underTest = new UrlBuilder().setModule("");
    }


    // ---------------------------------------------------------- version tests

    @Test
    public void testSetVersion()
    {
        underTest = new UrlBuilder();
        assertEquals(UrlBuilder.DEFAULT_VERSION, underTest.getVersion());

        underTest.setVersion("/v13");
        assertEquals("v13", underTest.getVersion());

        underTest.setVersion("v13");
        assertEquals("v13", underTest.getVersion());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullVersion()
    {
        underTest = new UrlBuilder().setVersion(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyVersion()
    {
        underTest = new UrlBuilder().setVersion("");
    }


    // --------------------------------------------------- authentication tests

    @Test
    public void testSetAuthentication()
    {
        underTest = new UrlBuilder();
        assertNull(underTest.getAuthentication());

        underTest.setAuthentication("/0815");
        assertEquals("0815", underTest.getAuthentication());

        underTest.setAuthentication("0815");
        assertEquals("0815", underTest.getAuthentication());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetNullAuthentication()
    {
        underTest = new UrlBuilder().setAuthentication(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyAuthentication()
    {
        underTest = new UrlBuilder().setAuthentication("");
    }


    // --------------------------------------------------------- resource paths

    @Test
    public void testAddResourcePath()
    {
        underTest = new UrlBuilder();
        assertTrue(underTest.getResourcePaths().isEmpty());

        underTest.addResourcePath("eins", "/", null, "/zwei");
        assertEquals(4, underTest.getResourcePaths().size());
        assertEquals("eins", underTest.getResourcePaths().get(0));
        assertEquals("", underTest.getResourcePaths().get(1));
        assertNull(underTest.getResourcePaths().get(2));
        assertEquals("zwei", underTest.getResourcePaths().get(3));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddNullResourcePath()
    {
        underTest = new UrlBuilder().addResourcePath((String[]) null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyResourcePath()
    {
        underTest = new UrlBuilder().addResourcePath(new String[0]);
    }


    @Test
    public void testClearResourcePaths()
    {
        underTest = new UrlBuilder().addResourcePath("1", "2", "3", "viele");
        assertEquals(4, underTest.getResourcePaths().size());
        underTest.clearResourcePaths();
        assertTrue(underTest.getResourcePaths().isEmpty());
    }


    // -------------------------------------------------- query parameter tests

    @Test
    public void testAddQueryParameter()
    {
        underTest = new UrlBuilder();
        assertTrue(underTest.getQueryParams().isEmpty());

        underTest.addQueryParameter("nachname", "pehl").addQueryParameter("vorname", "harald", "willi");
        assertEquals(2, underTest.getQueryParams().size());
        assertArrayEquals(new String[] {"pehl"}, underTest.getQueryParams().get("nachname"));
        assertArrayEquals(new String[] {"harald", "willi"}, underTest.getQueryParams().get("vorname"));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddQueryParameterWithNullKey()
    {
        underTest = new UrlBuilder().addQueryParameter(null, "egal");
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddQueryParameterWithNullValues()
    {
        underTest = new UrlBuilder().addQueryParameter("egal", (String[]) null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddQueryParameterWithEmptyValues()
    {
        underTest = new UrlBuilder().addQueryParameter("egal", new String[0]);
    }
}
