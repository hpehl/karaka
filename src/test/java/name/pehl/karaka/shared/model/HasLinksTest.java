package name.pehl.karaka.shared.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HasLinksTest
{
    // ------------------------------------------------------------------ tests

    @Test
    public void newInstance()
    {
        TestableHasLinks cut = new TestableHasLinks();
        assertTrue(cut.getLinks().isEmpty());
    }


    @Test
    public void links()
    {
        TestableHasLinks cut = new TestableHasLinks();
        cut.addLink(null, null);
        assertTrue(cut.getLinks().isEmpty());
        cut.addLink(null, "foo");
        assertTrue(cut.getLinks().isEmpty());
        cut.addLink("foo", null);
        assertTrue(cut.getLinks().isEmpty());
        cut.addLink("foo", "bar");
        assertFalse(cut.getLinks().isEmpty());
        assertEquals(1, cut.getLinks().size());
        assertEquals(new Link("foo", "bar"), cut.getLinks().get(0));
    }
}
