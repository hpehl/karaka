package name.pehl.tire.client.settings;

import static java.util.logging.Level.INFO;
import name.pehl.tire.client.dispatch.TireCallback;
import name.pehl.tire.client.model.AbstractModelCache;
import name.pehl.tire.client.model.ModelCache;
import name.pehl.tire.shared.model.Settings;
import name.pehl.tire.shared.model.User;

import com.google.gwt.core.client.Scheduler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;

public class SettingsCache extends AbstractModelCache<Settings> implements ModelCache<Settings>
{
    private static Settings currentSettings = defaultSettings();


    @Inject
    public SettingsCache(EventBus eventBus, Scheduler scheduler, DispatchAsync dispatcher)
    {
        super(eventBus, dispatcher);
        models.add(currentSettings);
    }


    @Override
    public void refresh()
    {
        logger.log(INFO, "About to refresh settings...");
        dispatcher.execute(new GetSettingsAction(), new TireCallback<GetSettingsResult>(eventBus)
        {
            @Override
            public void onSuccess(GetSettingsResult result)
            {
                Settings settings = result.getSettings();
                models.clear();
                models.add(settings);
                currentSettings = settings;
                logger.log(INFO, "Settings refreshed.");
            }
        });
    }


    private static Settings defaultSettings()
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


    /**
     * Static helper to access current settings from other static methods
     * 
     * @return
     */
    public static Settings currentSettings()
    {
        return currentSettings;
    }
}
