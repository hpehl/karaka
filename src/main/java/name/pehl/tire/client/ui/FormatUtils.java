package name.pehl.tire.client.ui;

import name.pehl.tire.client.Defaults;
import name.pehl.tire.client.settings.SettingsCache;
import name.pehl.tire.shared.model.Time;

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


    public static String hours(long minutes)
    {
        if (SettingsCache.currentSettings().isFormatHoursAsFloatingPointNumber())
        {
            if (minutes > 0)
            {
                return Defaults.NUMBER_FORMAT.format(minutes / 60.0) + "h";
            }
            else
            {
                return "0h";
            }
        }
        else
        {
            if (minutes > 0)
            {
                long h = minutes / 60;
                long m = minutes % 60;
                return Defaults.HOUR_MINUTE_FORMAT.format(h) + ":" + Defaults.HOUR_MINUTE_FORMAT.format(m);
            }
            else
            {
                return "00:00";
            }
        }
    }


    public static String dateDuration(Time start, Time end)
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


    public static String date(Time time)
    {
        if (time != null && time.getDate() != null)
        {
            return Defaults.DATE_FORMAT.format(time.getDate());
        }
        return "";
    }


    public static String timeDuration(Time start, Time end)
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


    public static String time(Time time)
    {
        if (time != null && time.getDate() != null)
        {
            return Defaults.TIME_FORMAT.format(time.getDate());
        }
        return "";
    }
}
