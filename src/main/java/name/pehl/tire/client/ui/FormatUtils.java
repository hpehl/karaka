package name.pehl.tire.client.ui;

import java.util.Date;

import name.pehl.tire.client.Defaults;

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
        if (minutes > 0)
        {
            return Defaults.NUMBER_FORMAT.format(minutes / 60.0) + "h";
        }
        return "0h";
    }


    public static String date(Date date)
    {
        if (date != null)
        {
            return Defaults.DATE_FORMAT.format(date);
        }
        return "";
    }


    public static String time(Date date)
    {
        if (date != null)
        {
            return Defaults.TIME_FORMAT.format(date);
        }
        return "";
    }
}
