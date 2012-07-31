package name.pehl.tire.shared.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class MinutesTest
{
    @Test
    public void equalsAndHashcode()
    {
        Duration d1 = new Duration(1);
        Duration d2 = new Duration(2);
        new EqualsTester().addEqualityGroup(new Minutes(), new Minutes())
                .addEqualityGroup(new Minutes(d1, d1, d1), new Minutes(d1, d1, d1))
                .addEqualityGroup(new Minutes(d1, d1, d2), new Minutes(d1, d1, d2))
                .addEqualityGroup(new Minutes(d1, d2, d1), new Minutes(d1, d2, d1))
                .addEqualityGroup(new Minutes(d2, d1, d1), new Minutes(d2, d1, d1)).testEquals();
    }
}
