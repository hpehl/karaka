package name.pehl.tire.client.activity.view;

import name.pehl.tire.client.activity.view.HourMinuteParser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HourMinuteParserTest
{
    HourMinuteParser cut;


    @Before
    public void setUp()
    {
        cut = new HourMinuteParser();
    }


    @Test(expected = IllegalArgumentException.class)
    public void nil()
    {
        cut.parse(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void blank()
    {
        cut.parse("");
    }


    @Test(expected = IllegalArgumentException.class)
    public void empty()
    {
        cut.parse("   ");
    }


    @Test(expected = IllegalArgumentException.class)
    public void twoColons()
    {
        cut.parse("1:2:3");
    }


    @Test(expected = IllegalArgumentException.class)
    public void hoursWrongFormat()
    {
        cut.parse("aa");
    }


    @Test(expected = IllegalArgumentException.class)
    public void hoursWrongFormatMinutesOk()
    {
        cut.parse("aa:12");
    }


    @Test(expected = IllegalArgumentException.class)
    public void hoursOkMinutesWrongFormat()
    {
        cut.parse("12:aa");
    }


    @Test(expected = IllegalArgumentException.class)
    public void hoursAndMinuteswrongFormat()
    {
        cut.parse("aa:bb");
    }


    @Test
    public void justHours()
    {
        int[] hm = cut.parse("12");
        assertEquals(12, hm[0]);
        assertEquals(0, hm[1]);
    }


    @Test
    public void justMinutes()
    {
        int[] hm = cut.parse("12");
        assertEquals(12, hm[0]);
        assertEquals(0, hm[1]);
    }


    @Test
    public void minutesHours()
    {
        int[] hm = cut.parse("12:34");
        assertEquals(12, hm[0]);
        assertEquals(34, hm[1]);
    }
}
