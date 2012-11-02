package name.pehl.karaka.client.ui;

import name.pehl.karaka.client.Defaults;
import name.pehl.karaka.client.settings.SettingsCache;
import name.pehl.karaka.shared.model.Duration;
import name.pehl.karaka.shared.model.Time;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-03 16:50:33 +0100 (Fr, 03. Dez 2010) $ $Revision: 173
 *          $
 */
public final class FormatUtils
{
    private FormatUtils()
    {
    }


    public static String duration(final Duration duration)
    {
        if (SettingsCache.currentSettings().isFormatHoursAsFloatingPointNumber())
        {
            if (duration.isZero())
            {
                return "0h";
            }
            else
            {
                return Defaults.NUMBER_FORMAT.format(duration.getTotalHours()) + "h";
            }
        }
        else
        {
            if (duration.isZero())
            {
                return "00:00";
            }
            else
            {
                return Defaults.HOUR_MINUTE_FORMAT.format(duration.getHours()) + ":"
                        + Defaults.HOUR_MINUTE_FORMAT.format(duration.getMinutes());
            }
        }
    }


    public static String dateDuration(final Time start, final Time end)
    {
        StringBuilder duration = new StringBuilder();
        if (start != null && start.getDate() != null)
        {
            duration.append(date(start)).append(" - ");
        }
        if (end != null && end.getDate() != null)
        {
            duration.append(date(end));
        }
        return duration.toString();
    }


    public static String date(final Time time)
    {
        if (time != null && time.getDate() != null)
        {
            return Defaults.DATE_FORMAT.format(time.getDate());
        }
        return "";
    }


    public static String fulldate(final Time time)
    {
        if (time != null && time.getDate() != null)
        {
            return Defaults.FULL_DATE_FORMAT.format(time.getDate());
        }
        return "";
    }


    public static String timeDuration(final Time start, final Time end)
    {
        StringBuilder duration = new StringBuilder();
        if (start != null && start.getDate() != null)
        {
            duration.append(time(start)).append(" - ");
        }
        if (end != null && end.getDate() != null)
        {
            duration.append(time(end));
        }
        return duration.toString();
    }


    public static String time(final Time time)
    {
        if (time != null && time.getDate() != null)
        {
            return Defaults.TIME_FORMAT.format(time.getDate());
        }
        return "";
    }
}
