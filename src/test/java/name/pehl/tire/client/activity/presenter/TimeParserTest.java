package name.pehl.tire.client.activity.presenter;

import static org.junit.Assert.assertEquals;
import name.pehl.tire.client.activity.view.DurationParser;
import name.pehl.tire.client.activity.view.DurationParser.ParseException;
import name.pehl.tire.shared.model.Duration;

import org.junit.Before;
import org.junit.Test;

public class TimeParserTest
{
    // ------------------------------------------------------------------ setup

    DurationParser cut;


    @Before
    public void setUp()
    {
        this.cut = new DurationParser();
    }


    // --------------------------------------------------- null / empty / blank

    @Test
    public void nil() throws ParseException
    {
        Duration duration = cut.parse(null);
        assertEquals(Duration.EMPTY, duration);
    }


    @Test
    public void empty() throws ParseException
    {
        Duration duration = cut.parse("");
        assertEquals(Duration.EMPTY, duration);
    }


    @Test
    public void blank() throws ParseException
    {
        Duration duration = cut.parse("    ");
        assertEquals(Duration.EMPTY, duration);
    }


    // ---------------------------------------------------------- invalid input

    @Test(expected = ParseException.class)
    public void invalidTime() throws ParseException
    {
        cut.parse("foo");
    }


    @Test(expected = ParseException.class)
    public void negativeHours() throws ParseException
    {
        cut.parse("-12");
    }


    @Test(expected = ParseException.class)
    public void negativeMinutes() throws ParseException
    {
        cut.parse("-12m");
    }


    // ------------------------------------------------------------------ hours

    @Test
    public void hoursOk() throws ParseException
    {
        String[] times = new String[] {"1", "01", "1h", "01h", "1:", "01:", "1.", "01.", "1,", "01,"};
        for (String time : times)
        {
            Duration duration = cut.parse(time);
            assertEquals("Error parsing " + time, 1, duration.getHours());
            assertEquals("Error parsing " + time, 0, duration.getMinutes());
        }
    }


    @Test(expected = ParseException.class)
    public void hoursError() throws ParseException
    {
        cut.parse("24");
    }


    // ---------------------------------------------------------------- minutes

    @Test
    public void minutesOk() throws ParseException
    {
        String[] times = new String[] {"1m", "01m", "1.2m", "01.2m", "1.22m", "01.22m", "1,2m", "01,2m", "1,22m",
                "01,22m"};
        for (String time : times)
        {
            Duration duration = cut.parse(time);
            assertEquals("Error parsing " + time, 0, duration.getHours());
            assertEquals("Error parsing " + time, 1, duration.getMinutes());
        }
    }


    @Test(expected = ParseException.class)
    public void minutesError() throws ParseException
    {
        cut.parse("1440m");
    }


    // ------------------------------------------------------ hours and minutes

    @Test
    public void hoursAndMinutesOk() throws ParseException
    {
        String[] timesWithPoint = new String[] {"1.2", "1.20", "01.2", "01.20", "1.2h", "1.20h", "01.2h", "01.20h"};
        for (String time : timesWithPoint)
        {
            Duration duration = cut.parse(time);
            assertEquals("Error parsing " + time, 1, duration.getHours());
            assertEquals("Error parsing " + time, 12, duration.getMinutes());
        }

        String[] timesWithComma = new String[] {"1,2", "1,20", "01,2", "01,20", "1,2h", "1,20h", "01,2h", "01,20h"};
        for (String time : timesWithComma)
        {
            Duration duration = cut.parse(time);
            assertEquals("Error parsing " + time, 1, duration.getHours());
            assertEquals("Error parsing " + time, 12, duration.getMinutes());
        }

        String[] timesWithColumn = new String[] {"1:2", "1:02", "1:2h", "1:02h", "1:2m", "1:02m"};
        for (String time : timesWithColumn)
        {
            Duration duration = cut.parse(time);
            assertEquals("Error parsing " + time, 1, duration.getHours());
            assertEquals("Error parsing " + time, 2, duration.getMinutes());
        }
        timesWithColumn = new String[] {"1:20", "01:20", "1:20h", "01:20h", "1:20m", "01:20m"};
        for (String time : timesWithColumn)
        {
            Duration duration = cut.parse(time);
            assertEquals("Error parsing " + time, 1, duration.getHours());
            assertEquals("Error parsing " + time, 20, duration.getMinutes());
        }
    }


    @Test(expected = ParseException.class)
    public void hoursAndMinutesErrorHour1() throws ParseException
    {
        cut.parse("24,10");
    }


    @Test(expected = ParseException.class)
    public void hoursAndMinutesErrorHour2() throws ParseException
    {
        cut.parse("24:10");
    }


    @Test(expected = ParseException.class)
    public void hoursAndMinutesErrorMinute1() throws ParseException
    {
        cut.parse("10,123");
    }


    @Test(expected = ParseException.class)
    public void hoursAndMinutesErrorMinute2() throws ParseException
    {
        cut.parse("10:60");
    }
}
