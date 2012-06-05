package name.pehl.tire.shared.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserTest
{
    @Test
    public void newInstance()
    {
        User cut = new User();
        assertNotNull(cut.getId());
        cut = new User("foo", "foo@bar.com");
        assertNotNull(cut.getId());
        cut = new User("0815", "foo", "foo@bar.com");
        assertNotNull(cut.getId());
    }
}
