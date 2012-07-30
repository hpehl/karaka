package name.pehl.tire.shared.model;

import name.pehl.piriti.converter.client.AbstractConverter;

public class DurationConverter extends AbstractConverter<Duration>
{
    @Override
    public Duration convert(String value)
    {
        if (value != null && value.length() != 0)
        {
            try
            {
                long d = Long.valueOf(value);
                return new Duration(d);
            }
            catch (NumberFormatException e)
            {
            }
        }
        return Duration.EMPTY;
    }
}
