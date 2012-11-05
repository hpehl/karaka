package name.pehl.karaka.server.sampledata;

import name.pehl.karaka.server.settings.entity.Settings;
import name.pehl.karaka.server.settings.entity.User;

import javax.enterprise.inject.Produces;

public class DefaultSettingsProducer
{
    @Produces
    @DefaultSettings
    public Settings defaultSettings()
    {
        Settings settings = new Settings();
        settings.setFormatHoursAsFloatingPointNumber(false);
        User user = new User("foobar", "foo@bar.com");
        user.setFirstname("Foo");
        user.setSurname("Bar");
        settings.setUser(user);
        return settings;
    }

}
