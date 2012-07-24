package name.pehl.tire.client.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import name.pehl.tire.client.ui.Highlighter;

import org.junit.Before;
import org.junit.Test;

public class HighlighterTest
{
    Highlighter cut;


    @Before
    public void setUp()
    {
        cut = new Highlighter("foo");
    }


    @Test
    public void nullString()
    {
        String result = cut.highlight(null);
        assertNull(result);
    }


    @Test
    public void emptyString()
    {
        String result = cut.highlight("");
        assertNull(result);
    }


    @Test
    public void blankString()
    {
        String result = cut.highlight("     ");
        assertEquals("     ", result);
    }


    @Test
    public void noFoo()
    {
        String result = cut.highlight("bar");
        assertEquals("bar", result);
    }


    @Test
    public void justFoo()
    {
        String result = cut.highlight("foo");
        assertEquals("<strong>foo</strong>", result);
    }


    @Test
    public void containigFoo()
    {
        String result = cut.highlight("foo bar meep");
        assertEquals("<strong>foo</strong> bar meep", result);

        result = cut.highlight("foo bar foobar");
        assertEquals("<strong>foo</strong> bar <strong>foo</strong>bar", result);
    }


    @Test
    public void mixedCase()
    {
        String input = "Foo and foo but also FOO, foobar, FooBar and FOOBAR";
        String result = cut.highlight(input);
        String expected = "<strong>Foo</strong> and <strong>foo</strong> but also <strong>FOO</strong>, <strong>foo</strong>bar, <strong>Foo</strong>Bar and <strong>FOO</strong>BAR";
        assertEquals(expected, result);

        cut = new Highlighter("Foo");
        result = cut.highlight(input);
        assertEquals(expected, result);
    }
}
