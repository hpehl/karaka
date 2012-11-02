package name.pehl.karaka.client.activity.model;

import name.pehl.piriti.converter.client.AbstractConverter;
import name.pehl.karaka.shared.model.Duration;

public class DurationConverter extends AbstractConverter<Duration>
{
    @Override
    public Duration convert(String value)
    {
        if (isValid(value))
        {
            try
            {
                long d = Long.valueOf(value);
                return new Duration(d);
            }
            catch (NumberFormatException e)
            {
                return Duration.ZERO;
            }
        }
        return Duration.ZERO;
    }
}
