package name.pehl.karaka.client.resources;

import java.util.MissingResourceException;

import com.google.inject.Inject;

/**
 * @author $Author: harald.pehl $
 * @version $Date: 2010-09-22 17:30:50 +0200 (Mi, 22. Sep 2010) $ $Revision: 85
 *          $
 */
public class I18n
{
    private final Constants constants;
    private final Enums enums;
    private final Messages messages;


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
