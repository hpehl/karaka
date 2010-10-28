package name.pehl.tire.rest.activity;

import static name.pehl.tire.rest.activity.ActivityParameters.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public class ActivityParametersTest
{
    // ----------------------------------------------------------- test methods

    @Test
    public void testEmpty()
    {
        assertFalse(new ActivityParameters().parse(null).hasParameters());
        assertFalse(new ActivityParameters().parse(new HashMap<String, Object>()).hasParameters());
    }


    @Test
    public void testId()
    {
        ActivityParameters parameters = new ActivityParameters().parse(mapFor(ID, "0"));
        assertTrue(parameters.hasId());
        assertEquals(0, parameters.getId());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongId()
    {
        new ActivityParameters().parse(mapFor(ID, "nope"));
    }


    @Test
    public void testYearMonth()
    {
        ActivityParameters parameters = new ActivityParameters().parse(mapFor(YEAR_OR_ID, "1973",
                MONTH_OR_WEEK_OR_ACTION, "9"));
        assertTrue(parameters.hasYear());
        assertEquals(1973, parameters.getYear());
        assertTrue(parameters.hasMonth());
        assertEquals(9, parameters.getMonth());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongYearMonth()
    {
        new ActivityParameters().parse(mapFor(YEAR_OR_ID, "nope", MONTH_OR_WEEK_OR_ACTION, "9"));
    }


    @Test
    public void testYearWeek()
    {
        ActivityParameters parameters = new ActivityParameters().parse(mapFor(YEAR_OR_ID, "1973",
                MONTH_OR_WEEK_OR_ACTION, "cw42"));
        assertTrue(parameters.hasYear());
        assertEquals(1973, parameters.getYear());
        assertTrue(parameters.hasWeek());
        assertEquals(42, parameters.getWeek());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongYearWeek()
    {
        new ActivityParameters().parse(mapFor(YEAR_OR_ID, "nope", MONTH_OR_WEEK_OR_ACTION, "cw42"));
    }


    @Test
    public void testYearMonthDay()
    {
        ActivityParameters parameters = new ActivityParameters().parse(mapFor(YEAR, "1973", MONTH, "9", DAY, "2"));
        assertTrue(parameters.hasYear());
        assertEquals(1973, parameters.getYear());
        assertTrue(parameters.hasMonth());
        assertEquals(9, parameters.getMonth());
        assertTrue(parameters.hasDay());
        assertEquals(2, parameters.getDay());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongYearMonthDay()
    {
        new ActivityParameters().parse(mapFor(YEAR, "nope", MONTH, "9", DAY, "2"));
    }


    @Test
    public void testIdAction()
    {
        ActivityParameters parameters = new ActivityParameters().parse(mapFor(YEAR_OR_ID, "123",
                MONTH_OR_WEEK_OR_ACTION, "stop"));
        assertTrue(parameters.hasId());
        assertEquals(123, parameters.getId());
        assertTrue(parameters.hasAction());
        assertEquals(Action.STOP, parameters.getAction());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testWrongIdAction()
    {
        new ActivityParameters().parse(mapFor(YEAR_OR_ID, "123", MONTH_OR_WEEK_OR_ACTION, "foo"));
    }


    // --------------------------------------------------------- helper methods

    private Map<String, Object> mapFor(String... keysAndValues)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (keysAndValues != null && keysAndValues.length != 0 && keysAndValues.length % 2 == 0)
        {
            for (int i = 0; i < keysAndValues.length - 1; i++)
            {
                map.put(keysAndValues[i], keysAndValues[i + 1]);
            }
        }
        return map;
    }
}
