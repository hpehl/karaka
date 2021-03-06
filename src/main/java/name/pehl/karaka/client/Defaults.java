package name.pehl.karaka.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-12-18 01:35:28 +0100 (Sa, 18. Dez 2010) $ $Revision: 173
 *          $
 */
public interface Defaults
{
    String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss.SSS Z";

    // TODO Replace with user setting
    DateTimeFormat FULL_DATE_FORMAT = DateTimeFormat.getFormat("EEE dd.MM.yyyy");
    DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd.MM.yyyy");
    DateTimeFormat TIME_FORMAT = DateTimeFormat.getFormat("HH:mm");
    NumberFormat NUMBER_FORMAT = NumberFormat.getFormat("#0.00");
    NumberFormat HOUR_MINUTE_FORMAT = NumberFormat.getFormat("00");
}
