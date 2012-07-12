package name.pehl.tire.client.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class DisplayStringFormatterTest
{
    DisplayStringFormatter cut;


    @Before
    public void setUp()
    {
        cut = new DisplayStringFormatter("foo");
    }


    @Test
    public void nullString()
    {
        String result = cut.format(null);
        assertNull(result);
    }


    @Test
    public void emptyString()
    {
        String result = cut.format("");
        assertNull(result);
    }


    @Test
    public void blankString()
    {
        String result = cut.format("     ");
        assertEquals("     ", result);
    }


    @Test
    public void noFoo()
    {
        String result = cut.format("bar");
        assertEquals("bar", result);
    }


    @Test
    public void justFoo()
    {
        String result = cut.format("foo");
        assertEquals("<strong>foo</strong>", result);
    }


    @Test
    public void containigFoo()
    {
        String result = cut.format("foo bar meep");
        assertEquals("<strong>foo</strong> bar meep", result);

        result = cut.format("foo bar foobar");
        assertEquals("<strong>foo</strong> bar <strong>foo</strong>bar", result);
    }


    @Test
    public void mixedCase()
    {
        String result = cut.format("Foo and foo but also FOO, foobar, FooBar and FOOBAR");
        assertEquals(
                "<strong>Foo</strong> and <strong>foo</strong> but also <strong>FOO</strong>, <strong>foo</strong>bar, <strong>Foo</strong>Bar and <strong>FOO</strong>BAR",
                result);
    }
}
