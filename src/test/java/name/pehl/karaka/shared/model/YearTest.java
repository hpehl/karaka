package name.pehl.karaka.shared.model;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YearTest
{
    @Test
    public void newInstance()
    {
        Year cut = new Year();
        assertEquals(0, cut.getYear());
        assertTrue(cut.getMonths().isEmpty());
        assertTrue(cut.getWeeks().isEmpty());
        cut = new Year(1973);
        assertEquals(1973, cut.getYear());
        assertTrue(cut.getMonths().isEmpty());
        assertTrue(cut.getWeeks().isEmpty());
    }


    @Test
    public void equalsAndHashcode()
    {
        Year empty = new Year(2);
        Year withMonths = new Year(2);
        withMonths.addMonth(1);
        Year withWeeks = new Year(2);
        withWeeks.addWeek(1);
        Year withMonthAndWeeks = new Year(2);
        withMonthAndWeeks.addMonth(1);
        withMonthAndWeeks.addWeek(1);

        new EqualsTester().addEqualityGroup(new Year(), new Year()).addEqualityGroup(new Year(1), new Year(1))
                .addEqualityGroup(empty, withMonths, withWeeks, withMonthAndWeeks).testEquals();
    }


    @Test
    public void compare()
    {
        Year past = new Year(1000);
        Year cut = new Year(2000);
        Year future = new Year(3000);

        assertEquals(0, cut.compareTo(cut));
        assertTrue(cut.compareTo(future) > 0);
        assertTrue(cut.compareTo(past) < 0);
    }
}
