package name.pehl.tire.shared.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

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
    }


    @Test(expected = IllegalArgumentException.class)
    public void setId()
    {
        new TestableBaseModel().setId(null);
    }
}
