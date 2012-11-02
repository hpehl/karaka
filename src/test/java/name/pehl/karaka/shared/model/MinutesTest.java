package name.pehl.karaka.shared.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class MinutesTest
{
    @Test
    public void equalsAndHashcode()
    {
        Duration d1 = new Duration(1);
        Duration d2 = new Duration(2);
        new EqualsTester().addEqualityGroup(new Durations(), new Durations())
                .addEqualityGroup(new Durations(d1, d1, d1), new Durations(d1, d1, d1))
                .addEqualityGroup(new Durations(d1, d1, d2), new Durations(d1, d1, d2))
                .addEqualityGroup(new Durations(d1, d2, d1), new Durations(d1, d2, d1))
                .addEqualityGroup(new Durations(d2, d1, d1), new Durations(d2, d1, d1)).testEquals();
    }
}
