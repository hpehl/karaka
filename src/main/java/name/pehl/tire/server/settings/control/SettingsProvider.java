package name.pehl.tire.server.settings.control;

import javax.enterprise.inject.Produces;

import name.pehl.tire.server.settings.entity.Settings;

public class SettingsProvider
{
    @Produces
    @CurrentSettings
    public Settings produceSettings()
    {
        // TODO Replace with settings loaded from datastore
        return new Settings();
    }
}
