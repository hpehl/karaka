package name.pehl.karaka.shared.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;

public class LinkTest
{
    // ------------------------------------------------------------------ tests

    @Test
    public void equalsAndHashcode()
    {
        new EqualsTester().addEqualityGroup(new Link(null, null), new Link(null, null))
                .addEqualityGroup(new Link("foo", null), new Link("foo", null))
                .addEqualityGroup(new Link(null, "bar"), new Link(null, "bar"))
                .addEqualityGroup(new Link("foo", "bar"), new Link("foo", "bar")).testEquals();
    }


    @Test
    public void toStringTest()
    {
        Link cut = new Link("foo", "bar");
        assertEquals("<bar>; rel=\"foo\"", cut.toString());
    }
}
