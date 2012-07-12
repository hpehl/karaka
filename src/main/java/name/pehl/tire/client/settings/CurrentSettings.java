package name.pehl.tire.client.settings;

import name.pehl.tire.shared.model.Settings;
import name.pehl.tire.shared.model.User;

public class CurrentSettings
{
    private static final CurrentSettings instance = new CurrentSettings();
    private Settings settings;


    private CurrentSettings()
    {
        this.settings = defaultSettings();
    }


    private Settings defaultSettings()
    {
        Settings defaults = new Settings();
        defaults.setFormatHoursAsFloatingPointNumber(false);
        defaults.setHoursPerMonth(40);
        defaults.setTimeZoneId("UTC");
        User defaultUser = new User();
        defaultUser.setUsername("n/a");
        defaultUser.setFirstname("n/a");
        defaultUser.setSurname("n/a");
        defaultUser.setEmail("n/a");
        defaults.setUser(defaultUser);
        return defaults;
    }


    public static CurrentSettings get()
    {
        return instance;
    }


    public static void set(Settings settings)
    {
        if (settings != null)
        {
            instance.settings = settings;
        }
    }


    public Settings settings()
    {
        return this.settings;
    }
}
