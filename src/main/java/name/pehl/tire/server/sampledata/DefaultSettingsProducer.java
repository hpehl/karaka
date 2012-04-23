package name.pehl.tire.server.sampledata;

import javax.enterprise.inject.Produces;

import name.pehl.tire.server.settings.entity.Settings;
import name.pehl.tire.server.settings.entity.User;

public class DefaultSettingsProducer
{
    @Produces
    @DefaultSettings
    public Settings defaultSettings()
    {
        Settings settings = new Settings();
        settings.setHoursPerMonth(40);
        User user = new User("foobar", "foo@bar.com");
        user.setFirstname("Foo");
        user.setSurname("Bar");
        settings.setUser(user);
        return settings;
    }

}
