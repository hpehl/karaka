package name.pehl.karaka.server.sampledata;

import javax.enterprise.inject.Produces;

import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.settings.entity.User;

public class DefaultSettingsProducer
{
    @Produces
    @DefaultSettings
    public Settings defaultSettings()
    {
        Settings settings = new Settings();
        settings.setFormatHoursAsFloatingPointNumber(false);
        settings.setHoursPerMonth(40);
        User user = new User("foobar", "foo@bar.com");
        user.setFirstname("Foo");
        user.setSurname("Bar");
        settings.setUser(user);
        return settings;
    }

}
