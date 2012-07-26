package name.pehl.tire.client.activity.presenter;

import static name.pehl.tire.client.activity.presenter.TimeParser.Seperator.COLUMN;
import static name.pehl.tire.client.activity.presenter.TimeParser.Seperator.COMMA;
import static name.pehl.tire.client.activity.presenter.TimeParser.Seperator.DOT;
import static name.pehl.tire.client.activity.presenter.TimeParser.Unit.HOURS;
import static name.pehl.tire.client.activity.presenter.TimeParser.Unit.MINUTES;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeParser
{
    static final Pattern REGEX = Pattern.compile("([0-9]+)([,.:]?)([0-9]*)([hm]?)");


    public Duration parse(String time) throws ParseException
    {
        Duration result = Duration.EMPTY;
        if (time != null && time.trim().length() != 0)
        {
            Matcher matcher = REGEX.matcher(time);
            if (matcher.matches())
            {
                long hours = asLong(matcher.group(1));
                Seperator seperator = asSeperator(matcher.group(2));
                long minutes = asLong(matcher.group(3));
                Unit unit = asUnit(matcher.group(4));

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
            else
            {
                throw new ParseException("Illegal time: \"" + time + "\"");
            }
        }
        return result;
    }


    private Seperator asSeperator(String value)
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
        }
        return seperator;
    }


    private Unit asUnit(String value)
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

    public static class Duration
    {
        public static final Duration EMPTY = new Duration(0, 0);

        private final long hours;
        private final long minutes;


        public Duration(long hours, long minutes)
        {
            super();
            this.hours = hours;
            this.minutes = minutes;
        }


        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (hours ^ hours >>> 32);
            result = prime * result + (int) (minutes ^ minutes >>> 32);
            return result;
        }


        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
            {
                return true;
            }
            if (obj == null)
            {
                return false;
            }
            if (!(obj instanceof Duration))
            {
                return false;
            }
            Duration other = (Duration) obj;
            if (hours != other.hours)
            {
                return false;
            }
            if (minutes != other.minutes)
            {
                return false;
            }
            return true;
        }


        @Override
        public String toString()
        {
            return hours + "h " + minutes + "m";
        }


        public long getHours()
        {
            return hours;
        }


        public long getMinutes()
        {
            return minutes;
        }


        public long getTotalMinutes()
        {
            return hours * 60 + minutes;
        }


        public boolean isEmpty()
        {
            return this.equals(EMPTY);
        }
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
