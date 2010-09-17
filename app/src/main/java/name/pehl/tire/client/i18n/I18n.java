package name.pehl.tire.client.i18n;

import java.util.MissingResourceException;

import com.google.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-16 16:22:23 +0200 (Do, 16. Sep 2010) $ $Revision: 41 $
 */
public class I18n
{
    private Constants constants;
    private Enums enums;
    private Messages messages;


    @Inject
    public I18n(Constants constants, Enums enums, Messages messages)
    {
        this.constants = constants;
        this.enums = enums;
        this.messages = messages;
    }


    public <E extends Enum<E>> String enumLabel(Enum<E> enumValue)
    {
        String title = constants.na();
        if (enumValue != null)
        {
            String lookupKey = enumValue.getClass().getName() + "." + enumValue.name();
            lookupKey = lookupKey.replace(".", "_");
            lookupKey = lookupKey.replace("$", "_");
            try
            {
                title = enums.getString(lookupKey);
            }
            catch (MissingResourceException e)
            {
                title = "No constant for '" + lookupKey + "'";
            }
        }
        return title;
    }


    public Constants constants()
    {
        return constants;
    }


    public Enums enums()
    {
        return enums;
    }


    public Messages messages()
    {
        return messages;
    }
}
