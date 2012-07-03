package name.pehl.tire.shared.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class MinutesTest
{
    @Test
    public void equalsAndHashcode()
    {
        new EqualsTester().addEqualityGroup(new Minutes(), new Minutes())
                .addEqualityGroup(new Minutes(1, 1, 1), new Minutes(1, 1, 1))
                .addEqualityGroup(new Minutes(1, 1, 2), new Minutes(1, 1, 2))
                .addEqualityGroup(new Minutes(1, 2, 1), new Minutes(1, 2, 1))
                .addEqualityGroup(new Minutes(2, 1, 1), new Minutes(2, 1, 1)).testEquals();
    }
}
