package name.pehl.tire.client.activity.view;

import static name.pehl.tire.client.activity.view.DurationParser.Seperator.*;
import static name.pehl.tire.client.activity.view.DurationParser.Unit.HOURS;
import static name.pehl.tire.client.activity.view.DurationParser.Unit.MINUTES;
import name.pehl.tire.shared.model.Duration;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;

public class DurationParser
{
    static final RegExp REGEXP = RegExp.compile("([0-9]+)([.,:]?)([0-9]*)([hm]?)");


    public Duration parse(String time) throws ParseException
    {
        Duration result = Duration.EMPTY;
        if (time != null && time.trim().length() != 0)
        {
            if (!REGEXP.test(time))
            {
                throw new ParseException("Illegal time: \"" + time + "\"");
            }
            MatchResult match = REGEXP.exec(time);
            String hoursGroup = match.getGroup(1);
            if (!time.startsWith(hoursGroup))
            {
                throw new ParseException("Illegal time: \"" + time + "\"");
            }
            long hours = asLong(hoursGroup);
            Seperator seperator = asSeperator(match.getGroup(2));
            long minutes = asLong(match.getGroup(3));
            Unit unit = asUnit(match.getGroup(4));

            if (seperator == COLUMN)
            {
                // Unit is ignored!
                if (hours < 0 || hours > 23)
                {
                    throw new ParseException("Hour is out of range [0-23]");
                }
                if (minutes < 0 || minutes > 59)
                {
                    throw new ParseException("Minute is out of range [0-59]");
                }
            }
            else
            {
                if (unit == Unit.NONE || unit == HOURS)
                {
                    if (hours < 0 || hours > 23)
                    {
                        throw new ParseException("Hour is out of range [0-23]");
                    }
                    if (minutes < 0 || minutes > 99)
                    {
                        throw new ParseException("Minute is out of range [0-99]");
                    }
                    if (minutes < 10)
                    {
                        minutes *= 10;
                    }
                    minutes *= .6;
                }
                else
                {
                    minutes = hours;
                    hours = 0;
                    if (minutes < 0 || minutes > 1439)
                    {
                        throw new ParseException("Minute is out of range [0-1439]");
                    }
                }
            }
            result = new Duration(hours, minutes);
        }
        return result;
    }


    private Seperator asSeperator(String value) throws ParseException
    {
        Seperator seperator = Seperator.NONE;
        if (value != null && value.length() != 0)
        {
            if (":".equals(value))
            {
                seperator = COLUMN;
            }
            else if (".".equals(value))
            {
                seperator = DOT;
            }
            else if (",".equals(value))
            {
                seperator = COMMA;
            }
            else
            {
                throw new ParseException("Illegal seperator: \"" + value + "\"");
            }
        }
        return seperator;
    }


    private Unit asUnit(String value) throws ParseException
    {
        Unit unit = Unit.NONE;
        if (value != null && value.length() != 0)
        {
            if ("h".equals(value.toLowerCase()))
            {
                unit = HOURS;
            }
            else if ("m".equals(value.toLowerCase()))
            {
                unit = MINUTES;
            }
            else
            {
                throw new ParseException("Illegal unit: \"" + value + "\"");
            }
        }
        return unit;
    }


    private long asLong(String number)
    {
        if (number == null || number.trim().length() == 0)
        {
            return 0;
        }
        return Long.parseLong(number);
    }

    enum Seperator
    {
        NONE,
        DOT,
        COMMA,
        COLUMN;
    }

    enum Unit
    {
        NONE,
        HOURS,
        MINUTES;
    }

    public static class ParseException extends Exception
    {
        public ParseException()
        {
            super();
        }


        public ParseException(String message, Throwable cause)
        {
            super(message, cause);
        }


        public ParseException(String message)
        {
            super(message);
        }


        public ParseException(Throwable cause)
        {
            super(cause);
        }
    }
}
