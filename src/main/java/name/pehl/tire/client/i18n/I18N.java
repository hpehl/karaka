package name.pehl.tire.client.i18n;

import java.util.MissingResourceException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;

/**
 * @author $Author:$
 * @version $Date:$ $Revision:$
 */
public final class I18N
{
    public static final Constants CONSTANTS = GWT.create(Constants.class);
    public static final ConstantsWithLookup ENUMS = GWT.create(Enums.class);
    public static final Messages MESSAGES = GWT.create(Messages.class);


    private I18N()
    {
    }


    public static <E extends Enum<E>> String enumLabel(Enum<E> enumValue)
    {
        String title = CONSTANTS.na();
        if (enumValue != null)
        {
            String key = buildEnumKey(enumValue);
            try
            {
                title = ENUMS.getString(key);
            }
            catch (MissingResourceException e)
            {
                title = "Fehlende Konstante für '" + key + "'";
            }
        }
        return title;
    }


    public static <E extends Enum<E>> String buildEnumKey(Enum<E> enumValue)
    {
        if (enumValue != null)
        {
            String key = enumValue.getClass().getName() + "." + enumValue.ordinal();
            return key.replace('.', '_');
        }
        return null;
    }
}
