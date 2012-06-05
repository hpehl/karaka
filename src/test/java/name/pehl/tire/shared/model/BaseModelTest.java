package name.pehl.tire.shared.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BaseModelTest
{
    // ------------------------------------------------------------------ tests

    @Test
    public void equalsAndHashcode()
    {
        TestableBaseModel cut = new TestableBaseModel();
        new EqualsTester().addEqualityGroup(cut, cut)
                .addEqualityGroup(new TestableBaseModel("0815"), new TestableBaseModel("0815")).testEquals();
    }


    @Test
    public void newInstance()
    {
        TestableBaseModel cut = new TestableBaseModel();
        assertNotNull(cut.getId());
        assertTrue(cut.isTransient());
        assertTrue(cut.getLinks().isEmpty());
    }


    @Test
    public void links()
    {
        TestableBaseModel cut = new TestableBaseModel();
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


    @Test(expected = IllegalArgumentException.class)
    public void setId()
    {
        new TestableBaseModel().setId(null);
    }
}
